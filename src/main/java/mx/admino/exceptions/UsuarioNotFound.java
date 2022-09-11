package mx.admino.exceptions;

public class UsuarioNotFound extends RuntimeException {
    public UsuarioNotFound(String username) {
        super("El usuario no existe " + username);
    }
}
