package sistema.eventos.interfaces;

public interface IGestionarVenta { //entradas de tipo reventa

   String mostrarEntradasAdquiridas(String correo); //muestra las entradas asociadas al usuario con correo indicado
   boolean comprobarPrecio(int idEvento, double posible_precio); //comprueba si un precio de reventa es válido
   boolean publicarEntradaEnVenta(int idEntrada, double precio); //pone a la venta una entrada
   boolean modificarVenta(int idVenta, double precio); //cambia el precio de una entrada puesta en venta por el usuario
   boolean eliminarVenta(int idVenta); //elimina la entrada de la venta y la devuelve al usuario que pertenece

}
