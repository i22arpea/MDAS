package negocio.evento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

import modelo.Entrada;
import java.util.Date;

public class IEventoMgr extends IEventoMgt {

    private static final String USUARIO_JSON = "usuarios.json";
    private static final String EVENTOS_JSON = "eventos.json";

    public IEventoMgr() {
        try {
            if (!Files.exists(Paths.get(USUARIO_JSON))) {
                Files.write(Paths.get(USUARIO_JSON), "[]".getBytes());
            }
            if (!Files.exists(Paths.get(EVENTOS_JSON))) {
                Files.write(Paths.get(EVENTOS_JSON), "[]".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //IComprarEntrada
    @Override
    public boolean comprobarEntradas(List<Entrada> entradasAdquirir) {
        for (Entrada entrada : entradasAdquirir) {
            if ("Vendida".equalsIgnoreCase(entrada.getEstadoEntrada()) || !entrada.getCorreoAsociado().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean pagoDeEntradas(String correoUser, String formaPago, double precio) {
        System.out.println("El usuario con correo " + correoUser + " está realizando el pago por " + precio + " con el método " + formaPago);
        return true;
    }

    @Override
    public Entrada generarEntradaUsuario(Entrada entradaAdquirida, String correoUsuario) {
        entradaAdquirida.setCorreoAsociado(correoUsuario);
        entradaAdquirida.setEstadoEntrada("Vendida");
        actualizarEntradaEnEventoJson(entradaAdquirida);
        return entradaAdquirida;
    }

    // Método auxiliar para actualizar la entrada en el array de entradas del evento en eventos.json
    private void actualizarEntradaEnEventoJson(Entrada entrada) {
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                if (eventoJson.getInt("idEvento") == entrada.getIdEvento()) {
                    JSONArray entradasEvento = eventoJson.getJSONArray("entradas");
                    boolean updated = false;
                    for (int j = 0; j < entradasEvento.length(); j++) {
                        JSONObject entradaJson = entradasEvento.getJSONObject(j);
                        if (entradaJson.getInt("idEntrada") == entrada.getIdEntrada()) {
                            entradaJson.put("correoAsociado", entrada.getCorreoAsociado());
                            entradaJson.put("estadoEntrada", entrada.getEstadoEntrada());
                            updated = true;
                            break;
                        }
                    }
                    if (!updated) {
                        JSONObject nuevaEntrada = new JSONObject();
                        nuevaEntrada.put("idEntrada", entrada.getIdEntrada());
                        nuevaEntrada.put("idEvento", entrada.getIdEvento());
                        nuevaEntrada.put("precio", entrada.getPrecio());
                        nuevaEntrada.put("tipoEntrada", entrada.getTipoEntrada());
                        nuevaEntrada.put("estadoEntrada", entrada.getEstadoEntrada());
                        nuevaEntrada.put("correoAsociado", entrada.getCorreoAsociado());
                        entradasEvento.put(nuevaEntrada);
                    }
                    break;
                }
            }
            try (FileWriter writer = new FileWriter(EVENTOS_JSON)) {
                writer.write(eventosArray.toString(4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //IGestionarVenta
    @Override
    public String mostrarEntradasAdquiridas(String correo) {
        List<Entrada> entradasUsuario = new ArrayList<>();
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                JSONArray entradasEvento = eventoJson.getJSONArray("entradas");
                for (int j = 0; j < entradasEvento.length(); j++) {
                    JSONObject entradaJson = entradasEvento.getJSONObject(j);
                    if (correo.equalsIgnoreCase(entradaJson.optString("correoAsociado"))) {
                        Entrada entrada = new Entrada(
                                entradaJson.getInt("idEntrada"),
                                entradaJson.getInt("idEvento"),
                                entradaJson.getString("tipoEntrada"),
                                entradaJson.getDouble("precio")
                        );
                        entrada.setEstadoEntrada(entradaJson.getString("estadoEntrada"));
                        entrada.setCorreoAsociado(entradaJson.getString("correoAsociado"));
                        entradasUsuario.add(entrada);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (Entrada entrada : entradasUsuario) {
            sb.append(entrada).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean comprobarPrecio(int idEvento, double posiblePrecio) {
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);

            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject evento = eventosArray.getJSONObject(i);

                if (evento.getInt("idEvento") == idEvento) {
                    double precioMaximo = evento.getDouble("maxPrice");
                    return posiblePrecio <= precioMaximo;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // si no se encuentra el evento o hay error, consideramos que no es válido
    }

    @Override
    public boolean publicarEntradaEnVenta(int idEntrada, double precio) {
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                JSONArray entradasEvento = eventoJson.getJSONArray("entradas");
                for (int j = 0; j < entradasEvento.length(); j++) {
                    JSONObject entradaJson = entradasEvento.getJSONObject(j);
                    if (entradaJson.getInt("idEntrada") == idEntrada) {
                        int idEvento = entradaJson.getInt("idEvento");
                        // Validar precio
                        if (!comprobarPrecio(idEvento, precio)) {
                            return false;
                        }
                        // Cambiar estado y precio
                        entradaJson.put("estadoEntrada", "En Reventa");
                        entradaJson.put("precio", precio);
                        // Guardar el nuevo array en el fichero
                        try (FileWriter writer = new FileWriter(EVENTOS_JSON)) {
                            writer.write(eventosArray.toString(4));
                        }
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // entrada no encontrada o error
    }

    @Override
    public boolean modificarVenta(int idEntrada, double precio) {
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                JSONArray entradasEvento = eventoJson.getJSONArray("entradas");
                for (int j = 0; j < entradasEvento.length(); j++) {
                    JSONObject entradaJson = entradasEvento.getJSONObject(j);
                    if (entradaJson.getInt("idEntrada") == idEntrada) {
                        String estado = entradaJson.getString("estadoEntrada");
                        if (!"En Reventa".equalsIgnoreCase(estado)) {
                            return false; // Solo modificar si está en reventa
                        }
                        int idEvento = entradaJson.getInt("idEvento");
                        if (!comprobarPrecio(idEvento, precio)) {
                            return false; // Precio no válido
                        }
                        // Modificar precio
                        entradaJson.put("precio", precio);
                        // Guardar los cambios en el JSON
                        try (FileWriter writer = new FileWriter(EVENTOS_JSON)) {
                            writer.write(eventosArray.toString(4));
                        }
                        return true; // Modificado correctamente
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // No encontrada o error
    }

    @Override
    public boolean eliminarVenta(int idEntrada) {
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                JSONArray entradasEvento = eventoJson.getJSONArray("entradas");
                for (int j = 0; j < entradasEvento.length(); j++) {
                    JSONObject entradaJson = entradasEvento.getJSONObject(j);
                    if (entradaJson.getInt("idEntrada") == idEntrada) {
                        String estado = entradaJson.getString("estadoEntrada");
                        if (!"En Reventa".equalsIgnoreCase(estado)) {
                            return false; // Solo se puede cancelar si está en reventa
                        }
                        // Cambiar estado a Vendida
                        entradaJson.put("estadoEntrada", "Vendida");
                        // Guardar los cambios
                        try (FileWriter writer = new FileWriter(EVENTOS_JSON)) {
                            writer.write(eventosArray.toString(4));
                        }
                        return true; // Cancelación correcta
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Entrada no encontrada o error
    }
    

    //IRealizarEvento
    @Override
    public boolean crearEvento(String titulo, Date fechaRealizacion, String categoria, List<Entrada> entradas, String direccion, String politicas) {
        try {
            // Leer eventos existentes
            String eventosContenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(eventosContenido);

            // Crear un idEvento nuevo (único), por ejemplo max(idEvento) + 1 o UUID
            int nuevoIdEvento = 1;
            for (int i = 0; i < eventosArray.length(); i++) {
                int idExistente = eventosArray.getJSONObject(i).getInt("idEvento");
                if (idExistente >= nuevoIdEvento) {
                    nuevoIdEvento = idExistente + 1;
                }
            }

            // Formatear la fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String fechaFormateada = sdf.format(fechaRealizacion);

            // --- NUEVO: Pedir número de entradas y crearlas ---
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el número de entradas para el evento: ");
            int numEntradas = Integer.parseInt(sc.nextLine());
            List<Entrada> nuevasEntradas = new ArrayList<>();
            for (int i = 1; i <= numEntradas; i++) {
                System.out.print("Tipo de entrada para la entrada " + i + ": ");
                String tipoEntrada = sc.nextLine();
                System.out.print("Precio para la entrada " + i + ": ");
                double precio = Double.parseDouble(sc.nextLine());
                Entrada entrada = new Entrada(i, nuevoIdEvento, tipoEntrada, precio);
                nuevasEntradas.add(entrada);
            }
            entradas = nuevasEntradas;
            // --- FIN NUEVO ---
            // Calcular maxPrice a partir de las entradas (por ejemplo el mayor precio)
            double maxPrice = 0;
            for (Entrada e : entradas) {
                if (e.getPrecio() > maxPrice) {
                    maxPrice = e.getPrecio();
                }
            }

            // Crear JSONObject para el nuevo evento
            JSONObject nuevoEvento = new JSONObject();
            nuevoEvento.put("idEvento", nuevoIdEvento);
            nuevoEvento.put("titulo", titulo);
            nuevoEvento.put("fechaRealizacion", fechaFormateada);
            nuevoEvento.put("categoria", categoria);
            nuevoEvento.put("direccion", direccion);
            nuevoEvento.put("politicas", politicas);
            nuevoEvento.put("maxPrice", maxPrice);
            // Añadir listado de entradas completas al evento
            JSONArray entradasJson = new JSONArray();
            for (Entrada e : entradas) {
                JSONObject entradaJson = new JSONObject();
                entradaJson.put("idEntrada", e.getIdEntrada());
                entradaJson.put("idEvento", nuevoIdEvento);
                entradaJson.put("correoAsociado", e.getCorreoAsociado());
                entradaJson.put("tipoEntrada", e.getTipoEntrada());
                entradaJson.put("estadoEntrada", e.getEstadoEntrada());
                entradaJson.put("precio", e.getPrecio());
                entradasJson.put(entradaJson);
            }
            nuevoEvento.put("entradas", entradasJson);

            // Añadir nuevo evento al array
            eventosArray.put(nuevoEvento);

            // Guardar eventos actualizados
            try (FileWriter writer = new FileWriter(EVENTOS_JSON)) {
                writer.write(eventosArray.toString(4));
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

    return false;
    }

    @Override
    public boolean modificarEvento(int idEventoModificar, String titulo, Date fechaRealizacion, String categoria, List<Entrada> entradas, String direccion, String politicas, double maxPrice) {
        try {
            // Leer eventos existentes
            String eventosContenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(eventosContenido);

            boolean eventoEncontrado = false;

            // Formatear fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String fechaFormateada = sdf.format(fechaRealizacion);

            // Actualizar evento y su listado de entradas
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                if (eventoJson.getInt("idEvento") == idEventoModificar) {
                    eventoJson.put("titulo", titulo);
                    eventoJson.put("fechaRealizacion", fechaFormateada);
                    eventoJson.put("categoria", categoria);
                    eventoJson.put("direccion", direccion);
                    eventoJson.put("politicas", politicas);
                    eventoJson.put("maxPrice", maxPrice);
                    // Actualizar el array de entradas del evento
                    JSONArray nuevasEntradasEvento = new JSONArray();
                    for (Entrada e : entradas) {
                        JSONObject entradaJson = new JSONObject();
                        entradaJson.put("idEntrada", e.getIdEntrada());
                        entradaJson.put("idEvento", idEventoModificar);
                        entradaJson.put("correoAsociado", e.getCorreoAsociado());
                        entradaJson.put("tipoEntrada", e.getTipoEntrada());
                        entradaJson.put("estadoEntrada", e.getEstadoEntrada());
                        entradaJson.put("precio", e.getPrecio());
                        nuevasEntradasEvento.put(entradaJson);
                    }
                    eventoJson.put("entradas", nuevasEntradasEvento);
                    eventoEncontrado = true;
                    break;
                }
            }

            if (!eventoEncontrado) {
                return false; // Evento no encontrado
            }

            // Guardar eventos actualizados
            try (FileWriter writer = new FileWriter(EVENTOS_JSON)) {
                writer.write(eventosArray.toString(4));
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

    return false;
    }

    // Devuelve una lista de objetos Evento leídos del JSON
    @Override
    public List<modelo.Evento> obtenerEventos() {
        List<modelo.Evento> lista = new ArrayList<>();
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject evento = eventosArray.getJSONObject(i);
                int id = evento.getInt("idEvento");
                String titulo = evento.getString("titulo");
                String fechaStr = evento.getString("fechaRealizacion");
                String categoria = evento.getString("categoria");
                String direccion = evento.getString("direccion");
                String politicas = evento.getString("politicas");
                double maxPrice = evento.getDouble("maxPrice");
                // Parsear la fecha y crear el objeto Evento con el constructor adecuado
                Date fecha = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    fecha = sdf.parse(fechaStr);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                modelo.Evento ev = new modelo.Evento(id, titulo, fecha, categoria, direccion, politicas, maxPrice);
                lista.add(ev);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public JSONObject buscarEvento(String nombreBuscado) {
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);

            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject evento = eventosArray.getJSONObject(i);
                String titulo = evento.getString("titulo");

                if (titulo.equalsIgnoreCase(nombreBuscado)) {
                    // Imprimir el evento encontrado
                    System.out.println("Evento encontrado:");
                    System.out.println("ID: " + evento.getInt("idEvento"));
                    System.out.println("Título: " + evento.getString("titulo"));
                    System.out.println("Fecha: " + evento.getString("fechaRealizacion"));
                    System.out.println("Categoría: " + evento.getString("categoria"));
                    System.out.println("Dirección: " + evento.getString("direccion"));
                    System.out.println("Políticas: " + evento.getString("politicas"));
                    System.out.println("Precio máximo: " + evento.getDouble("maxPrice"));
                    return evento;
                }
            }

            System.out.println("No se encontró ningún evento con el título: " + nombreBuscado);
            return null;

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de eventos: " + e.getMessage());
            return null;
        }
    }

    //ITramitarDevolucion
    @Override
    public double procesarDevolucion(int idEvento) {
        double totalDevolucion = 0.0;
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                if (eventoJson.getInt("idEvento") == idEvento) {
                    JSONArray entradasEvento = eventoJson.getJSONArray("entradas");
                    for (int j = 0; j < entradasEvento.length(); j++) {
                        JSONObject entradaJson = entradasEvento.getJSONObject(j);
                        String estado = entradaJson.getString("estadoEntrada");
                        if (estado.equalsIgnoreCase("Vendida") || estado.equalsIgnoreCase("En Reventa")) {
                            double precio = entradaJson.getDouble("precio");
                            totalDevolucion += precio;
                        }
                    }
                    break;
                }
            }
            return totalDevolucion;
        } catch (IOException e) {
            System.err.println("Error al procesar devoluciones: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean enviarListadoDevolucion(int idEvento, double devolucion){

        System.out.println("La pasarela de pago devolvera un total de " + devolucion + " por la cancelacion del evento con id " + idEvento);

        return true;

    }

    @Override
    public boolean eliminarEvento(int idEvento) {
        try {
            // 1. Leer eventos y eliminar el evento con el ID indicado
            String contenidoEventos = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenidoEventos);
            boolean eventoEncontrado = false;
            JSONArray nuevosEventos = new JSONArray();
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject evento = eventosArray.getJSONObject(i);
                if (evento.getInt("idEvento") == idEvento) {
                    eventoEncontrado = true;
                    // No se añade al nuevo array
                } else {
                    nuevosEventos.put(evento);
                }
            }
            if (!eventoEncontrado) {
                System.out.println("No se encontró ningún evento con ID " + idEvento + ".");
                return false;
            }
            // 2. Guardar los eventos sin el evento eliminado
            try (FileWriter eventosWriter = new FileWriter(EVENTOS_JSON)) {
                eventosWriter.write(nuevosEventos.toString(4));
            }
            System.out.println("Evento eliminado correctamente.");
            return true;
        } catch (IOException e) {
            System.err.println("Error al eliminar el evento: " + e.getMessage());
            return false;
        }
    }

    
}
