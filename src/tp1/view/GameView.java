//Grupo 15: Pablo Arlandis Ocaña 
package tp1.view;

import tp1.logic.*;

public abstract class GameView implements ViewInterface {
	protected GameStatus game;

	public GameView(GameStatus game) {
		this.game = game;
	}

}
