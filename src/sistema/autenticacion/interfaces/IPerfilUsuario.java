package sistema.autenticacion.interfaces;

public interface IPerfilUsuario {

    /**
     * Permite a un usuario o organizador modificar su información personal.
     * @param email Email del usuario autenticado.
     * @param campo Campo a modificar (nombre, nombreEmpresa, numeroContacto, etc.).
     * @param nuevoValor Nuevo valor para el campo.
     * @return true si se modificó correctamente.
     */
    boolean modificarInformacion(String email, String campo, String nuevoValor);

    /**
     * Devuelve una representación del perfil del usuario.
     * @param email Email del usuario autenticado.
     * @return String con la información del perfil, o null si no se encuentra.
     */
    String visualizarPerfil(String email);

    /**
     * Elimina la cuenta del usuario del sistema.
     * @param email Email del usuario.
     * @return true si la cuenta fue eliminada.
     */
    boolean eliminarCuenta(String email);
}
