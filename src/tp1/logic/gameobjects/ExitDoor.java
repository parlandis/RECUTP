//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic.gameobjects;

import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.logic.*;
import tp1.view.Messages;

public class ExitDoor extends GameObject {

	ExitDoor() {
		super(null, null);
	}

	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos);
	}

	@Override
	protected ExitDoor newObj(GameWorld game, Position pos, String[] words) throws ObjectParseException {
		if (words.length > 2) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", words)));
		}
		return new ExitDoor(game, pos);
	}

	@Override
	public void update() {
	}

	@Override
	public String getIcon() {
		return Messages.EXIT_DOOR;
	}

	@Override
	public boolean interactWith(GameItem other) {
		return other.isInPosition(getPosition()) && other.isAlive() && other.receiveInteraction(this);
	}

	@Override
	public boolean receiveInteraction(Land land) {
		return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor exitDoor) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		boolean interacted = false;

		if (mario.isInPosition(getPosition())) {
			interacted = true;
			game.winGame();
		}

		return interacted;
	}

	@Override
	public boolean receiveInteraction(Goomba goomba) {
		return false;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean isSolid(Position pos) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}

	public void addActionList(List<Action> acciones) {
	}

	public ExitDoor(ExitDoor other) {
		this(other.game, other.getPosition());
	}

	@Override
	protected String getName() {
		return Messages.EXITDOOR_NAME;
	}

	@Override
	public GameObject copy() {
		return new ExitDoor(this);
	}

	@Override
	protected String getShort() {
		return Messages.EXITDOOR_SHORTCUT;
	}

	@Override
	public String toString() {
		return getPosition().toString() + " " + Messages.EXITDOOR_NAME;
	}

	@Override
	public void reverse() {
		
		
	}

	@Override
	public boolean receiveInteraction(Grenade grenade) {
		// TODO Auto-generated method stub
		return false;
	}
}
