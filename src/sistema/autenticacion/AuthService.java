package sistema.autenticacion;

import negocio.autenticacion.AutenticacionMgr;
import sistema.autenticacion.interfaces.IGestionarCuenta;
import sistema.autenticacion.interfaces.IPerfilUsuario;
import sistema.autenticacion.interfaces.IRegistro;
import sistema.autenticacion.interfaces.ISesion;

public class AuthService implements IRegistro, ISesion, IPerfilUsuario, IGestionarCuenta {
    private final AutenticacionMgr mgr = new AutenticacionMgr();

    //Registro
    // Firma correcta según AutenticacionMgr: (String correo, int telefono, String direccion, String contraseña)
    public boolean verificarDatosCuenta(String correo, int telefono, String direccion, String contraseña) {
        return mgr.verificarDatosCuenta(correo, telefono, direccion, contraseña);
    }

    public void crearCuenta(String nombre, String apellidos, String correo, int telefono, String direccion, String tipoUsuario, byte[] foto, String contraseña, String dni) {
        mgr.crearCuenta(nombre, apellidos, correo, telefono, direccion, tipoUsuario, foto, contraseña, dni);
    }

    //Sesion
    @Override
    public String iniciarSesion(String usuario, String contraseña) {
        return mgr.iniciarSesion(usuario, contraseña);
    }

    @Override
    public void recuperarContrasena(String correo) {
        mgr.recuperarContrasena(correo);
    }

    //Perfil
    @Override
    public boolean modificarInformacion(String correo, String campo, String nuevoValor) {
        return mgr.modificarInformacion(correo, campo, nuevoValor);
    }

    @Override
    public String visualizarPerfil(String correo) {
        return mgr.visualizarPerfil(correo);
    }

    @Override
    public boolean eliminarCuenta(String correo) {
        return mgr.eliminarCuenta(correo);
    }

    //Recuperar contraseña
    @Override
    public boolean cambiarContrasena(String correo, String passwordActual, String passwordNueva) {
        return mgr.cambiarContrasena(correo, passwordActual, passwordNueva);
    }

    //Gestionar Cuenta
    @Override
    public void visualizarCuentas() {
        mgr.visualizarCuentas();
    }
    @Override
    public void buscarCuenta(String busqueda) {
        mgr.buscarCuenta(busqueda);
    }
    @Override
    public void eliminarCuentaUsuario(String correo) {
        mgr.eliminarCuentaUsuario(correo);
    }

    // Devuelve un objeto Usuario (subclase) usando UsuarioFactory a partir del email
    public modelo.Usuario obtenerUsuarioPorEmail(String email) {
        return mgr.obtenerUsuarioPorEmail(email);
    }

}
