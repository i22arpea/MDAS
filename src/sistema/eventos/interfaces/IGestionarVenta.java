package sistema.eventos.interfaces;

public interface IGestionarVenta { //entradas de tipo reventa

   public abstract String mostrarEntradasAdquiridas(String correo); //muestra las entradas asociadas al usuario con correo indicado
   public abstract boolean comprobarPrecio(int idEvento, double posible_precio); //comprueba si un precio de reventa es válido
   public abstract boolean publicarEntradaEnVenta(int idEntrada, double precio); //pone a la venta una entrada
   public abstract boolean modificarVenta(int idVenta, double precio); //cambia el precio de una entrada puesta en venta por el usuario
   public abstract boolean eliminarVenta(int idVenta); //elimina la entrada de la venta y la devuelve al usuario que pertenece

}
