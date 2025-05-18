package modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
        this.max_price = max_price;
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

    public boolean listaEntradas(int numeroEntradas){

        //CODIGO

    }

}