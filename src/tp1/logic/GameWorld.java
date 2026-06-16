//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic;

import tp1.logic.gameobjects.GameObject;


public interface GameWorld {
	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	boolean isSolid(Position pos);
    boolean isValidPosition(Position pos);
    
    void killPlayer();
    void addPoints(int points);
    void winGame(); 
   
    void doInteractionsFrom(GameItem item);

    void addToGame(GameObject obj);


}