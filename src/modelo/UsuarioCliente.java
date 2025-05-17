package modelo;

public class UsuarioCliente extends Usuario {
    private String dni;
    private String nombre;

    public UsuarioCliente(String email, String contraseña, String dni, String nombre) {
        super(email, contraseña);
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }
}

