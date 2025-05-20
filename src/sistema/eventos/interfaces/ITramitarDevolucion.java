package sistema.eventos.interfaces;

public interface ITramitarDevolucion {

   public abstract double procesarDevolucion(int idEvento); //calcula el dinero a devolver por entrada
   public abstract boolean enviarListadoDevolucion(int idEvento, double devolucion); //calculamos el numero de entradas a devolver y lo enviamos a la pasarela de pago para que realicen ellos el proceso
   public abstract boolean eliminarEvento(int idEventoEliminar); //elimina el evento del sistema

}
