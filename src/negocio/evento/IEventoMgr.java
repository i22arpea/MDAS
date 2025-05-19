package negocio.evento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IEventoMgr {

    private static final String USUARIO_JSON = "usuarios.json";
    private static final String EVENTOS_JSON = "eventos.json";
    private static final String ENTRADAS_JSON = "entradas.json";

    public IEventoMgr() {
        try {
            if (!Files.exists(Paths.get(USUARIO_JSON))) {
                Files.write(Paths.get(USUARIO_JSON), "[]".getBytes());
            }
            if (!Files.exists(Paths.get(EVENTOS_JSON))) {
                Files.write(Paths.get(EVENTOS_JSON), "[]".getBytes());
            }
            if (!Files.exists(Paths.get(ENTRADAS_JSON))) {
                Files.write(Paths.get(ENTRADAS_JSON), "[]".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //IComprarEntrada
    public boolean comprobarEntradas(List<Entrada> entradasAdquirir) {
        for (Entrada entrada : entradasAdquirir) {
            if ("Vendida".equalsIgnoreCase(entrada.getEstadoEntrada()) || !entrada.getCorreo().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean pagoDeEntradas(int idUsuario, String formaPago, double precio) {
        System.out.println("El usuario con id " + idUsuario + " está realizando el pago por " + precio + " con el método " + formaPago);
        return true;
    }

    public Entrada generarEntradaUsuario(Entrada entradaAdquirida, String correoUsuario) {
        entradaAdquirida.setCorreo(correoUsuario);
        guardarEntradaEnJson(entradaAdquirida);
        return entradaAdquirida;
    }

    //IGestionarCuenta
    public String mostrarEntradasAdquiridas(String correo) {
        List<Entrada> entradasUsuario = new ArrayList<>();

        try {
            String contenido = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(contenido);

            for (int i = 0; i < entradasArray.length(); i++) {
                JSONObject entradaJson = entradasArray.getJSONObject(i);
                if (correo.equalsIgnoreCase(entradaJson.optString("correo"))) {
                    Entrada entrada = new Entrada(
                            entradaJson.getInt("id"),
                            entradaJson.getInt("idEvento"),
                            entradaJson.getDouble("precio"),
                            entradaJson.getString("estadoEntrada"),
                            entradaJson.getString("correo")
                    );
                    entradasUsuario.add(entrada);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imprimir en consola y devolver como String
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
            String contenido = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(contenido);

            for (int i = 0; i < entradasArray.length(); i++) {
                JSONObject entradaJson = entradasArray.getJSONObject(i);

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
                    try (FileWriter writer = new FileWriter(ENTRADAS_JSON)) {
                        writer.write(entradasArray.toString(4)); // pretty print
                    }

                    return true;
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
            String contenido = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(contenido);

            for (int i = 0; i < entradasArray.length(); i++) {
                JSONObject entradaJson = entradasArray.getJSONObject(i);

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
                    try (FileWriter writer = new FileWriter(ENTRADAS_JSON)) {
                        writer.write(entradasArray.toString(4)); // formato legible
                    }

                    return true; // Modificado correctamente
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
            String contenido = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(contenido);

            for (int i = 0; i < entradasArray.length(); i++) {
                JSONObject entradaJson = entradasArray.getJSONObject(i);

                if (entradaJson.getInt("idEntrada") == idEntrada) {

                    String estado = entradaJson.getString("estadoEntrada");
                    if (!"En Reventa".equalsIgnoreCase(estado)) {
                        return false; // Solo se puede eliminar si está en reventa
                    }

                    // Cambiar estado a Vendida
                    entradaJson.put("estadoEntrada", "Vendida");

                    // Guardar los cambios
                    try (FileWriter writer = new FileWriter(ENTRADAS_JSON)) {
                        writer.write(entradasArray.toString(4));
                    }

                    return true; // Eliminación (cambio de estado) correcta
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

            // Añadir nuevo evento al array
            eventosArray.put(nuevoEvento);

            // Guardar eventos actualizados
            try (FileWriter writer = new FileWriter(EVENTOS_JSON)) {
                writer.write(eventosArray.toString(4));
            }

            // Leer entradas existentes
            String entradasContenido = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(entradasContenido);

            // Añadir entradas nuevas al array (asignando el idEvento correcto)
            for (Entrada e : entradas) {
                JSONObject entradaJson = new JSONObject();
                entradaJson.put("idEntrada", e.getIdEntrada());
                entradaJson.put("idEvento", nuevoIdEvento);
                entradaJson.put("correo", e.getCorreoAsociado());
                entradaJson.put("tipoEntrada", e.getTipoEntrada());
                entradaJson.put("estadoEntrada", e.getEstadoEntrada());
                entradaJson.put("precio", e.getPrecio());

                entradasArray.put(entradaJson);
            }

            // Guardar entradas actualizadas
            try (FileWriter writer = new FileWriter(ENTRADAS_JSON)) {
                writer.write(entradasArray.toString(4));
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

    return false;
    }

    @Override
    public boolean modificarEvento(int idEvento, Date fechaRealizacion, List<Entrada> entradas, String direccion, String politicas, double maxPrice) {
        try {
            // Leer eventos existentes
            String eventosContenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(eventosContenido);

            boolean eventoEncontrado = false;

            // Formatear fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String fechaFormateada = sdf.format(fechaRealizacion);

            // Actualizar evento
            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoJson = eventosArray.getJSONObject(i);
                if (eventoJson.getInt("idEvento") == idEvento) {

                    eventoJson.put("fechaRealizacion", fechaFormateada);
                    eventoJson.put("direccion", direccion);
                    eventoJson.put("politicas", politicas);
                    eventoJson.put("maxPrice", maxPrice);  // aquí el nuevo precio máximo

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

            // Leer entradas existentes
            String entradasContenido = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(entradasContenido);

            // Eliminar entradas existentes del evento modificado
            JSONArray nuevasEntradasArray = new JSONArray();
            for (int i = 0; i < entradasArray.length(); i++) {
                JSONObject entradaJson = entradasArray.getJSONObject(i);
                if (entradaJson.getInt("idEvento") != idEvento) {
                    nuevasEntradasArray.put(entradaJson);
                }
            }

            // Añadir entradas nuevas (pasadas en el parámetro)
            for (Entrada e : entradas) {
                JSONObject entradaJson = new JSONObject();
                entradaJson.put("idEntrada", e.getIdEntrada());
                entradaJson.put("idEvento", idEvento);
                entradaJson.put("correo", e.getCorreoAsociado());
                entradaJson.put("tipoEntrada", e.getTipoEntrada());
                entradaJson.put("estadoEntrada", e.getEstadoEntrada());
                entradaJson.put("precio", e.getPrecio());

                nuevasEntradasArray.put(entradaJson);
            }

            // Guardar entradas actualizadas
            try (FileWriter writer = new FileWriter(ENTRADAS_JSON)) {
                writer.write(nuevasEntradasArray.toString(4));
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

    return false;
    }

    @Override
    public void visualizarEventos() {
        try {
            String contenido = Files.readString(Paths.get(EVENTOS_JSON));
            JSONArray eventosArray = new JSONArray(contenido);

            if (eventosArray.length() == 0) {
                System.out.println("No hay eventos registrados.");
                return;
            }

            System.out.println("==== EVENTOS REGISTRADOS ====");

            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject evento = eventosArray.getJSONObject(i);

                int id = evento.getInt("idEvento");
                String titulo = evento.getString("titulo");
                String fecha = evento.getString("fechaRealizacion");
                String categoria = evento.getString("categoria");
                String direccion = evento.getString("direccion");
                String politicas = evento.getString("politicas");
                double maxPrice = evento.getDouble("maxPrice");

                System.out.println("ID Evento: " + id);
                System.out.println("Título: " + titulo);
                System.out.println("Fecha de realización: " + fecha);
                System.out.println("Categoría: " + categoria);
                System.out.println("Dirección: " + direccion);
                System.out.println("Políticas: " + politicas);
                System.out.println("Precio máximo: " + maxPrice);
                System.out.println("------------------------------");
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de eventos: " + e.getMessage());
        }
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
            String contenidoEntradas = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(contenidoEntradas);

            int entradasADevolver = 0;

            for (int i = 0; i < entradasArray.length(); i++) {
                JSONObject entrada = entradasArray.getJSONObject(i);

                if (entrada.getInt("idEvento") == idEvento) {
                    String estado = entrada.getString("estadoEntrada");

                    // Solo devolvemos dinero de entradas ya vendidas o en reventa
                    if (estado.equalsIgnoreCase("Vendida") || estado.equalsIgnoreCase("En Reventa")) {
                        double precio = entrada.getDouble("precio");
                        totalDevolucion += precio;
                        entradasADevolver++;
                    }
                }
            }

            System.out.println("Entradas a devolver: " + entradasADevolver);
            System.out.println("Total de devolución: " + totalDevolucion + "€");

            return totalDevolucion;

        } catch (IOException e) {
            System.err.println("Error al procesar devoluciones: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean enviarListadoDevolución(int idEvento, double devolucion){

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

            // 3. Leer entradas y eliminar las asociadas al evento eliminado
            String contenidoEntradas = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(contenidoEntradas);
            JSONArray nuevasEntradas = new JSONArray();

            for (int i = 0; i < entradasArray.length(); i++) {
                JSONObject entrada = entradasArray.getJSONObject(i);
                if (entrada.getInt("idEvento") != idEvento) {
                    nuevasEntradas.put(entrada);
                }
            }

            // 4. Guardar las entradas sin las del evento eliminado
            try (FileWriter entradasWriter = new FileWriter(ENTRADAS_JSON)) {
                entradasWriter.write(nuevasEntradas.toString(4));
            }

            System.out.println("Evento y entradas asociadas eliminados correctamente.");
            return true;

        } catch (IOException e) {
            System.err.println("Error al eliminar el evento y sus entradas: " + e.getMessage());
            return false;
        }
    }



    // Método auxiliar para guardar una entrada en el JSON
    private void guardarEntradaEnJson(Entrada entrada) {
        try {
            String contenido = Files.readString(Paths.get(ENTRADAS_JSON));
            JSONArray entradasArray = new JSONArray(contenido);

            JSONObject nuevaEntrada = new JSONObject();
            nuevaEntrada.put("id", entrada.getId());
            nuevaEntrada.put("idEvento", entrada.getIdEvento());
            nuevaEntrada.put("precio", entrada.getPrecio());
            nuevaEntrada.put("estadoEntrada", entrada.getEstadoEntrada());
            nuevaEntrada.put("correo", entrada.getCorreo());

            entradasArray.put(nuevaEntrada);

            FileWriter writer = new FileWriter(ENTRADAS_JSON);
            writer.write(entradasArray.toString(4)); // pretty print
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
