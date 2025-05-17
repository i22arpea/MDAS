// IGestionarCuenta.java
package sistema.autenticacion.interfaces;

public interface IGestionarCuenta {
    boolean cambiarContrasena(String correo, String passwordActual, String passwordNueva);
    void visualizarCuentas();
    void buscarCuenta(String correo);
    void eliminarCuentaUsuario(String correo);
}