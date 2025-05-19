package modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Entrada{

    //atributos
    protected int idEntrada; //id de la entrada
    protected int idEvento; //id del evento al que pertenece
    protected String correo; //correo del usuario al que pertenece
    protected String tipoEntrada; //tipo de entrada VIP, normal
    protected String estadoEntrada; //En Venta, vendida, En Reventa
    protected double precio;


    //constructor
    public Entrada(int idEntrada, int idEvento, String tipoEntrada, double precio){ //por defecto toda las entradas que se crean son para vender (no se generan ya vendidas o en reventa)

        this.idEntrada = idEntrada;
        this.idEvento = idEvento;
        this.correo = "";
        this.tipoEntrada = tipoEntrada;
        this.estadoEntrada = "En Venta";
        this.precio = precio;

    }

    //getters
    public int getIdEntrada(){

        return this.idEntrada;

    }

    public int getIdEvento(){

        return this.idEvento;

    }

    public String getCorreoAsociado(){

        return this.correo;

    }

    public String getTipoEntrada(){

        return this.tipoEntrada;

    }

    public String getEstadoEntrada(){

        return this.estadoEntrada;

    }

    public double getPrecio(){

        return this.precio;

    }

    //setters
    public boolean setIdEntrada(int idEntrada){

        this.idEntrada = idEntrada;

        return true;

    }

    public boolean setEventoAsociado(int idEvento){

        this.idEvento = idEvento;

        return true;

    }

    public boolean setCorreoAsociado(String correoAsociado){

        this.correo = correoAsociado;

        return true;

    }

    public boolean setTipoEntrada(String tipoEntrada){

        this.tipoEntrada = tipoEntrada;

        return true;

    }

    public boolean setEstadoEntrada(String nuevoEstadoEntrada){

        this.estadoEntrada = nuevoEstadoEntrada;

        return true;

    }

    public boolean setPrecio(double newPrice){

        this.precio = newPrice;

        return true;

    }

}