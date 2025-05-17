package sistema.autenticacion;

import sistema.autenticacion.interfaces.IGestionarCuenta;
import java.util.Scanner;

public class AdminCLI {
    private final IGestionarCuenta cuenta;
    private final Scanner sc;

    public AdminCLI(IGestionarCuenta cuenta, Scanner sc) {
        this.cuenta = cuenta;
        this.sc = sc;
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- Menú de Administrador: Gestión de Cuentas ---");
            System.out.println("1. Visualizar todas las cuentas");
            System.out.println("2. Buscar cuenta");
            System.out.println("3. Eliminar cuenta de usuario");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    cuenta.visualizarCuentas();
                    break;
                case "2":
                    System.out.print("Búsqueda por corre: ");
                    String busqueda = sc.nextLine();
                    cuenta.buscarCuenta(busqueda);
                    break;
                case "3":
                    System.out.print("Correo del usuario a eliminar: ");
                    String correo = sc.nextLine();
                    cuenta.eliminarCuentaUsuario(correo);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
