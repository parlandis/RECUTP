//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic.gameobjects;

import tp1.view.Messages;

import tp1.exceptions.ObjectParseException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;

public class Box extends GameObject {

	private boolean empty;

	Box() {
		super(null, null);
	}

	public Box(GameWorld game, Position pos) {
		this(game, pos, false);
	}

	private Box(GameWorld game, Position pos, boolean empty) {
		super(game, pos);
		this.empty = empty;
	}

	@Override
	public void update() {

	}

	@Override
	public String getIcon() {
		return empty ? Messages.BOX_EMPTY : Messages.BOX_FULL;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	protected Box newObj(GameWorld game, Position pos, String[] words) throws ObjectParseException {
		boolean isEmpty = false;

		if (words.length == 3) {
			String status = words[2].trim().toUpperCase();
			if (status.equals("EMPTY") || status.equals("E")) {
				isEmpty = true;
			} else if (status.equals("FULL") || status.equals("F")) {
				isEmpty = false;
			} else {
				throw new ObjectParseException(
						Messages.INVALID_BOX_STATUS.formatted(String.join(" ", words)));
			}
		} else if (words.length > 3) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", words)));
		}

		return new Box(game, pos, isEmpty);
	}

	@Override
	public String toString() {
		String status = empty ? "EMPTY" : "FULL";
		return getPosition().toString() + " " + Messages.BOX_NAME + " " + status;
	}

	@Override
	public boolean isSolid(Position pos) {
		return true;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		Position pos = getPosition();
		Position downPos = pos.move(Action.DOWN);
		boolean received = false;
		if (!empty && mario.isInPosition(downPos)) {

			this.empty = true;
			game.addPoints(50);
			Mushroom mushroom = new Mushroom(this.game, pos.move(Action.UP));
			addObject(mushroom);
			received = true;
		}

		return received;
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
	public boolean receiveInteraction(Goomba goomba) {
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

	@Override
	public boolean interactWith(GameItem other) {
		return other.isAlive() && other.receiveInteraction(this);
	}

	@Override
	protected String getName() {
		return Messages.BOX_NAME;
	}

	public Box(Box other) {
		this(other.game, other.getPosition(), other.empty);
	}

	@Override
	protected String getShort() {
		return Messages.BOX_SHORTCUT;
	}

	@Override
	public GameObject copy() {
		return new Box(this);
	}
}
