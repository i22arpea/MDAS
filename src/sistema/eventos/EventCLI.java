package sistema.eventos;

import java.util.Scanner;

import sistema.eventos.interfaces.IComprarEntrada;
import sistema.eventos.interfaces.IGestionarVenta;
import sistema.eventos.interfaces.IRealizarEvento;
import sistema.eventos.interfaces.ITramitarDevolucion;

public class EventCLI {
    private final IRealizarEvento realizarEvento;
    private final IComprarEntrada comprarEntrada;
    private final IGestionarVenta gestionarVenta;
    private final ITramitarDevolucion tramitarDevolucion;
    private final Scanner sc;

    public EventCLI(IRealizarEvento realizarEvento, IComprarEntrada comprarEntrada, IGestionarVenta gestionarVenta, ITramitarDevolucion tramitarDevolucion, Scanner sc) {
        this.realizarEvento = realizarEvento;
        this.comprarEntrada = comprarEntrada;
        this.gestionarVenta = gestionarVenta;
        this.tramitarDevolucion = tramitarDevolucion;
        this.sc = sc;
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- Menú de Eventos ---");
            System.out.println("1. Realizar Evento");
            System.out.println("2. Comprar Entrada");
            System.out.println("3. Gestionar Venta");
            System.out.println("4. Tramitar Devolución");
            System.out.println("0. Volver");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    // ...call realizarEvento...
                    break;
                case "2":
                    // ...call comprarEntrada...
                    break;
                case "3":
                    // ...call gestionarVenta...
                    break;
                case "4":
                    // ...call tramitarDevolucion...
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
