//Grupo 15: Pablo Arlandis Ocaña
package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.view.Messages;

import java.util.Arrays;
import java.util.List;


public class GameObjectFactory {
	private static final List<GameObject> listaObjectos = Arrays.asList(
			new Missile(),
	        new Mario(), 
			new Land(),
	        new ExitDoor(),
	        new Goomba(),
	        new Box(),
	        new Mushroom()
	);

	public static GameObject parse(String[] objWords, GameWorld game) throws GameParseException, OffBoardException  {
		GameObject obj = null;

		for(GameObject base : listaObjectos) {
			  obj = base.parse(objWords, game);
	            if(obj != null) {
	            	return obj;
	            }
		}

	throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(String.join(" ", objWords)));
	}
}
