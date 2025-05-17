package modelo;

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
}
