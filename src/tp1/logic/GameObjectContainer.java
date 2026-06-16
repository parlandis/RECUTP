//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic;

import java.util.ArrayList;
import java.util.List;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

public class GameObjectContainer {

    private List<GameObject> gameObjects;

    public GameObjectContainer() {
        gameObjects = new ArrayList<>();
    }

    public GameObjectContainer(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void add(GameObject obj) {
        if (obj != null)
            gameObjects.add(obj);
    }

    public void update() {
        List<GameObject> copia = new ArrayList<>(gameObjects);

        for (GameObject obj : copia) {
            if (obj.isAlive()) {

                obj.update();
            }
        }

        removeDeadObjects();
    }
    
	public void teleport(Position posIni, Position posfin) {
        List<GameObject> copia = new ArrayList<>(gameObjects);

        for (GameObject obj : copia) {
        	if(obj.isInPosition(posIni)) {
        		obj.teleport(posfin);
        	}
        }
		
	}

    public void doInteractionFrom(GameItem item) {
        List<GameObject> copia = new ArrayList<>(gameObjects);

        for (GameObject obj : copia) {
            if (item.isAlive() && obj.isAlive() && !obj.equals(item)) {
                item.interactWith(obj);
            }
        }
    }

    public boolean isAlive(Position pos) {
        boolean living = false;

        for (GameObject obj : gameObjects) {
            if (obj.isInPosition(pos) && !obj.isAlive()) {
                living = true;
                return living;
            }
        }

        return living;
    }

    public void removeDeadObjects() {
        List<GameObject> vivos = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (obj.isAlive()) {
                vivos.add(obj);
            }
        }
        gameObjects = vivos;
    }

    public boolean isSolid(Position pos) {
        boolean solid = false;
        for (GameObject obj : gameObjects) {
            if (obj.isAlive() && obj.isInPosition(pos) && obj.isSolid()) {
                solid = true;
            }
        }
        return solid;
    }

    public String positionToString(Position pos) {
        StringBuilder result = new StringBuilder();

        for (GameObject obj : gameObjects) {
            if (obj.isAlive() && obj.isInPosition(pos)) {
                result.append(obj.getIcon());
            }
        }

        return result.length() > 0 ? result.toString() : Messages.EMPTY;
    }

    public String ObjectsInStr() {
        StringBuilder sb = new StringBuilder();
        for (GameObject obj : gameObjects) {
            if (obj.isAlive()) {
                sb.append(obj.toString()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (GameObject obj : gameObjects) {
            if (obj.isAlive()) {
                sb.append(obj.toString()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }


}
