//Grupo 15: Pablo Arlandis Ocaña
package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.logic.*;
import tp1.view.Messages;

public class Land extends GameObject {

	Land() {
		super(null, null);
	}

	public Land(GameWorld game, Position pos) {
		super(game, pos);
	}

	@Override
	public String getIcon() {
		return Messages.LAND;
	}

	@Override
	protected Land newObj(GameWorld game, Position pos, String[] words) throws ObjectParseException {
		if (words.length > 2) {
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", words)));
		}
		return new Land(game, pos);
	}

	@Override
	public void update() {
	}

	@Override
	public boolean isSolid(Position pos) {
		return true;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean interactWith(GameItem other) {
		return false;
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
		return false;
	}

	@Override
	public boolean receiveInteraction(Goomba goomba) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Mushroom mushroom) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Box box) {
		return false;
	}

	@Override
	protected String getName() {
		return Messages.LAND_NAME;
	}

	@Override
	protected String getShort() {
		return Messages.LAND_SHORTCUT;
	}

	public Land(Land other) {
		this(other.game, other.getPosition());
	}

	@Override
	public GameObject copy() {
		return new Land(this);
	}

	@Override
	public String toString() {
		return getPosition().toString() + " " + Messages.LAND_NAME;
	}

	@Override
	public boolean receiveInteraction(Missile missile) {
		boolean interacted = false;
		if(missile.isInPosition(getPosition())) {
			interacted = true;
			missile.explode(); 
			super.die();
		}
		return interacted;
	}
}
