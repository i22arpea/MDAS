package sistema.eventos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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



    /**
     * Muestra el menú de eventos para usuarios.
     */
    public void mostrarMenuUsuario() {
        while (true) {
            System.out.println("\n--- Eventos Disponibles ---");
            List<Evento> eventos = realizarEvento.obtenerEventos();
            if (eventos == null || eventos.isEmpty()) {
                System.out.println("No hay eventos disponibles.");
                System.out.println("0. Volver");
                String opcion = sc.nextLine();
                if ("0".equals(opcion)) return;
                continue;
            }
            for (int i = 0; i < eventos.size(); i++) {
                Evento evento = eventos.get(i);
                System.out.println((i + 1) + ". " + evento.getTitulo() + " - " + evento.getFechaRealizacion());
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
            Evento eventoSeleccionado = eventos.get(idx);
            mostrarMenuEventoUsuario(eventoSeleccionado);
        }
    }

    /**
     * Muestra el menú de eventos para organizadores.
     */
    private void mostrarMenuEventoUsuario(Evento evento) {
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
                    mostrarSubmenuComprarEntrada(evento);
                    break;
                case "2":
                    mostrarSubmenuGestionarVenta(evento);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Muestra el menú de eventos para organizadores.
     */
    public void mostrarMenuOrganizador() {
        while (true) {
            System.out.println("\n--- Menú Organizador ---");
            System.out.println("1. Crear evento");
            System.out.println("2. Modificar evento");
            System.out.println("3. Visualizar eventos");
            System.out.println("4. Buscar evento");
            System.out.println("5. Eliminar evento");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
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
                    // Mostrar listado de eventos antes de pedir el ID
                    List<Evento> eventosModificar = realizarEvento.obtenerEventos();
                    if (eventosModificar == null || eventosModificar.isEmpty()) {
                        System.out.println("No hay eventos disponibles para modificar.");
                        break;
                    }
                    System.out.println("Eventos disponibles:");
                    for (Evento ev : eventosModificar) {
                        System.out.println("ID: " + ev.getIdEvento() + ", Título: " + ev.getTitulo() + ", Fecha: " + ev.getFechaRealizacion());
                    }
                    System.out.println("Introduzca el id del evento a modificar (0 para volver): ");
                    String inputIdEvento = sc.nextLine();
                    if ("0".equals(inputIdEvento)) break;
                    int idEvento = Integer.parseInt(inputIdEvento);
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
                case "5":
                    System.out.println("Introduzca el id del evento a eliminar: ");
                    int idEventoEliminar;
                    try {
                        idEventoEliminar = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID no válido.");
                        break;
                    }
                    double devolucion = tramitarDevolucion.procesarDevolucion(idEventoEliminar);
                    System.out.println("Total a devolver por entradas: " + devolucion + "€");
                    boolean enviado = tramitarDevolucion.enviarListadoDevolucion(idEventoEliminar, devolucion);
                    if (enviado) {
                        boolean eliminado = tramitarDevolucion.eliminarEvento(idEventoEliminar);
                        if (eliminado) {
                            System.out.println("Evento eliminado correctamente.");
                        } else {
                            System.out.println("No se pudo eliminar el evento.");
                        }
                    } else {
                        System.out.println("No se pudo tramitar la devolución. El evento no se eliminará.");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Muestra el menú de compra de entradas.
     *
     * @param eventoSeleccionado El evento seleccionado por el usuario.
     */
    private void mostrarSubmenuComprarEntrada(Evento eventoSeleccionado) {
        System.out.println("\n--- Menú Comprar Entrada ---\n");
        // Cargar la lista de entradas actualizadas desde el JSON
        List<Entrada> entradasActualizadas;
        try {
            entradasActualizadas = eventoSeleccionado.cargarEntradasDesdeJson();
        } catch (Exception e) {
            System.out.println("Error al leer las entradas del evento: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        // Mostrar tipos de entrada disponibles y sus cantidades
        java.util.Map<String, Integer> tiposDisponibles = new java.util.HashMap<>();
        for (Entrada entrada : entradasActualizadas) {
            if (!"Vendida".equals(entrada.getEstadoEntrada())) {
                tiposDisponibles.put(entrada.getTipoEntrada(), tiposDisponibles.getOrDefault(entrada.getTipoEntrada(), 0) + 1);
            }
        }
        if (tiposDisponibles.isEmpty()) {
            System.out.println("No hay entradas disponibles para este evento.");
            return;
        }
        System.out.println("Tipos de entrada disponibles:");
        int idxTipo = 1;
        java.util.List<String> tipos = new java.util.ArrayList<>();
        for (String tipo : tiposDisponibles.keySet()) {
            System.out.println(idxTipo + ". " + tipo + " (" + tiposDisponibles.get(tipo) + ")");
            tipos.add(tipo);
            idxTipo++;
        }
        System.out.println("0. Volver");
        int opcionTipo = -1;
        while (opcionTipo < 0 || opcionTipo > tipos.size()) {
            System.out.print("Seleccione el tipo de entrada: ");
            try {
                opcionTipo = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcionTipo = -1;
            }
            if (opcionTipo == 0) return;
            if (opcionTipo < 1 || opcionTipo > tipos.size()) {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        String tipoSeleccionado = tipos.get(opcionTipo - 1);
        int maxDisponibles = tiposDisponibles.get(tipoSeleccionado);
        int cantidadEntradas = 0;
        while (cantidadEntradas < 1 || cantidadEntradas > maxDisponibles) {
            System.out.print("¿Cuántas entradas de tipo '" + tipoSeleccionado + "' desea comprar? (máx " + maxDisponibles + ", 0 para volver): ");
            try {
                cantidadEntradas = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                cantidadEntradas = 0;
            }
            if (cantidadEntradas == 0) return;
            if (cantidadEntradas < 1 || cantidadEntradas > maxDisponibles) {
                System.out.println("Cantidad no válida. Intente de nuevo.");
            }
        }
        double precioUnitario = 0;
        for (Entrada entrada : entradasActualizadas) {
            if (tipoSeleccionado.equals(entrada.getTipoEntrada())) {
                precioUnitario = entrada.getPrecio();
                break;
            }
        }
        double precioTotal = precioUnitario * cantidadEntradas;
        System.out.println("\nPrecio total: " + precioTotal + "€\n");
        // Opciones de método de pago
        String[] metodos = {"Tarjeta", "Bizum", "PayPal"};
        System.out.println("Seleccione método de pago:");
        for (int i = 0; i < metodos.length; i++) {
            System.out.println((i + 1) + ". " + metodos[i]);
        }
        System.out.println("0. Volver");
        int opcionPago = -1;
        while (opcionPago < 0 || opcionPago > metodos.length) {
            System.out.print("Opción: ");
            try {
                opcionPago = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcionPago = -1;
            }
            if (opcionPago == 0) return;
            if (opcionPago < 1 || opcionPago > metodos.length) {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        String metodoPago = metodos[opcionPago - 1];
        System.out.println("Datos recogidos: " + cantidadEntradas + " entradas de tipo '" + tipoSeleccionado + "' compradas para el evento " + eventoSeleccionado.getTitulo() + " por un total de " + precioTotal + "€ con el método de pago " + metodoPago);
        List<Entrada> entradasParaUsuario = new ArrayList<>();
        for (Entrada entrada : entradasActualizadas) {
            if (!"Vendida".equals(entrada.getEstadoEntrada()) && tipoSeleccionado.equals(entrada.getTipoEntrada())) {
                entradasParaUsuario.add(entrada);
                if (entradasParaUsuario.size() == cantidadEntradas) break;
            }
        }
        boolean isValid = comprarEntrada.comprobarEntradas(entradasParaUsuario);
        if (!isValid) {
            System.out.println("Ha ocurrido un error al seleccionar entradas disponibles\n");
            return;
        }
        boolean isPagoValid = comprarEntrada.pagoDeEntradas(email, metodoPago, precioTotal);
        if (!isPagoValid) {
            System.out.println("Error al realizar el pago.");
            return;
        }
        // Sincroniza la lista de entradas del evento con la lista actualizada antes de guardar
        eventoSeleccionado.setListaEntradas(entradasActualizadas);
        for (Entrada entrada : entradasParaUsuario) {
            entrada.setEstadoEntrada("Vendida");
            entrada.setCorreoAsociado(email);
            // Actualiza la entrada en el JSON de entradas
            comprarEntrada.generarEntradaUsuario(entrada, email);
        }
        try {
            eventoSeleccionado.guardarEventoActualizado(); // Actualiza el evento y sus entradas en eventos.json
        } catch (Exception e) {
            System.out.println("Error al guardar el evento: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Compra realizada con éxito.");
    }

    private void mostrarSubmenuGestionarVenta(Evento eventoSeleccionado) {
        while (true) {
            System.out.println("\n--- Menú Gestionar Venta ---");
            System.out.println("1. Ver mis entradas para este evento");
            System.out.println("2. Poner entrada en reventa");
            System.out.println("3. Modificar precio de reventa");
            System.out.println("4. Cancelar reventa de entrada");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    try {
                        eventoSeleccionado.setListaEntradas(eventoSeleccionado.cargarEntradasDesdeJson());
                    } catch (Exception e) {
                        System.out.println("Error al recargar las entradas: " + e.getMessage());
                    }
                    System.out.println("Entradas asociadas a este evento:");
                    for (Entrada entrada : eventoSeleccionado.getListEntradas()) {
                        if (email.equals(entrada.getCorreoAsociado())) {
                            System.out.println("ID: " + entrada.getIdEntrada() + ", Tipo: " + entrada.getTipoEntrada() + ", Precio: " + entrada.getPrecio() + "€, Estado: " + entrada.getEstadoEntrada());
                        }
                    }
                    break;
                case "2":
                    System.out.println("ID de la entrada a poner en reventa (0 para volver):");
                    String inputReventa = sc.nextLine();
                    if ("0".equals(inputReventa)) break;
                    int idEntradaReventa = Integer.parseInt(inputReventa);
                    System.out.println("Precio de reventa (0 para volver):");
                    String inputPrecio = sc.nextLine();
                    if ("0".equals(inputPrecio)) break;
                    double precioReventa = Double.parseDouble(inputPrecio);
                    boolean publicada = gestionarVenta.publicarEntradaEnVenta(idEntradaReventa, precioReventa);
                    if (publicada) {
                        try {
                            eventoSeleccionado.setListaEntradas(eventoSeleccionado.cargarEntradasDesdeJson());
                            eventoSeleccionado.guardarEventoActualizado();
                        } catch (Exception e) {
                            System.out.println("Error al actualizar el evento tras poner en reventa: " + e.getMessage());
                        }
                        System.out.println("Entrada puesta en reventa correctamente.");
                    } else {
                        System.out.println("No se pudo poner la entrada en reventa (verifica el ID y el precio máximo permitido).");
                    }
                    break;
                case "3":
                    // Mostrar solo las entradas en reventa del usuario para este evento antes de modificar
                    List<Entrada> entradasReventaMod = new ArrayList<>();
                    for (Entrada entrada : eventoSeleccionado.getListEntradas()) {
                        if (email.equals(entrada.getCorreoAsociado()) && "En Reventa".equalsIgnoreCase(entrada.getEstadoEntrada())) {
                            entradasReventaMod.add(entrada);
                        }
                    }
                    if (entradasReventaMod.isEmpty()) {
                        System.out.println("No tienes entradas en reventa para este evento.");
                        break;
                    }
                    System.out.println("Entradas en reventa para modificar:");
                    for (Entrada entrada : entradasReventaMod) {
                        System.out.println("ID: " + entrada.getIdEntrada() + ", Tipo: " + entrada.getTipoEntrada() + ", Precio: " + entrada.getPrecio() + "€, Estado: " + entrada.getEstadoEntrada());
                    }
                    System.out.println("ID de la entrada en reventa a modificar (0 para volver):");
                    String inputMod = sc.nextLine();
                    if ("0".equals(inputMod)) break;
                    int idEntradaMod = Integer.parseInt(inputMod);
                    System.out.println("Nuevo precio de reventa (0 para volver):");
                    String inputNuevoPrecio = sc.nextLine();
                    if ("0".equals(inputNuevoPrecio)) break;
                    double nuevoPrecio = Double.parseDouble(inputNuevoPrecio);
                    boolean modificado = gestionarVenta.modificarVenta(idEntradaMod, nuevoPrecio);
                    if (modificado) {
                        try {
                            eventoSeleccionado.setListaEntradas(eventoSeleccionado.cargarEntradasDesdeJson());
                            eventoSeleccionado.guardarEventoActualizado();
                        } catch (Exception e) {
                            System.out.println("Error al actualizar el evento tras modificar reventa: " + e.getMessage());
                        }
                        System.out.println("Precio de reventa modificado correctamente.");
                    } else {
                        System.out.println("No se pudo modificar el precio (verifica el ID y que la entrada esté en reventa).");
                    }
                    break;
                case "4":
                    // Mostrar solo las entradas en reventa del usuario para este evento
                    List<Entrada> entradasReventa = new ArrayList<>();
                    for (Entrada entrada : eventoSeleccionado.getListEntradas()) {
                        if (email.equals(entrada.getCorreoAsociado()) && "En Reventa".equalsIgnoreCase(entrada.getEstadoEntrada())) {
                            entradasReventa.add(entrada);
                        }
                    }
                    if (entradasReventa.isEmpty()) {
                        System.out.println("No tienes entradas en reventa para este evento.");
                        break;
                    }
                    System.out.println("Entradas en reventa:");
                    for (Entrada entrada : entradasReventa) {
                        System.out.println("ID: " + entrada.getIdEntrada() + ", Tipo: " + entrada.getTipoEntrada() + ", Precio: " + entrada.getPrecio() + "€, Estado: " + entrada.getEstadoEntrada());
                    }
                    System.out.println("ID de la entrada en reventa a cancelar (0 para volver):");
                    String inputElim = sc.nextLine();
                    if ("0".equals(inputElim)) break;
                    int idEntradaElim = Integer.parseInt(inputElim);
                    boolean eliminado = gestionarVenta.eliminarVenta(idEntradaElim);
                    if (eliminado) {
                        System.out.println("Reventa cancelada, la entrada vuelve a estar en tu poder.");
                    } else {
                        System.out.println("No se pudo cancelar la reventa (verifica el ID y que esté en reventa).");
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
