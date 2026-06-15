//Grupo 15: Pablo Arlandis Ocaña 
package tp1.exceptions;

public class ObjectParseException extends GameParseException {
    private static final long serialVersionUID = 1L;

    ObjectParseException() {
        super();
    }

    public ObjectParseException(String message) {
        super(message);
    }

    public ObjectParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectParseException(Throwable cause) {
        super(cause);
    }
}
