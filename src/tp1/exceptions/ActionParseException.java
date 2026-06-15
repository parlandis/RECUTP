//Grupo 15: Pablo Arlandis Ocaña - Pablo Pérez Rey
package tp1.exceptions;

public class ActionParseException extends GameParseException {
    private static final long serialVersionUID = 1L; // Nose para que es esto el profe de tp2 me dijo que no me preocupe

    ActionParseException() {
        super();
    }

    public ActionParseException(String message) {
        super(message);
    }

    public ActionParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionParseException(Throwable cause) {
        super(cause);
    }
}
