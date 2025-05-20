package negocio.autenticacion;

import sistema.autenticacion.interfaces.IRegistro;
import sistema.autenticacion.interfaces.ISesion;

import org.json.JSONObject;

import sistema.autenticacion.interfaces.IGestionarCuenta;

public abstract class AutenticacionMgt implements IRegistro, ISesion, IGestionarCuenta {
    // Métodos abstractos para ser implementados por la clase Mgr
    // Métodos de registro
    public abstract boolean verificarDatosCuenta(String correo, int telefono, String direccion, String contraseña);
    public abstract boolean verificarCuentaNoExiste(String correo);
    public abstract void crearCuenta(String nombre, String apellidos, String correo, int telefono, String direccion, String tipoUsuario, byte[] foto, String contraseña, String dni);
    // Métodos de inicio de sesión
    // Cambiar la firma de iniciarSesion para que devuelva String (tipo de usuario)
    public abstract String iniciarSesion(String email, String contrasena);
    public abstract String comprobarCredenciales(String email, String contrasena);
    public abstract void recuperarContrasena(String correo);
    public abstract boolean cambiarContrasena(String correo, String passwordActual, String passwordNueva);
    public abstract boolean verificarContrasena(String correo, String password);
    public abstract void bloquearSesion(JSONObject usuario);
    // Métodos de gestión de cuentas
    public abstract void visualizarCuentas();
    public abstract void buscarCuenta(String correo);
    public abstract void eliminarCuentaUsuario(String correo);
    // Métodos de gestión de perfil
    public abstract String visualizarPerfil(String correo);
    public abstract boolean eliminarCuenta(String correo);
    public abstract boolean modificarInformacion(String correo, String nuevoNombre, String nuevoDni);
    
}
