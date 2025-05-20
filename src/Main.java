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
            System.out.println("4. Recuperar Contraseña");
            System.out.println("0. Salir");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    while (true) {
                        String email = "";
                        String pass = "";
                        String dni = "";
                        String nombre = "";
                        String apellidos = "";
                        int telefono = -1;
                        String direccion = "";
                        // Email
                        while (true) {
                            System.out.print("Email: ");
                            email = sc.nextLine();
                            if (email.contains("@") && email.contains(".")) break;
                            System.out.println("Error: El email debe contener '@' y un punto.");
                        }
                        // Contraseña
                        while (true) {
                            System.out.print("Contraseña: ");
                            pass = sc.nextLine();
                            if (pass.length() >= 6) break;
                            System.out.println("Error: La contraseña debe tener al menos 6 caracteres.");
                        }
                        // DNI
                        while (true) {
                            System.out.print("DNI: ");
                            dni = sc.nextLine();
                            if (!dni.trim().isEmpty() && dni.length() >= 7 && dni.length() <= 12) break;
                            System.out.println("Error: El DNI no puede estar vacío y debe tener entre 7 y 12 caracteres.");
                        }
                        // Nombre
                        while (true) {
                            System.out.print("Nombre: ");
                            nombre = sc.nextLine();
                            if (!nombre.trim().isEmpty()) break;
                            System.out.println("Error: El nombre no puede estar vacío.");
                        }
                        // Apellidos
                        while (true) {
                            System.out.print("Apellidos: ");
                            apellidos = sc.nextLine();
                            if (!apellidos.trim().isEmpty()) break;
                            System.out.println("Error: Los apellidos no pueden estar vacíos.");
                        }
                        // Teléfono
                        while (true) {
                            System.out.print("Telefono: ");
                            String telStr = sc.nextLine();
                            try {
                                telefono = Integer.parseInt(telStr);
                                if (telefono > 0) break;
                                System.out.println("Error: El teléfono debe ser un número positivo.");
                            } catch (NumberFormatException e) {
                                System.out.println("Error: El teléfono debe ser un número.");
                            }
                        }
                        // Dirección
                        while (true) {
                            System.out.print("Direccion: ");
                            direccion = sc.nextLine();
                            if (!direccion.trim().isEmpty()) break;
                            System.out.println("Error: La dirección no puede estar vacía.");
                        }
                        auth.crearCuenta(nombre, apellidos, email, telefono, direccion, "Usuario", null, pass, dni);
                        break;
                    }
                    break;

                case "2":
                    while (true) {
                        String email = "";
                        String pass = "";
                        String dni = "";
                        String nombre = "";
                        String apellidos = "";
                        int telefono = -1;
                        String direccion = "";
                        // Email
                        while (true) {
                            System.out.print("Email: ");
                            email = sc.nextLine();
                            if (email.contains("@") && email.contains(".")) break;
                            System.out.println("Error: El email debe contener '@' y un punto.");
                        }
                        // Contraseña
                        while (true) {
                            System.out.print("Contraseña: ");
                            pass = sc.nextLine();
                            if (pass.length() >= 6) break;
                            System.out.println("Error: La contraseña debe tener al menos 6 caracteres.");
                        }
                        // DNI
                        while (true) {
                            System.out.print("DNI: ");
                            dni = sc.nextLine();
                            if (!dni.trim().isEmpty() && dni.length() >= 7 && dni.length() <= 12) break;
                            System.out.println("Error: El DNI no puede estar vacío y debe tener entre 7 y 12 caracteres.");
                        }
                        // Nombre
                        while (true) {
                            System.out.print("Nombre: ");
                            nombre = sc.nextLine();
                            if (!nombre.trim().isEmpty()) break;
                            System.out.println("Error: El nombre no puede estar vacío.");
                        }
                        // Apellidos
                        while (true) {
                            System.out.print("Apellidos: ");
                            apellidos = sc.nextLine();
                            if (!apellidos.trim().isEmpty()) break;
                            System.out.println("Error: Los apellidos no pueden estar vacíos.");
                        }
                        // Teléfono
                        while (true) {
                            System.out.print("Telefono: ");
                            String telStr = sc.nextLine();
                            try {
                                telefono = Integer.parseInt(telStr);
                                if (telefono > 0) break;
                                System.out.println("Error: El teléfono debe ser un número positivo.");
                            } catch (NumberFormatException e) {
                                System.out.println("Error: El teléfono debe ser un número.");
                            }
                        }
                        // Dirección
                        while (true) {
                            System.out.print("Direccion: ");
                            direccion = sc.nextLine();
                            if (!direccion.trim().isEmpty()) break;
                            System.out.println("Error: La dirección no puede estar vacía.");
                        }
                        auth.crearCuenta(nombre, apellidos, email, telefono, direccion, "Organizador", null, pass, dni);
                        break;
                    }
                    break;

                case "3":
                    while (true) {
                        String email = "";
                        String pass = "";
                        // Email
                        while (true) {
                            System.out.print("Email: ");
                            email = sc.nextLine();
                            if (email.contains("@") && email.contains(".")) break;
                            System.out.println("Error: El email debe contener '@' y un punto.");
                        }
                        // Contraseña
                        while (true) {
                            System.out.print("Contraseña: ");
                            pass = sc.nextLine();
                            if (pass.length() >= 6) break;
                            System.out.println("Error: La contraseña debe tener al menos 6 caracteres.");
                        }
                        String tipoUsuario = auth.iniciarSesion(email, pass);
                        if (tipoUsuario == null) {
                            System.out.println("Error: credenciales incorrectas o usuario bloqueado. ¿Deseas intentarlo de nuevo? (s/n)");
                            String retry = sc.nextLine();
                            if (!retry.equalsIgnoreCase("s")) break;
                        } else if (tipoUsuario.equalsIgnoreCase("Administrador")) {
                            System.out.println("Inicio de sesión de administrador exitoso. Accediendo al menú de administración...");
                            sistema.autenticacion.AuthService authService = new sistema.autenticacion.AuthService();
                            sistema.autenticacion.AdminCLI menuAdmin = new sistema.autenticacion.AdminCLI(
                                authService, // IGestionarCuenta
                                sc
                            );
                            menuAdmin.mostrarMenu();
                            break;
                        } else {
                            System.out.println("Inicio de sesión exitoso. Accediendo al menú de usuario...");
                            sistema.autenticacion.AuthService authService = new sistema.autenticacion.AuthService();
                            boolean salirUsuario = false;
                            while (!salirUsuario) {
                                System.out.println("\n--- Menú Usuario ---");
                                System.out.println("1. Perfil de usuario");
                                System.out.println("2. Gestión de eventos");
                                System.out.println("0. Cerrar sesión");
                                String opcionUsuario = sc.nextLine();
                                switch (opcionUsuario) {
                                    case "1":
                                        sistema.autenticacion.AuthCLI menuPerfil = new sistema.autenticacion.AuthCLI(
                                            authService, // IPerfilUsuario
                                            authService, // IGestionarCuenta
                                            sc,
                                            email // email autenticado
                                        );
                                        menuPerfil.mostrarMenu();
                                        break;
                                    case "2":
                                        if (tipoUsuario.equalsIgnoreCase("Organizador")) {
                                            // Menú para organizador
                                            sistema.eventos.EventService eventService = new sistema.eventos.EventService();
                                            sistema.eventos.EventCLI menuEvento = new sistema.eventos.EventCLI(
                                                eventService, // IRealizarEvento
                                                eventService, // IComprarEntrada
                                                eventService, // IGestionarVenta
                                                eventService, // ITramitarDevolucion
                                                sc,
                                                email // email autenticado
                                            );
                                            menuEvento.mostrarMenuOrganizador();
                                        } else if (tipoUsuario.equalsIgnoreCase("Usuario")) {
                                            // Menú para usuario (gestión de entradas)
                                            sistema.eventos.EventService eventService = new sistema.eventos.EventService();
                                            sistema.eventos.EventCLI menuEvento = new sistema.eventos.EventCLI(
                                                eventService, // IRealizarEvento
                                                eventService, // IComprarEntrada
                                                eventService, // IGestionarVenta
                                                eventService, // ITramitarDevolucion
                                                sc,
                                                email // email autenticado
                                            );
                                            menuEvento.mostrarMenuUsuario();
                                        } else {
                                            System.out.println("No tienes permisos para gestionar eventos.");
                                        }
                                        break;
                                    case "0":
                                        salirUsuario = true;
                                        System.out.println("Sesión cerrada.");
                                        break;
                                    default:
                                        System.out.println("Opción no válida.");
                                }
                            }
                            break;
                        }
                    }
                    break;

                case "4":
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
