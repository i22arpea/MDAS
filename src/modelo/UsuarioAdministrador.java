package modelo;

import org.json.JSONObject;


public class UsuarioAdministrador extends Usuario {
    public UsuarioAdministrador(String email, String contraseña) {
        super(email, contraseña);
    }
    
     @Override
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "administrador");
        obj.put("email", this.email);
        obj.put("contrasena", this.contraseña);
        return obj;
    }
}

