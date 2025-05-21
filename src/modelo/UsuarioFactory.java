package modelo;

import org.json.JSONObject;

public class UsuarioFactory {

    public static Usuario crearUsuarioDesdeJSON(JSONObject json) {
        String tipo = json.optString("tipo", "").toLowerCase();
        String email = json.optString("email", "");
        String contrasena = json.optString("contrasena", "");

        switch (tipo) {
            case "cliente":
                String dni = json.optString("dni", "");
                String nombre = json.optString("nombre", "");
                return new UsuarioCliente(email, contrasena, dni, nombre);
            case "administrador":
                return new UsuarioAdministrador(email, contrasena);
            case "organizador":
                String nombreEmpresa = json.optString("nombreEmpresa", "");
                String numeroContacto = json.optString("numeroContacto", "");
                return new Organizador(email, contrasena, nombreEmpresa, numeroContacto);
            default:
                throw new IllegalArgumentException("Tipo de usuario no soportado: " + tipo);
        }
    }

    // Otros métodos de creación pueden ir aquí (crearUsuario, crearCliente, etc.)

}
