package negocio.evento;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public interface IEventoMgr extends IEventoMgt{
    
    private static final string USUARIO_JSON = "usuarios.json";
    private static final string EVENTOS_JSON = "eventos.json";
    private static final string ENTRADAS_JSON = "entradas.json";

    //CONTRUCTOR DONDE LEEMOS LOS JSON

    //FUNCIONALIDADES IComprarEntrada

    //FUNCIONALIDADES IGestionarVenta

    //FUNCIONALIDADES IRealizarEvento

    //FUNCIONALIDADES ITramitarDevolucion

}