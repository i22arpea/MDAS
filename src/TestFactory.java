import modelo.Usuario;
import modelo.UsuarioFactory;
import org.json.JSONObject;

public class TestFactory {
    public static void main(String[] args) {
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("tipo", "cliente");
        jsonCliente.put("email", "cliente@correo.com");
        jsonCliente.put("contrasena", "1234");
        jsonCliente.put("dni", "12345678A");
        jsonCliente.put("nombre", "Pepe");

        Usuario usuario = UsuarioFactory.crearUsuarioDesdeJSON(jsonCliente);

        System.out.println("Instancia creada: " + usuario.getClass().getSimpleName());
        System.out.println(usuario.toJSONObject().toString(2));
    }
}
