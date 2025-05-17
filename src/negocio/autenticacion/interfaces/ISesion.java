package negocio.autenticacion.interfaces;

public interface ISesion {
    boolean iniciarSesion(String email, String contraseña);
}
