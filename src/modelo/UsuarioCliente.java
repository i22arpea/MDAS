package modelo;

import org.json.JSONObject;


public class UsuarioCliente extends Usuario {
    private String dni;
    private String nombre;

    public UsuarioCliente(String email, String contraseña, String dni, String nombre) {
        super(email, contraseña);
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "cliente");
        obj.put("email", this.email);
        obj.put("contrasena", this.contraseña);
        obj.put("dni", this.dni);
        obj.put("nombre", this.nombre);
        // Añade otros campos base de Usuario si quieres (intentosFallidos, etc.)
        return obj;
    }
}

