//Grupo 15: Pablo Arlandis Ocaña - Pablo Pérez Rey
package tp1.exceptions;

public class CommandException extends Exception{
	private static final long serialVersionUID = 1L;
	
	CommandException() {
		super();
	}
	
	public CommandException(String Message) {
		super(Message);
	}
	
	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
