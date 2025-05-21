package modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;

public class Evento{

    //atributos
    protected int idEvento;
    protected String titulo; //nombre del evento
    protected Date fechaRealizacion; //fecha en que se realiza
    protected String categoria; //categoria
    protected List<Entrada> entradas; //lista con las entradas sin asignar a personas
    protected String direccion; //lugar donde se realiza el evento
    protected String politicas; //politicas de reventa
    protected double maxPrice; //maximo precio para las reventas


    //constructor()
    public Evento(int idEvento, String titulo, Date fechaRealizacion, String categoria, String direccion, String politicas, double maxPrice){ //las entradas se inicializan en vacio hasta que se crean

        this.idEvento = idEvento;
        this.titulo = titulo;
        this.fechaRealizacion = fechaRealizacion;
        this.categoria = categoria;
        this.direccion = direccion;
        this.politicas = politicas;
        this.maxPrice = maxPrice;
        this.entradas = new ArrayList<>(); //inicializamos la lista vacia

    }

    //getters
    public int getIdEvento(){

        return this.idEvento;

    }

    public String getTitulo(){

        return this.titulo;

    }

    public Date getFechaRealizacion(){

        return this.fechaRealizacion;

    }

    public String getCategoria(){

        return this.categoria;

    }

    public String getDireccion(){

        return this.direccion;

    }

    public String getPoliticas(){

        return this.politicas;

    }

    public double getMaxPrice(){

        return this.maxPrice;

    }

    public List<Entrada> getListEntradas(){

        return this.entradas;

    }

    //setters

    public boolean setIdEvento(int idEvento){

        this.idEvento = idEvento;

        return true;

    }

    public boolean setTitulo(String nuevoTitulo){

        this.titulo = nuevoTitulo;

        return true;

    }

    public boolean setFechaRealizacion(Date nuevaFecha){

        this.fechaRealizacion = nuevaFecha;

        return true;

    }

    public boolean setCategoria(String nuevaCategoria){

        this.categoria = nuevaCategoria;

        return true;

    }

    public boolean setDireccion(String nuevaDireccion){

        this.direccion = nuevaDireccion;

        return true;

    }

    public boolean setPoliticas(String nuevasPoliticas){

        this.politicas = nuevasPoliticas;

        return true;

    }

    public boolean setMaxPrice(double newMaxPrice){

        this.maxPrice = newMaxPrice;

        return true;

    }

    public boolean setListaEntradas(List<Entrada> listaEntradas){

        this.entradas = new ArrayList<Entrada>(listaEntradas); //esto realiza una copia exacta del array nuevo recibido sobre la memoria a la que apunta this.entradas

        return true;

    }

    //METODO PARA GENERAR UNA LISTA DE ENTRADAS VACIAS Y ASIGNARLA AL EVENTO

    public boolean listaEntradas(int numeroEntradas, String tipoEntrada, double precio){ //crea una lista de entradas vacia y la asigna al evento

        for (int i = 0 ; i < numeroEntradas ; i++){

            Entrada nuevaEntrada = new Entrada(i, this.idEvento, tipoEntrada, precio); //creamos la entrada

            this.entradas.add(nuevaEntrada); //la introducimos en el arraylist

        }

        return true;

    }

    public void guardarEventoActualizado() throws Exception {
        String path = "eventos.json";
        String contenido = new String(Files.readAllBytes(Paths.get(path)));
        JSONArray eventosArray = new JSONArray(contenido);

        for (int i = 0; i < eventosArray.length(); i++) {
            JSONObject eventoJson = eventosArray.getJSONObject(i);
            if (eventoJson.getInt("idEvento") == this.getIdEvento()) {
                // Actualiza los campos del evento
                eventoJson.put("titulo", this.getTitulo());
                // Formatea la fecha a yyyy-MM-dd'T'HH:mm:ss (la T es literal, debe ir entre comillas simples)
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String fechaFormateada = sdf.format(this.getFechaRealizacion());
                eventoJson.put("fechaRealizacion", fechaFormateada);
                eventoJson.put("categoria", this.getCategoria());
                eventoJson.put("direccion", this.getDireccion());
                eventoJson.put("politicas", this.getPoliticas());
                eventoJson.put("maxPrice", this.getMaxPrice());

                // Actualiza las entradas (todas las propiedades)
                JSONArray entradasArray = new JSONArray();
                for (Entrada entrada : this.getListEntradas()) {
                    JSONObject entradaJson = new JSONObject();
                    entradaJson.put("idEntrada", entrada.getIdEntrada());
                    entradaJson.put("idEvento", entrada.getIdEvento());
                    entradaJson.put("tipoEntrada", entrada.getTipoEntrada());
                    entradaJson.put("precio", entrada.getPrecio());
                    entradaJson.put("estadoEntrada", entrada.getEstadoEntrada());
                    entradaJson.put("correoAsociado", entrada.getCorreoAsociado());
                    entradasArray.put(entradaJson);
                }
                eventoJson.put("entradas", entradasArray);
                break;
            }
        }

        // Guarda el array actualizado en el archivo
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(eventosArray.toString(4)); // pretty print
        }

        // Borra el fichero entradas.json si existe
        java.nio.file.Path entradasPath = java.nio.file.Paths.get("entradas.json");
        if (java.nio.file.Files.exists(entradasPath)) {
            java.nio.file.Files.delete(entradasPath);
        }
    }
    /**
     * Lee las entradas asociadas a este evento desde eventos.json y las devuelve como lista.
     */
    public List<Entrada> cargarEntradasDesdeJson() throws Exception {
        List<Entrada> lista = new ArrayList<>();
        String path = "eventos.json";
        String contenido = new String(Files.readAllBytes(Paths.get(path)));
        JSONArray eventosArray = new JSONArray(contenido);
        for (int i = 0; i < eventosArray.length(); i++) {
            JSONObject eventoJson = eventosArray.getJSONObject(i);
            if (eventoJson.getInt("idEvento") == this.getIdEvento()) {
                JSONArray entradasArray = eventoJson.optJSONArray("entradas");
                if (entradasArray != null) {
                    for (int j = 0; j < entradasArray.length(); j++) {
                        JSONObject entradaJson = entradasArray.getJSONObject(j);
                        int idEntrada = entradaJson.getInt("idEntrada");
                        String estado = entradaJson.optString("estadoEntrada", "");
                        String correo = entradaJson.optString("correoAsociado", "");
                        String tipo = entradaJson.has("tipoEntrada") ? entradaJson.getString("tipoEntrada") : "";
                        double precio = entradaJson.has("precio") ? entradaJson.getDouble("precio") : 0.0;
                        Entrada entrada = new Entrada(idEntrada, this.idEvento, tipo, precio);
                        entrada.setEstadoEntrada(estado);
                        entrada.setCorreoAsociado(correo);
                        lista.add(entrada);
                    }
                }
                break;
            }
        }
        return lista;
    }

}