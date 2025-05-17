package negocio.autenticacion.interfaces;

public interface IRegistro {
    void registrarUsuarioCliente(String email, String contraseña, String dni, String nombre);
    void registrarOrganizador(String email, String contraseña, String nombreEmpresa, String numeroContacto);
}