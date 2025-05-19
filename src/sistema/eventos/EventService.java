package sistema.eventos;

import sistema.eventos.interfaces.IComprarEntrada;
import sistema.eventos.interfaces.IGestionarVenta;
import sistema.eventos.interfaces.IRealizarEvento;
import sistema.eventos.interfaces.ITramitarDevolucion;

import negocio.evento.IEventoMgr;

import java.util.Date;
import java.util.List;
import modelo.Entrada;
import org.json.JSONObject;

public class EventService implements IRealizarEvento, IComprarEntrada, IGestionarVenta, ITramitarDevolucion {
    //instancia del mgr que se debe llamar
    private final IEventoMgr mgr = new IEventoMgr();

    //ComprarEntrada
    /*
    @Override
    public boolean comprobarEntradas(List<Entrada> entradasAdquirir){

        return mgr.comprobarEntradas(entradasAdquirir);

    }

    @Override
    public boolean pagoDeEntradas(int idUsuario, String formaPago, double precio){

        return mgr.pagoDeEntradas(idUsuario, formaPago, precio);

    }

    @Override
    public Entrada generarEntradaUsuario(Entrada entradaAdquirida, int idUsuario){

        return mgr.generarEntradaUsuario(entradaAdquirida, idUsuario);

    }

    //IGestionarCuenta
    @Override
    public String mostrarEntradasAdquiridas(String correo){

        return mgr.mostrarEntradasAdquiridas(correo);

    }

    @Override
    public boolean comprobarPrecio(int idEvento, double posiblePrecio){

        return mgr.comprobarPrecio(idEvento, posiblePrecio);

    }

    @Override
    public boolean publicarEntradaEnVenta(int idEntrada, double precio){

        return mgr.publicarEntradaEnVenta(idEntrada, precio);

    }

    @Override
    public boolean modificarVenta(int idEntrada, double precio){

        return mgr.modificarVenta(idEntrada, precio);

    }

    @Override
    public boolean eliminarVenta(int idEntrada){

        return mgr.eliminarVenta(idEntrada);

    }
    */
    //IRealizarEvento
    @Override
    public boolean crearEvento(String titulo, Date fechaRealizacion, String categoria, List<Entrada> entradas, String direccion, String politicas){

        return mgr.crearEvento(titulo, fechaRealizacion, categoria, entradas, direccion, politicas);

    }

    @Override
    public boolean modificarEvento(int idEventoModificar, String titulo, Date fechaRealizacion, String categoria, List<Entrada> entradas, String direccion, String politicas, double maxPrice){

        return mgr.modificarEvento(idEventoModificar, titulo, fechaRealizacion, categoria, entradas, direccion, politicas, maxPrice);

    }

    @Override
    public void visualizarEventos(){

        mgr.visualizarEventos();

    }

    @Override
    public JSONObject buscarEvento(String nombreBuscado){

        return mgr.buscarEvento(nombreBuscado);

    }
    /*
    //tramitardevolucion
    @Override
    public double procesarDevolucion(int idEvento){

        return mgr.procesarDevolucion(idEvento);

    }

    @Override
    public boolean enviarListadoDevolución(int idEvento, double devolucion){

        return mgr.enviarListadoDevolución(idEvento, devolucion);

    }

    @Override
    public boolean eliminarEvento(int idEvento){

        return mgr.eliminarEvento(idEvento);

    }
    */
}