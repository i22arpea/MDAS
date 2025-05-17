// File: Main.java
import java.util.Scanner;

import negocio.autenticacion.AutenticacionMgr;

public class Main {
    public static void main(String[] args) {
        AutenticacionMgr auth = new AutenticacionMgr();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Organizador");
            System.out.println("3. Iniciar Sesión");
            System.out.println("4. Iniciar Sesión como Administrador");
            System.out.println("5. Recuperar Contraseña");
            System.out.println("0. Salir");
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
                    System.out.print("Apellidos: ");
                    String apellidos = sc.nextLine();
                    System.out.print("Telefono: ");
                    int telefono = Integer.parseInt(sc.nextLine());
                    System.out.print("Direccion: ");
                    String direccion = sc.nextLine();
                    auth.crearCuenta(nombre, apellidos, email, telefono, direccion, "Usuario", null, pass);
                    break;

                case "2":
                    System.out.print("Email: ");
                    email = sc.nextLine();
                    System.out.print("Contraseña: ");
                    pass = sc.nextLine();
                    System.out.print("DNI: ");
                    dni = sc.nextLine();
                    System.out.print("Nombre: ");
                    nombre = sc.nextLine();
                    System.out.print("Apellidos: ");
                    apellidos = sc.nextLine();
                    System.out.print("Telefono: ");
                    telefono = Integer.parseInt(sc.nextLine());
                    System.out.print("Direccion: ");
                    direccion = sc.nextLine();
                    auth.crearCuenta(nombre, apellidos, email, telefono, direccion, "Organizador", null, pass);
                    break;

                case "3":
                    System.out.print("Email: ");
                    email = sc.nextLine();
                    System.out.print("Contraseña: ");
                    pass = sc.nextLine();
                    boolean sesionIniciada = auth.iniciarSesion(email, pass);
                    if (sesionIniciada) {
                        System.out.println("Inicio de sesión exitoso. Accediendo al menú de perfil...");
                        // Llamada al menú de perfil pasando el email autenticado
                        sistema.autenticacion.AuthService authService = new sistema.autenticacion.AuthService();
                        sistema.autenticacion.AuthCLI menuPerfil = new sistema.autenticacion.AuthCLI(
                            authService, // IPerfilUsuario
                            authService, // IGestionarCuenta
                            sc,
                            email // email autenticado
                        );
                        menuPerfil.mostrarMenu();
                    } else {
                        System.out.println("Error: credenciales incorrectas o usuario bloqueado.");
                    }
                    break;

                case "4":
                    System.out.print("Email de administrador: ");
                    String adminEmail = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String adminPass = sc.nextLine();
                    boolean adminSesion = auth.iniciarSesion(adminEmail, adminPass);
                    if (adminSesion) {
                        System.out.println("Inicio de sesión de administrador exitoso. Accediendo al menú de administración...");
                        // Aquí deberías llamar al menú de administración
                        // Por ejemplo:
                        // MenuAdmin menuAdmin = new MenuAdmin(auth, sc, adminEmail);
                        // menuAdmin.mostrarMenu();
                    } else {
                        System.out.println("Error: credenciales de administrador incorrectas o usuario bloqueado.");
                    }
                    break;

                case "5":
                    System.out.print("Email: ");
                    String emailRec = sc.nextLine();
                    sistema.autenticacion.AuthService authServiceRec = new sistema.autenticacion.AuthService();
                    authServiceRec.recuperarContrasena(emailRec);
                    break;

                case "0":
                    System.out.println("Saliendo...");
                    return;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
