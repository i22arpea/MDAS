package sistema.autenticacion.interfaces;


public interface IRegistro {
    /**
     * Crea una cuenta de usuario con los datos proporcionados.
     *
     * @param nombre      El nombre del usuario.
     * @param apellidos   Los apellidos del usuario.
     * @param correo      El correo electrónico del usuario.
     * @param telefono    El número de teléfono del usuario.
     * @param direccion   La dirección del usuario.
     * @param tipoUsuario El tipo de usuario (por ejemplo, "cliente", "administrador").
     * @param foto       La foto del usuario en formato byte[].
     * @param contrasena  La contraseña del usuario.
     */
    void crearCuenta(String nombre, String apellidos, String correo, int telefono, String direccion, String tipoUsuario, byte[] foto, String contrasena);  
    
    /**
     * Verifica los datos de la cuenta del usuario.
     *
     * @param correo      El correo electrónico del usuario.
     * @param telefono    El número de teléfono del usuario.
     * @param direccion   La dirección del usuario.
     * @param contrasena  La contraseña del usuario.
     * @return true si los datos son válidos, false en caso contrario.
     */
    boolean verificarDatosCuenta(String correo, int telefono, String direccion, String contrasena);
}