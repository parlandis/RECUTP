//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.*;
import tp1.view.Messages;

public class Goomba extends MovingObject {

	Goomba() {
		super(null, null, Action.LEFT);
	}

	public Goomba(GameWorld game, Position pos) {
		this(game, pos, Action.LEFT);

	}

	private Goomba(GameWorld game, Position pos, Action dir) {
		super(game, pos, dir);
	}

	public Goomba(Goomba other) {
		this(other.game, other.getPosition(), other.getDirection());
	}

	@Override
	protected MovingObject createMovingObject(GameWorld game, Position pos, Action dir, String[] objWords)
			throws ObjectParseException {
		if (objWords.length == 2)
			dir = Action.LEFT;
		return new Goomba(game, pos, dir);
	}

	@Override
	public Goomba parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		if (objWords.length > 3)
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", objWords)));
		return (Goomba) super.parse(objWords, game);
	}

	@Override
	public String getIcon() {
		return Messages.GOOMBA;
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

			if (!mario.isFalling()) {
				mario.receiveInteraction(this);
			} else
				game.addPoints(100);
			super.die();
		}
		return interacted;
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
	public String toString() {
		return getPosition().toString() + " " + Messages.GOOMBA_NAME + " " + getDirection().toString();
	}

	@Override
	protected String getName() {
		return Messages.GOOMBA_NAME;
	}

	@Override
	protected String getShort() {
		return Messages.GOOMBA_SHORTCUT;
	}

	@Override
	public GameObject copy() {
		return new Goomba(this);
	}

	@Override
	public boolean receiveInteraction(Grenade grenade) {
		boolean interacted = false;
		if(grenade.isExploded()) {
			if(grenade.menor1(getPosition())) {
				interacted = true;
				game.addPoints(100);
				super.die();
			}
		}
		

		return interacted;
	}

}
