package negocio.autenticacion;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import modelo.Usuario;
import modelo.UsuarioFactory;

public class AutenticacionMgr extends AutenticacionMgt {
    private static final String RUTA_JSON = "usuarios.json";

    public AutenticacionMgr() {
        try {
            if (!Files.exists(Paths.get(RUTA_JSON))) {
                Files.write(Paths.get(RUTA_JSON), "[]".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Parte de IRegistro

    @Override
    public boolean verificarDatosCuenta(String correo, int telefono, String direccion, String contrasena) {
        // Ejemplo de verificación simple + comprobar que la cuenta no existe
        return correo != null && correo.contains("@")
                && telefono > 0
                && direccion != null && !direccion.isEmpty()
                && contrasena != null && contrasena.length() >= 6
                && verificarCuentaNoExiste(correo);
    }

    @Override
    public boolean verificarCuentaNoExiste(String correo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getString("email").equalsIgnoreCase(correo)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Usuario obtenerUsuario(String email) {
    try {
        String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
        JSONArray array = new JSONArray(contenido);
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if (obj.getString("email").equalsIgnoreCase(email)) {
                return UsuarioFactory.crearUsuarioDesdeJSON(obj);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
    }

    public void guardarUsuario(Usuario usuario) {
    try {
        String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
        JSONArray array = new JSONArray(contenido);
        array.put(usuario.toJSONObject());
        try (FileWriter file = new FileWriter(RUTA_JSON)) {
            file.write(array.toString(2));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void crearCuenta(String nombre, String apellidos, String correo, int telefono, String direccion, String tipoUsuario, byte[] foto, String contrasena) {
        if (!verificarDatosCuenta(correo, telefono, direccion, contrasena)) {
            System.out.println("Datos de cuenta inválidos o el correo ya está registrado. No se puede crear la cuenta.");
            return;
        }
        JSONObject obj = new JSONObject();
        obj.put("nombre", nombre);
        obj.put("apellidos", apellidos);
        obj.put("email", correo);
        obj.put("telefono", telefono);
        obj.put("direccion", direccion);
        obj.put("tipo", tipoUsuario);
        obj.put("foto", foto != null ? java.util.Base64.getEncoder().encodeToString(foto) : JSONObject.NULL);
        obj.put("contrasena", contrasena);
        obj.put("intentosFallidos", 0);
        obj.put("bloqueadoHasta", JSONObject.NULL);
        obj.put("sesionActiva", false);
        guardarUsuario(obj);
        System.out.println("Usuario registrado con éxito.");
    }

    private void guardarUsuario(JSONObject usuario) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            array.put(usuario);
            try (FileWriter file = new FileWriter(RUTA_JSON)) {
                file.write(array.toString(2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Parte de ISesion

    /**
     * Devuelve el tipo de usuario si las credenciales son correctas, o null si no lo son.
     */
    @Override
    public String comprobarCredenciales(String email, String contrasena) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            for (int i = 0; i < array.length(); i++) {
                JSONObject usuario = array.getJSONObject(i);
                if (usuario.getString("email").equalsIgnoreCase(email)) {
                    if (!usuario.isNull("bloqueadoHasta") && usuario.get("bloqueadoHasta") != null) {
                        long tiempoBloqueo = usuario.getLong("bloqueadoHasta");
                        if (new Date().getTime() < tiempoBloqueo) {
                            System.out.println("Usuario bloqueado hasta: " + new Date(tiempoBloqueo));
                            return null;
                        }
                    }
                    if (usuario.getString("contrasena").equals(contrasena)) {
                        usuario.put("intentosFallidos", 0);
                        usuario.put("sesionActiva", true);
                        array.put(i, usuario);
                        guardarJSON(array);
                        if (usuario.has("tipo")) {
                            return usuario.getString("tipo");
                        } else {
                            return "Usuario";
                        }
                    } else {
                        int fallos = usuario.getInt("intentosFallidos") + 1;
                        usuario.put("intentosFallidos", fallos);
                        if (fallos >= 3) {
                            bloquearSesion(usuario);
                            System.out.println("Demasiados intentos fallidos. Usuario bloqueado por 5 minutos.");
                        }
                        array.put(i, usuario);
                        guardarJSON(array);
                        return null;
                    }
                }
            }
            System.out.println("Usuario no encontrado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cambiar la firma de iniciarSesion para que devuelva String (tipo de usuario)
    @Override
    public String iniciarSesion(String email, String contrasena) {
        return comprobarCredenciales(email, contrasena);
    }


    @Override
    public void recuperarContrasena(String correo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            boolean encontrado = false;
            for (int i = 0; i < array.length(); i++) {
                JSONObject usuario = array.getJSONObject(i);
                if (usuario.getString("email").equalsIgnoreCase(correo)) {
                    encontrado = true;
                    // Simulación de envío de correo
                    System.out.println("Se ha enviado un correo de recuperación a: " + correo);
                    // Aquí podrías generar un token y almacenarlo, pero para la simulación llamamos directamente a cambiarContraseña
                    // Por ejemplo, podrías pedir al usuario que introduzca una nueva contraseña
                    // cambiarContraseña(correo, "nuevaContraseña");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontró ninguna cuenta con ese correo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cambiarContrasena(String correo, String passwordActual, String passwordNueva) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            boolean encontrado = false;
             for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getString("email").equalsIgnoreCase(correo)) {
                    // Usar el método verificarContraseña en vez de comparar directamente
                    if (verificarContrasena(correo, passwordActual)) {
                        obj.put("contrasena", passwordNueva);
                        array.put(i, obj);
                        encontrado = true;
                        guardarJSON(array);
                        System.out.println("Contraseña cambiada correctamente.");
                        return true;
                    } else {
                        System.out.println("La contraseña actual no es correcta.");
                        return false;
                    }
                }
            }
            if (!encontrado) {
                System.out.println("Usuario no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    @Override
    public boolean verificarContrasena(String correo, String password) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getString("email").equalsIgnoreCase(correo)) {
                    return obj.getString("contrasena").equals(password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void bloquearSesion(JSONObject usuario) {
        long cincoMinutos = System.currentTimeMillis() + (5 * 60 * 1000);
        usuario.put("bloqueadoHasta", cincoMinutos);
        usuario.put("intentosFallidos", 0);
    }
    

    // Parte de IGestionarCuenta

    @Override
    public void visualizarCuentas() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                System.out.println(obj.toString(2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buscarCuenta(String correo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            boolean encontrado = false;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getString("email").equalsIgnoreCase(correo)) {
                    System.out.println(obj.toString(2));
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontró ninguna cuenta con ese correo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCuentaUsuario(String correo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            JSONArray nuevoArray = new JSONArray();
            boolean eliminado = false;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (!obj.getString("email").equalsIgnoreCase(correo)) {
                    nuevoArray.put(obj);
                } else {
                    eliminado = true;
                }
            }
            Files.write(Paths.get(RUTA_JSON), nuevoArray.toString(2).getBytes());
            if (eliminado) {
                System.out.println("Cuenta eliminada correctamente.");
            } else {
                System.out.println("No se encontró una cuenta con ese correo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    //Parte de IPerfilUsuario

    @Override
    public boolean eliminarCuenta(String correo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            JSONArray nuevoArray = new JSONArray();
            boolean eliminado = false;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (!obj.getString("email").equalsIgnoreCase(correo)) {
                    nuevoArray.put(obj);
                } else {
                    eliminado = true;
                }
            }
            Files.write(Paths.get(RUTA_JSON), nuevoArray.toString(2).getBytes());
            if (eliminado) {
                System.out.println("Cuenta eliminada correctamente.");
                return true;
            } else {
                System.out.println("No se encontró una cuenta con ese correo.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String visualizarPerfil(String email) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getString("email").equalsIgnoreCase(email)) {
                    JSONObject editable = new JSONObject();
                    editable.put("nombre", obj.optString("nombre"));
                    editable.put("apellidos", obj.optString("apellidos"));
                    editable.put("telefono", obj.optInt("telefono"));
                    editable.put("direccion", obj.optString("direccion"));
                    if (!obj.isNull("foto")) {
                        editable.put("foto", obj.get("foto"));
                    }
                    return editable.toString(2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modificarInformacion(String email, String campo, String nuevoValor) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            boolean encontrado = false;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getString("email").equalsIgnoreCase(email)) {
                    obj.put(campo, nuevoValor);
                    array.put(i, obj);
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                guardarJSON(array);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void guardarJSON(JSONArray array) {
        try (FileWriter file = new FileWriter(RUTA_JSON)) {
            file.write(array.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
