package sistema.eventos.interfaces;

import java.util.Date;
import java.util.List;
import modelo.Entrada; // Ajusta el paquete si tu clase Entrada está en otro lugar
import org.json.JSONObject;
import modelo.Evento; // Ajusta el paquete si tu clase Evento está en otro lugar

public interface IRealizarEvento {
    public abstract boolean crearEvento(String titulo, Date fechaRealizacion, String categoria, List<Entrada> entradas, String direccion, String politicas); //crea un evento en el sistema
    public abstract boolean modificarEvento(int idEventoModificar, String titulo, Date fechaRealizacion, String categoria, List<Entrada> entradas, String direccion, String politicas, double maxPrice); //modifica un evento ya creado
    public abstract void visualizarEventos(); //muestra todos los eventos del sistema
    public abstract JSONObject buscarEvento(String nombreBuscado); //busca un evento por su nombre
}
