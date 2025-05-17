import negocio.autenticacion.AutenticacionMgr;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AutenticacionMgr auth = new AutenticacionMgr();
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Registrar Cliente");
        System.out.println("2. Registrar Organizador");
        System.out.println("3. Iniciar Sesión");
        String opcion = sc.nextLine();

        switch (opcion) {
            case "1":
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Contraseña: ");
                String pass = sc.nextLine();
                System.out.print("DNI: ");
                String dni = sc.nextLine();
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                auth.registrarUsuarioCliente(email, pass, dni, nombre);
                break;

            case "2":
                System.out.print("Email: ");
                email = sc.nextLine();
                System.out.print("Contraseña: ");
                pass = sc.nextLine();
                System.out.print("Empresa: ");
                String empresa = sc.nextLine();
                System.out.print("Contacto: ");
                String contacto = sc.nextLine();
                auth.registrarOrganizador(email, pass, empresa, contacto);
                break;

            case "3":
                System.out.print("Email: ");
                email = sc.nextLine();
                System.out.print("Contraseña: ");
                pass = sc.nextLine();
                auth.iniciarSesion(email, pass);
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }
}
