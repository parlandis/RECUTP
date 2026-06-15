//Grupo 15: Pablo Arlandis Ocaña - Pablo Pérez Rey
package tp1.exceptions;

public class CommandExecuteException extends CommandException {
    private static final long serialVersionUID = 1L;

    CommandExecuteException() {
        super();
    }

    public CommandExecuteException(String message) {
        super(message);
    }

    public CommandExecuteException(String message, Throwable cause) {
        super(message, cause);
    }


}
