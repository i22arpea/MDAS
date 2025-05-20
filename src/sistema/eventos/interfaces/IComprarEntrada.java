package sistema.eventos.interfaces;

import java.util.List;
import modelo.Entrada; // Ajusta el paquete si tu clase Entrada está en otro lugar

public interface IComprarEntrada {
    
    boolean comprobarEntradas(List<Entrada> nEntradasAdquirir); // confirma las entradas que el usuario va a comprar
    boolean pagoDeEntradas(String correoUser, String formaPago, double precio); //realiza el pago de las entradas
    Entrada generarEntradaUsuario(Entrada entradaAdquirida, String correoUsuario); //se genera la entrada para el usuario y se le asocia
    
}
