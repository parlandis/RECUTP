//Grupo 15: Pablo Arlandis Ocaña - Pablo Pérez Rey
package tp1.exceptions;

public class PositionParseException extends GameParseException {
    private static final long serialVersionUID = 1L;

    PositionParseException() {
        super();
    }

    public PositionParseException(String message) {
        super(message);
    }

    public PositionParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PositionParseException(Throwable cause) {
        super(cause);
    }
}
