package sistema.eventos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import modelo.Entrada;
import modelo.Evento;
import modelo.Usuario;
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

                            //realizarEvento.visualizarEventos();

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

    public void mostrarMenuPorTipoUsuario(String tipoUsuario) {
        if ("Organizador".equalsIgnoreCase(tipoUsuario)) {
            mostrarMenuOrganizador();
        } else if ("Usuario".equalsIgnoreCase(tipoUsuario)) {
            mostrarMenuUsuario();
        } else {
            System.out.println("Tipo de usuario no soportado en menú de eventos.");
        }
    }

    public void mostrarMenuUsuario() {
        while (true) {
            System.out.println("\n--- Eventos Disponibles ---");
            // Mostrar todos los eventos disponibles
            List<modelo.Evento> eventos = realizarEvento.obtenerEventos();
            if (eventos == null || eventos.isEmpty()) {
                System.out.println("No hay eventos disponibles.");
                System.out.println("0. Volver");
                String opcion = sc.nextLine();
                if ("0".equals(opcion)) return;
                continue;
            }
            for (int i = 0; i < eventos.size(); i++) {
                modelo.Evento evento = eventos.get(i);
                System.out.println((i+1) + ". " + evento.getTitulo() + " - " + evento.getFechaRealizacion());
            }
            System.out.println("0. Volver");
            System.out.print("Seleccione un evento: ");
            String opcion = sc.nextLine();
            if ("0".equals(opcion)) return;
            int idx;
            try {
                idx = Integer.parseInt(opcion) - 1;
                if (idx < 0 || idx >= eventos.size()) {
                    System.out.println("Opción no válida.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida.");
                continue;
            }
            modelo.Evento eventoSeleccionado = eventos.get(idx);
            mostrarMenuEventoUsuario(eventoSeleccionado);
        }
    }

    private void mostrarMenuEventoUsuario(modelo.Evento evento) {
        while (true) {
            System.out.println("\n--- Información del Evento ---");
            System.out.println("Título: " + evento.getTitulo());
            System.out.println("Fecha: " + evento.getFechaRealizacion());
            System.out.println("Categoría: " + evento.getCategoria());
            System.out.println("Dirección: " + evento.getDireccion());
            System.out.println("Políticas: " + evento.getPoliticas());
            System.out.println("\n1. Comprar entrada");
            System.out.println("2. Gestionar venta");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    mostrarSubmenuComprarEntrada(evento); //PARA PODER ACCEDER AL ID DEL EVENTO DESDE EL SUBMENU
                    break;
                case "2":
                    mostrarSubmenuGestionarVenta(evento); //PARA PODER ACCEDER AL ID DEL EVENTO DESDE EL SUBMENU
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public void mostrarMenuOrganizador() {
        while (true) {
            System.out.println("\n--- Menú Organizador ---");
            System.out.println("1. Realizar evento");
            System.out.println("2. Tramitar devolución");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    mostrarSubmenuRealizarEvento();
                    break;
                case "2":
                    mostrarSubmenuTramitarDevolucion();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Submenús específicos
    private void mostrarSubmenuRealizarEvento() {
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
                //realizarEvento.visualizarEventos();
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
    }

    private void mostrarSubmenuTramitarDevolucion() {
        System.out.println("\n--- Menú Gestionar Devolución ---");
        System.out.println("(Funcionalidad de gestionar devolución aquí)");
        // Aquí puedes implementar el menú real de devoluciones
    }

    private void mostrarSubmenuComprarEntrada(Evento eventoSeleccionado) {
        System.out.println("\n--- Menú Comprar Entrada ---\n");
        // Aquí puedes implementar el menú real de compra de entradas
        System.out.println("Cantidad de entradas a comprar:");
        int cantidadEntradas = Integer.parseInt(sc.nextLine()); //se pide el numero de entradas a comprar
        //se comprueba si hay entradas disponibles
        
        int disponibles = 0;
        for(Entrada entrada : eventoSeleccionado.getListEntradas()){
            if(!entrada.getEstadoEntrada().equals("Vendida")){
                disponibles++;
            }
        }

        if(disponibles >= cantidadEntradas){

            //se calcula y muestra el precio total
            double precioTotal = eventoSeleccionado.getMaxPrice() * cantidadEntradas;
            System.out.println("\nPrecio total: " + precioTotal + "€\n");
            //se pregunta metodo de pago
            System.out.println("Método de pago: ");
            String metodoPago = sc.nextLine();

            System.out.println("Datos recodigos: " + cantidadEntradas + " entradas compradas para el evento " + eventoSeleccionado.getTitulo() + " por un total de " + precioTotal + "€ con el método de pago " + metodoPago);

            ArrayList<Entrada> entradasParaUsuario = new ArrayList<>();
            for(Entrada entrada : eventoSeleccionado.getListEntradas()){
                if(!entrada.getEstadoEntrada().equals("Vendida")){
                    entradasParaUsuario.add(entrada);
                }
            }

            //comprobamos que las entradas seleccionadas son válidas
            boolean isValid = comprarEntrada.comprobarEntradas(entradasParaUsuario);

            if(isValid){

                boolean isPagoValid = comprarEntrada.pagoDeEntradas(email, metodoPago, precioTotal);

                if(isPagoValid){

                    for(Entrada entrada : entradasParaUsuario){
                        
                        Entrada entradaGenerada = comprarEntrada.generarEntradaUsuario(entrada, email);
                        //se asigna la entrada generada al usuario
                        List<Entrada> listaEntradas = eventoSeleccionado.getListEntradas();
                        for(Entrada entrada1 : listaEntradas){
                            if(entradaGenerada.getIdEntrada() == entrada1.getIdEntrada()){
                                entrada1.setEstadoEntrada("Vendida");
                                entrada1.setCorreoAsociado(email);
                            }
                        }
                        eventoSeleccionado.setListaEntradas(listaEntradas);

                        //GUARDAR CAMBIOS EN EL JSON DE ENTRADAS
                        try {
                            eventoSeleccionado.guardarEventoActualizado();
                        } catch (Exception e) {
                            System.out.println("Error al guardar el evento: " + e.getMessage());
                            e.printStackTrace();
                        }

                    }

                }else{

                    System.out.println("Error al realizar el pago.");

                }

                    

            }else{

                System.out.println("Ha ocurrido un error al seleccionar entradas disponibles\n");

            }

        }else{

            System.out.println("No hay suficientes entradas disponibles para el evento " + eventoSeleccionado.getTitulo());
            System.out.println("Entradas disponibles: " + disponibles);

            //volver al menu anterior

        }
        

        

        //FUNCIONALIDADES DE LA INTERFAZCOMPRAR ENTRADA



    }

    private void mostrarSubmenuGestionarVenta(Evento eventoSeleccionado) {
        System.out.println("\n--- Menú Gestionar Venta ---");
        System.out.println("(Funcionalidad de gestionar venta aquí)");
        // Aquí puedes implementar el menú real de gestión de venta
    }
}
