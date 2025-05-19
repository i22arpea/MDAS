package sistema.eventos.interfaces;

public interface IComprarEntrada {
    
boolean comprobarEntradas(List<Entrada> nEntradasAdquirir); // confirma las entradas que el usuario va a comprar
    boolean pagoDeEntradas(int idUsuario, String FormaPago); //realiza el pago de las entradas
    Entrada generarEntradaUsuario(Entrada entradaAdquirida, int idUsuario); //se genera la entrada para el usuario y se le asocia

}
