package negocio.evento;

import java.util.Date;
import java.util.List;
import modelo.Entrada; // Ajusta el paquete si tu clase Entrada está en otro lugar
import modelo.Evento;  // Ajusta el paquete si tu clase Evento está en otro lugar

import sistema.eventos.interfaces.IComprarEntrada;
import sistema.eventos.interfaces.IGestionarVenta;
import sistema.eventos.interfaces.IRealizarEvento;
import sistema.eventos.interfaces.ITramitarDevolucion;

import org.json.JSONObject;

public abstract class IEventoMgt implements IComprarEntrada, IGestionarVenta, IRealizarEvento, ITramitarDevolucion{

    //IComprarEntrada
    /*
    boolean comprobarEntradas(List<Entrada> nEntradasAdquirir); //confirma las entradas que el usuario va a comprar
    boolean pagoDeEntradas(int idUsuario, String FormaPago); //realiza el pago de las entradas
    Entrada generarEntradaUsuario(Entrada entradaAdquirida, int idUsuario); //se genera la entrada para el usuario y se le asocia
    */
    //IGestionarVenta
    /*
    String mostrarEntradasAdquiridas(String correo); //muestra las entradas asociadas al usuario con correo indicado
    boolean comprobarPrecio(int idEvento, double posiblePrecio); //comprueba si un precio de reventa es válido
    boolean publicarEntradaEnVenta(int idEntrada, double precio); //pone a la venta una entrada
    boolean modificarVenta(int idEntrada, double precio); //cambia el precio de una entrada puesta en venta por el usuario
    boolean eliminarVenta(int idEntrada); //elimina la entrada de la venta y la devuelve al usuario que pertenece
    */
    //IRealizarEvento
    public abstract boolean crearEvento(String titulo, Date fechaRealizacion, String categoria, List<Entrada> entradas, String direccion, String politicas); //crea un evento en el sistema
    public abstract boolean modificarEvento(int idEventoModificar, Date fechaRealizacion, List<Entrada> entradas, String direccion, String politicas); //modifica un evento ya creado
    public abstract List<modelo.Evento> obtenerEventos(); //muestra todos los eventos del sistema
    public abstract JSONObject buscarEvento(String nombreBuscado); //busca un evento por su nombre

    //ITramitarDevolucion
    /*
    double procesarDevolución(int idEvento); //calcula el dinero a devolver por entrada
    boolean enviarListadoDevolución(int idEvento, double devolucion); //calculamos el numero de entradas a devolver y lo enviamos a la pasarela de pago para que realicen ellos el proceso
    boolean eliminarEvento(int idEvento); //elimina el evento del sistema
    */

}