package sistema.eventos.interfaces;

public interface IRealizarEvento {

   boolean crearEvento(String titulo, Date FechaRealizacion, String Categoria, List<Entrada> Entradas, String direccion, String politicas); //crea un evento en el sistema
   boolean modificarEvento(int idEventoModificar, Date FechaRealizacion, List<Entrada> Entradas, String direccion, String politicas, double maxPrice); //modifica un evento ya creado
   JSONObject visualizarEventos(); //muestra todos los eventos del sistema
   Evento buscarEvento(String NombreBuscado); //busca un evento por su nombre

}
