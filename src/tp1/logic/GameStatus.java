//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic;

public interface GameStatus {
    boolean playerWins();
    boolean playerLoses();
    boolean gameEnd();
    int remainingTime();
    int points();
    int numLives();
    String positionToString(int col, int row);
}