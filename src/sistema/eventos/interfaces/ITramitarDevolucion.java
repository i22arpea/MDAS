package sistema.eventos.interfaces;

public interface ITramitarDevolucion {

   double procesarDevolución(int idEvento, String politicas); //calcula el dinero a devolver por entrada
   boolean enviarListadoDevolución(int idEvento, double devolucion); //calculamos el numero de entradas a devolver y lo enviamos a la pasarela de pago para que realicen ellos el proceso
   boolean eliminarEvento(int idEventoEliminar); //elimina el evento del sistema

}
