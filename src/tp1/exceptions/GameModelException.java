//Grupo 15: Pablo Arlandis Ocaña 
package tp1.exceptions;

public class GameModelException extends Exception {
    private static final long serialVersionUID = 1L;

    GameModelException() {
        super();
    }

    public GameModelException(String message) {
        super(message);
    }

    public GameModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameModelException(Throwable cause) {
        super(cause);
    }
}
