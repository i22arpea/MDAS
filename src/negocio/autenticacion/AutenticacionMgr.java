package negocio.autenticacion;

import negocio.autenticacion.interfaces.IRegistro;
import negocio.autenticacion.interfaces.ISesion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class AutenticacionMgr implements IRegistro, ISesion {
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

    @Override
    public void registrarUsuarioCliente(String email, String contraseña, String dni, String nombre) {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "cliente");
        obj.put("email", email);
        obj.put("contraseña", contraseña);
        obj.put("intentosFallidos", 0);
        obj.put("bloqueadoHasta", JSONObject.NULL);
        obj.put("sesionActiva", false);
        obj.put("dni", dni);
        obj.put("nombre", nombre);

        guardarUsuario(obj);
        System.out.println("Usuario cliente registrado con éxito.");
    }

    @Override
    public void registrarOrganizador(String email, String contraseña, String nombreEmpresa, String numeroContacto) {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "organizador");
        obj.put("email", email);
        obj.put("contraseña", contraseña);
        obj.put("intentosFallidos", 0);
        obj.put("bloqueadoHasta", JSONObject.NULL);
        obj.put("sesionActiva", false);
        obj.put("nombreEmpresa", nombreEmpresa);
        obj.put("numeroContacto", numeroContacto);

        guardarUsuario(obj);
        System.out.println("Organizador registrado con éxito.");
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

    @Override
    public boolean iniciarSesion(String email, String contraseña) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA_JSON)));
            JSONArray array = new JSONArray(contenido);
            boolean encontrado = false;

            for (int i = 0; i < array.length(); i++) {
                JSONObject usuario = array.getJSONObject(i);

                if (usuario.getString("email").equalsIgnoreCase(email)) {
                    encontrado = true;

                    // Verificar si está bloqueado
                    if (!usuario.isNull("bloqueadoHasta") && usuario.get("bloqueadoHasta") != null) {
                        long tiempoBloqueo = usuario.getLong("bloqueadoHasta");
                        if (new Date().getTime() < tiempoBloqueo) {
                            System.out.println("Usuario bloqueado hasta: " + new Date(tiempoBloqueo));
                            return false;
                        }
                    }

                    if (usuario.getString("contraseña").equals(contraseña)) {
                        // Contraseña correcta
                        usuario.put("intentosFallidos", 0);
                        usuario.put("sesionActiva", true);
                        System.out.println("Inicio de sesión exitoso.");
                        array.put(i, usuario);
                        guardarJSON(array);
                        return true;
                    } else {
                        // Contraseña incorrecta
                        int fallos = usuario.getInt("intentosFallidos") + 1;
                        usuario.put("intentosFallidos", fallos);

                        if (fallos >= 3) {
                            long cincoMinutos = System.currentTimeMillis() + (5 * 60 * 1000);
                            usuario.put("bloqueadoHasta", cincoMinutos);
                            usuario.put("intentosFallidos", 0);
                            System.out.println("Demasiados intentos fallidos. Usuario bloqueado por 5 minutos.");
                        }
                        array.put(i, usuario);
                        guardarJSON(array);
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

    private void guardarJSON(JSONArray array) {
        try (FileWriter file = new FileWriter(RUTA_JSON)) {
            file.write(array.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
