package modelo;

import java.util.Date;
import org.json.JSONObject;

public abstract class Usuario {
    protected String email;
    protected String contraseña;
    protected int intentosFallidos;
    protected Date bloqueadoHasta;
    protected boolean sesionActiva;

    public Usuario(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
        this.intentosFallidos = 0;
        this.bloqueadoHasta = null;
        this.sesionActiva = false;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public Date getBloqueadoHasta() {
        return bloqueadoHasta;
    }

    public boolean isSesionActiva() {
        return sesionActiva;
    }

    public abstract JSONObject toJSONObject();
}
