package sistema.autenticacion.interfaces;

public interface ISesion {
    /**
     * Inicia sesión en el sistema con las credenciales proporcionadas.
     *
     * @param usuario     El nombre de usuario o correo electrónico del usuario.
     * @param contraseña  La contraseña del usuario.
     * @return true si la sesión se inicia correctamente, false en caso contrario.
     */
    boolean iniciarSesion(String usuario, String contraseña);

    /**
     * Recupera la contraseña del usuario a través de su correo electrónico.
     *
     * @param correo El correo electrónico del usuario.
     */
    void recuperarContrasena(String correo);
}
