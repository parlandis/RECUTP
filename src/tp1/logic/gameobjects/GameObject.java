//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.view.Messages;
import tp1.logic.Action;
import tp1.logic.*;
import tp1.logic.Position;

public abstract class GameObject implements GameItem {

	private Position pos;
	private boolean isAlive;
	protected GameWorld game;

	public GameObject(GameWorld game, Position pos) {
		this.pos = pos;
		this.game = game;
		this.isAlive = true;
	}

	protected Position getPosition() {
		return pos;
	}

	protected void move(Action dir) {
		this.pos = this.pos.move(dir);
	}

	public GameObject parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		GameObject obj = null;

		if (objWords.length < 2)
			throw new ObjectParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);

		if (matchCommandName(objWords[1])) {
			Position p;
			try {
				p = Position.parse(objWords[0]);
			} catch (PositionParseException ppe) {
				throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)),
						ppe);
			}

			if (!p.isValid())
				throw new OffBoardException(Messages.OFF_BOARD_POSITION.formatted(String.join(" ", objWords)));
			obj = newObj(game, p, objWords);
		}

		return obj;
	}

	protected abstract GameObject newObj(GameWorld game, Position p, String[] objWords) throws ObjectParseException;

	protected abstract String getName();

	protected abstract String getShort();

	protected boolean matchCommandName(String name) {
		return getName().equalsIgnoreCase(name) || getShort().equalsIgnoreCase(name);
	}
	
	@Override
	public boolean menor1(Position pos) {
		return pos.menor1(this.getPosition());
	}

	@Override
	public boolean isInPosition(Position p) {
		return isAlive && this.pos.equals(p);
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}

	protected void die() {
		this.isAlive = false;
	}
	
	public abstract void reverse();
	


	public abstract boolean isSolid(Position pos);

	public abstract boolean isSolid();

	public abstract void update();

	public abstract String getIcon();

	protected void addObject(GameObject obj) {
		game.addToGame(obj);
	}

	protected void requestInteractions() {
		this.game.doInteractionsFrom(this);
	}
	
	protected boolean isSolidAt(Position position) {
		return this.game.isSolid(position);
	}

	protected boolean isValidPosition(Position position) {
		return this.game.isValidPosition(position);
	}

	@Override
	public boolean interactWith(GameItem other) {
		return false;
	}

	@Override
	public abstract boolean receiveInteraction(Land land);
	
	@Override 
	public abstract boolean receiveInteraction(Grenade grenade);

	@Override
	public abstract boolean receiveInteraction(ExitDoor exitDoor);

	@Override
	public abstract boolean receiveInteraction(Mario mario);

	@Override
	public abstract boolean receiveInteraction(Goomba goomba);

	@Override
	public abstract boolean receiveInteraction(Mushroom mushroom);

	@Override
	public abstract boolean receiveInteraction(Box box);

	public abstract GameObject copy();
}
