package negocio.evento;

import sistema.evento.interfaces.IComprarEntrada;
import sistema.evento.interfaces.IGestionarVenta;
import sistema.evento.interfaces.IRealizarEvento;
import sistema.evento.interfaces.ITramitarDevolucion;

import org.json.JSONObject;

public abstract class IEventoMgt implements IComprarEntrada, IGestionarVenta, IRealizarEvento, ITramitarDevolucion{

    //IComprarEntrada
    boolean comprobarEntradas(List<Entrada> nEntradasAdquirir); //confirma las entradas que el usuario va a comprar
    boolean pagoDeEntradas(int idUsuario, String FormaPago); //realiza el pago de las entradas
    Entrada generarEntradaUsuario(Entrada entradaAdquirida, int idUsuario); //se genera la entrada para el usuario y se le asocia

    //IGestionarCuenta
    String mostrarEntradasAdquiridas(String correo); //muestra las entradas asociadas al usuario con correo indicado
    boolean comprobarPrecio(int idEvento, double posiblePrecio); //comprueba si un precio de reventa es válido
    boolean publicarEntradaEnVenta(int idEntrada, double precio); //pone a la venta una entrada
    boolean modificarVenta(int idEntrada, double precio); //cambia el precio de una entrada puesta en venta por el usuario
    boolean eliminarVenta(int idEntrada); //elimina la entrada de la venta y la devuelve al usuario que pertenece

    //IRealizarEvento
    boolean crearEvento(String titulo, Date FechaRealizacion, String Categoria, List<Entrada> Entradas, String direccion, String politicas); //crea un evento en el sistema
    boolean modificarEvento(int idEventoModificar, Date FechaRealizacion, List<Entrada> Entradas, String direccion, String politicas); //modifica un evento ya creado
    void visualizarEventos(); //muestra todos los eventos del sistema
    Evento buscarEvento(String NombreBuscado); //busca un evento por su nombre

    //ITramitarDevolucion
    double procesarDevolución(int idEvento); //calcula el dinero a devolver por entrada
    boolean enviarListadoDevolución(int idEvento, double devolucion); //calculamos el numero de entradas a devolver y lo enviamos a la pasarela de pago para que realicen ellos el proceso
    boolean eliminarEvento(int idEvento); //elimina el evento del sistema

}