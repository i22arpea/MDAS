package sistema.autenticacion;

import sistema.autenticacion.interfaces.IGestionarCuenta;
import sistema.autenticacion.interfaces.IPerfilUsuario;
import sistema.autenticacion.interfaces.IRegistro;
import sistema.autenticacion.interfaces.ISesion;
import java.util.Scanner;

public class AuthCLI {
    private final IRegistro registro = null;
    private final ISesion sesion = null;
    private final IPerfilUsuario perfil;
    private final IGestionarCuenta cuenta;
    private final Scanner sc;
    private final String usuarioEmail;

    public AuthCLI(IPerfilUsuario perfil, IGestionarCuenta cuenta, Scanner sc, String usuarioEmail) {
        this.perfil = perfil;
        this.cuenta = cuenta;
        this.sc = sc;
        this.usuarioEmail = usuarioEmail;
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- Menú de Perfil de Usuario ---");
            System.out.println("1. Visualizar Perfil");
            System.out.println("2. Modificar Información");
            System.out.println("3. Cambiar Contraseña");
            System.out.println("4. Eliminar Cuenta");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    String perfilInfo = perfil.visualizarPerfil(usuarioEmail);
                    if (perfilInfo != null) {
                        System.out.println(perfilInfo);
                    } else {
                        System.out.println("No se encontró el perfil.");
                    }
                    break;
                case "2":
                    while (true) {
                        System.out.println("\n--- Selecciona el campo a modificar ---");
                        System.out.println("1. Nombre");
                        System.out.println("2. Apellidos");
                        System.out.println("3. Teléfono");
                        System.out.println("4. Dirección");
                        System.out.println("0. Volver");
                        String campoOpcion = sc.nextLine();
                        String campo = null;
                        switch (campoOpcion) {
                            case "1":
                                campo = "nombre";
                                break;
                            case "2":
                                campo = "apellidos";
                                break;
                            case "3":
                                campo = "telefono";
                                break;
                            case "4":
                                campo = "direccion";
                                break;
                            case "0":
                                campo = null;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                continue;
                        }
                        if (campo == null) break;
                        System.out.print("Nuevo valor para " + campo + ": ");
                        String nuevoValor = sc.nextLine();
                        boolean modificado = perfil.modificarInformacion(usuarioEmail, campo, nuevoValor);
                        if (modificado) {
                            System.out.println("Información modificada correctamente.");
                        } else {
                            System.out.println("No se pudo modificar la información.");
                        }
                        break;
                    }
                    break;
                case "3":
                    System.out.print("Contraseña actual: ");
                    String actual = sc.nextLine();
                    System.out.print("Nueva contraseña: ");
                    String nueva = sc.nextLine();
                    boolean cambiada = cuenta.cambiarContrasena(usuarioEmail, actual, nueva);
                    if (cambiada) {
                        System.out.println("Contraseña cambiada correctamente.");
                    } else {
                        System.out.println("No se pudo cambiar la contraseña. Verifique los datos.");
                    }
                    break;
                case "4":
                    boolean eliminado = perfil.eliminarCuenta(usuarioEmail);
                    if (eliminado) {
                        System.out.println("Cuenta eliminada correctamente.");
                        return;
                    } else {
                        System.out.println("No se pudo eliminar la cuenta.");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
