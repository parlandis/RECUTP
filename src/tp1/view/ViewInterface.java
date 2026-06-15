//Grupo 15: Pablo Arlandis Ocaña - Pablo Pérez Rey
package tp1.view;

public interface ViewInterface {
	// show methods
	public void showWelcome();
	public void showGame();
	public void showEndMessage();
	public void showError(String message);
	public void showMessage(String message);

	// get data from view methods
	public String[] getPrompt();
}
