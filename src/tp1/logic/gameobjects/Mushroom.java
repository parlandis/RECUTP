//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject {

	Mushroom() {
		super(null, null, null);
	}

	public Mushroom(GameWorld game, Position pos) {
		this(game, pos, Action.RIGHT);
	}

	private Mushroom(GameWorld game, Position pos, Action dir) {
		super(game, pos, dir);
	}

	@Override
	public String getIcon() {
		return Messages.MUSHROOM;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	protected MovingObject createMovingObject(GameWorld game, Position pos, Action dir, String[] objWords)
			throws ObjectParseException {
		if (objWords.length == 2)
			dir = Action.RIGHT;
		return new Mushroom(game, pos, dir);
	}

	@Override
	public Mushroom parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		if (objWords.length > 3)
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", objWords)));
		return (Mushroom) super.parse(objWords, game);
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		boolean interacted = mario.receiveInteraction(this);

		if (interacted) {
			this.die();
		}
		return interacted;
	}

	@Override
	public boolean interactWith(GameItem other) {

		return other.isAlive() && other.receiveInteraction(this);
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
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}

	@Override
	protected String getName() {
		return Messages.MUSHROOM_NAME;
	}

	public Mushroom(Mushroom other) {
		this(other.game, other.getPosition(), other.getDirection());
	}

	@Override
	protected String getShort() {
		return Messages.MUSHROOM_SHORTCUT;
	}

	@Override
	public String toString() {
		return getPosition().toString() + " " + Messages.MUSHROOM_NAME + " " + getDirection().toString();
	}

	@Override
	public GameObject copy() {
		return new Mushroom(this);
	}

	@Override
	public boolean receiveInteraction(Missile missile) {
		boolean interacted = false;
		if(missile.isInPosition(getPosition())) {
			interacted = true;
			//game.addPoints(100);
			missile.receiveInteraction(this);
			super.die();
		}
		return interacted;
	}
}
