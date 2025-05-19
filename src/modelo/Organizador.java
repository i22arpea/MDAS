package modelo;

import org.json.JSONObject;


public class Organizador extends Usuario {
    private String nombreEmpresa;
    private String numeroContacto;

    public Organizador(String email, String contraseña, String nombreEmpresa, String numeroContacto) {
        super(email, contraseña);
        this.nombreEmpresa = nombreEmpresa;
        this.numeroContacto = numeroContacto;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

     @Override
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "organizador");
        obj.put("email", this.email);
        obj.put("contrasena", this.contraseña);
        obj.put("nombreEmpresa", this.nombreEmpresa);
        obj.put("numeroContacto", this.numeroContacto);
        return obj;
    }
}
