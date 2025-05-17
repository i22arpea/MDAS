package sistema.autenticacion.interfaces;

public interface ISesion {
    /**
     * Inicia sesión en el sistema con las credenciales proporcionadas.
     *
     * @param usuario     El nombre de usuario o correo electrónico del usuario.
     * @param contraseña  La contraseña del usuario.
     * @return El tipo de usuario si la sesión se inicia correctamente ("Administrador", "Organizador", "Usuario", etc.), null en caso contrario.
     */
    String iniciarSesion(String usuario, String contraseña);

    /**
     * Recupera la contraseña del usuario a través de su correo electrónico.
     *
     * @param correo El correo electrónico del usuario.
     */
    void recuperarContrasena(String correo);
}
