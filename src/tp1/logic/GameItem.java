// Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic;

import tp1.logic.gameobjects.*;

public interface GameItem {
    boolean isSolid();
    boolean isAlive();
    boolean isInPosition(Position pos);
    
    boolean interactWith(GameItem item);
    
    boolean receiveInteraction(Land obj);
    boolean receiveInteraction(ExitDoor obj);
    boolean receiveInteraction(Mario obj);
    boolean receiveInteraction(Goomba obj);
    boolean receiveInteraction(Mushroom obj);
    boolean receiveInteraction(Box obj);   
    void verticalReverse();
}