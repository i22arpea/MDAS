package sistema.eventos;

import java.util.Scanner;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import modelo.Entrada;
import modelo.Evento;

import sistema.eventos.interfaces.IComprarEntrada;
import sistema.eventos.interfaces.IGestionarVenta;
import sistema.eventos.interfaces.IRealizarEvento;
import sistema.eventos.interfaces.ITramitarDevolucion;

public class EventCLI {
    private final IRealizarEvento realizarEvento;
    private final IComprarEntrada comprarEntrada;
    private final IGestionarVenta gestionarVenta;
    private final ITramitarDevolucion tramitarDevolucion;
    private final Scanner sc;
    private final String email;

    public EventCLI(IRealizarEvento realizarEvento, IComprarEntrada comprarEntrada, IGestionarVenta gestionarVenta, ITramitarDevolucion tramitarDevolucion, Scanner sc, String email) {
        this.realizarEvento = realizarEvento;
        this.comprarEntrada = comprarEntrada;
        this.gestionarVenta = gestionarVenta;
        this.tramitarDevolucion = tramitarDevolucion;
        this.sc = sc;
        this.email = email;
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- Menú de Eventos ---");
            System.out.println("1. Realizar Evento");
            System.out.println("2. Comprar Entrada");
            System.out.println("3. Gestionar Venta");
            System.out.println("4. Tramitar Devolución");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    // ...call realizarEvento...
                    System.out.println("\n--- Menú RealizarEvento ---");
                    System.out.println("1. crearEvento");
                    System.out.println("2. modificarEvento");
                    System.out.println("3. visualizarEvento");
                    System.out.println("4. buscarEvento");
                    System.out.println("0. Volver");
                    String opcion2 = sc.nextLine();

                    switch(opcion2){

                        case "1":

                            System.out.println("Titulo: ");
                            String titulo = sc.nextLine();

                            System.out.println("Categoria: ");
                            String categoria = sc.nextLine();

                            System.out.println("Direccion: ");
                            String direccion = sc.nextLine();

                            System.out.println("Politicas: ");
                            String politicas = sc.nextLine();

                            Date fechaRealizacion = new Date();
                            List<Entrada> entradas = new ArrayList<>();

                            realizarEvento.crearEvento(titulo, fechaRealizacion, categoria, entradas, direccion, politicas);

                            break;

                        case "2":

                            System.out.println("Introduzca el id del evento a modificar: ");
                            int idEvento = Integer.parseInt(sc.nextLine());

                            System.out.println("Titulo: ");
                            titulo = sc.nextLine();

                            System.out.println("Categoria: ");
                            categoria = sc.nextLine();

                            System.out.println("Direccion: ");
                            direccion = sc.nextLine();

                            System.out.println("Politicas: ");
                            politicas = sc.nextLine();

                            System.out.println("Precio evento: ");
                            double maxPrice = Double.parseDouble(sc.nextLine());

                            fechaRealizacion = new Date();
                            entradas = new ArrayList<>();

                            realizarEvento.modificarEvento(idEvento, titulo, fechaRealizacion, categoria, entradas, direccion, politicas, maxPrice);

                            break;

                        case "3":

                            realizarEvento.visualizarEventos();

                            break;

                        case "4":

                            System.out.println("Introduzca el nombre del evento a buscar: ");
                            String nombre = sc.nextLine();

                            realizarEvento.buscarEvento(nombre);

                            break;

                        case "0":

                            return;

                        default:
                            System.out.println("Opción no válida");

                    }

                    break;
                case "2":/*
                    // ...call comprarEntrada...
                    System.out.println("\n--- Menú comprarEntrada ---");
                    System.out.println("1. comprobarEntradas");
                    System.out.println("2. pagoDeEntradas");
                    System.out.println("3. generarEntradaUsuario");
                    System.out.println("0. Volver");
                    String opcion2 = sc.nextLine();

                    switch(opcion2){

                        case "1":

                            //comprobarEntradas

                            break;

                        case "2":

                            System.out.println("Indique forma de pago: ");
                            formaPago = sc.nextLine();

                            idUsuario = 1;

                            comprarEntrada.pagoDeEntradas(idUsuario, formaPago);

                            break;

                        case "3":

                            //generarEntradaUsuario

                            break;

                        case "0":

                            return;

                        default:
                            System.out.println("Opción no válida");

                    }
*/
                    break;
                case "3":/*
                    // ...call gestionarVenta...
                    System.out.println("\n--- Menú GestionarVenta ---");
                    System.out.println("1. mostrarEntradasAdquiridas");
                    System.out.println("2. comprobarPrecio");
                    System.out.println("3. publicarEntradaEnVenta");
                    System.out.println("4. modificarVenta");
                    System.out.println("5. eliminarVenta");
                    System.out.println("0. Volver");
                    opcion2 = sc.nextLine();

                    switch(opcion2){

                        case "1":

                            System.out.println("Introduzca el correo de usuario");
                            correo = sc.nextLine();
                            gestionarVenta.mostrarEntradasAdquiridas(correo);

                            break;
                        case "2":

                            System.out.println("introduzca el id del evento");
                            idEvento = sc.nextLine();

                            System.out.println("introduzca el precio de reventa");
                            posiblePrecio = sc.nextLine();
                            gestionarVenta.comprobarPrecio(idEvento, posiblePrecio);

                            break;
                        case "3":

                            System.out.println("Introduzca el id de la entrada a poner en venta");
                            idEntrada = sc.nextLine();
                            System.out.println("Introduzca el precio de reventa");
                            posiblePrecio = sc.nextLine();
                            gestionarVenta.publicarEntradaEnVenta(idEntrada, posiblePrecio);

                            break;
                        case "4":

                            System.out.println("Introduzca el id de la entrada en venta a modificar");
                            idEntrada = sc.nextLine();
                            System.out.println("Introduzca el nuevo precio de reventa");
                            posiblePrecio = sc.nextLine();
                            gestionarVenta.modificarVenta(idEntrada, posiblePrecio);

                            break;
                        case "5":

                            System.out.println("Introduzca el id de la entrada en venta a restaurar");
                            idEntrada = sc.nextLine();
                            gestionarVenta.eliminarVenta(idEntrada);

                            break;
                        case "0":
                            return;
                        default:
                            System.out.println("Opción no válida");

                    }
*/
                    break;
                case "4":/*
                    // ...call tramitarDevolucion...
                    System.out.println("\n--- Menú TramitarDevolución ---");
                    System.out.println("1. procesarDevolución");
                    System.out.println("2. enviarListadoDevolución");
                    System.out.println("3. eliminarEvento");
                    System.out.println("0. Volver");
                    opcion2 = sc.nextLine();

                    switch(opcion2){

                        case "1":

                            System.out.println("Introduzca el id del evento del cual realizar devolución");
                            idEvento = sc.nextLine();
                            tramitarDevolucion.procesarDevolucion(idEvento);

                            break;
                        case "2":

                            System.out.println("Introduzca el id del evento a realiza devolucion");
                            idEvento = sc.nextLine();
                            System.out.println("Introduzca el precio a devolver");
                            devolucion = sc.nextLine();
                            tramitarDevolucion.enviarListadoDevolución(idEvento, devolucion);

                            break;
                        case "3":
                            System.out.println("Introduzca el id del evento a eliminar del sistema (se eliminarán tambien las entradas asociadas al evento)");
                            idEvento = sc.nextLine();
                            tramitarDevolucion.eliminarEvento(idEvento);
                            break;
                        case "0":
                            return;
                        default:
                            System.out.println("Opción no válida");

                    }
*/
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
