//Grupo 15: Pablo Arlandis Ocaña
package tp1.logic.gameobjects;

import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.*;
import tp1.view.Messages;

public class Mario extends MovingObject implements Player {
	private boolean big;
	private ActionList acciones;
	private Action lastDirH;

	public Mario() {
		super(null, null, Action.RIGHT);
	}

	public Mario(GameWorld game, Position pos) {
		this(game, pos, Action.RIGHT, true);
	}

	private Mario(Mario other) { 
		this(other.game, other.getPosition(), other.getDirection(), other.big);
		this.acciones = new ActionList();
		// this.lastDirH = other.lastDirH;
	}

	private Mario(GameWorld game, Position pos, Action dir, boolean isBig) {
		super(game, pos, dir);
		this.acciones = new ActionList();
		this.big = isBig;
		this.lastDirH = dir;
	}

	@Override
	protected MovingObject createMovingObject(GameWorld game, Position pos, Action dir, String[] objWords)
			throws ObjectParseException {
		if (objWords.length == 2)
			dir = Action.RIGHT;
		boolean isBig = true;
		if (objWords.length == 4) {
			String size = objWords[3].toUpperCase();
			if (size.equals("SMALL") || size.equals("S"))
				isBig = false;
			else if (size.equals("BIG") || size.equals("B"))
				isBig = true;
			else
				throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", objWords)));
		}
		return new Mario(game, pos, dir, isBig);
	}

	@Override
	public Mario parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		if (objWords.length > 4)
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", objWords)));

		Mario mario = (Mario) super.parse(objWords, game);

		return mario;
	}

	@Override
	public void update() {
		if (!this.acciones.isEmpty()) {

			for (Action a : acciones) {
				doAction(a);
				if (isAlive())
					requestInteractions();
			}
			acciones.clear();
		} else {
			super.update();
		}
	}

	private void doAction(Action a) {
		
		if(a == Action.SPARKLES) {
			Sparkles sparkles = new Sparkles(game, getPosition(), getDirection(), 2);
			addObject(sparkles);
			sparkles.update();
		}
		else {
			if (a == Action.UP) {
				if (canMove(a))
					jump();
			} else if (a == Action.DOWN) {
				if (!stay()) {
					while (!fall() && isAlive()) {
						requestInteractions();
					}
				}
			} else {

				moveHorizontal(a);

			}
		}
	}

	@Override
	protected boolean canMove(Action action) {
		if (big) {
			return (super.canMove(getPosition().move(action)) && super.canMove(headPosition().move(action)));
		} else
			return super.canMove(getPosition().move(action));
	}

	private Position headPosition() {
		return getPosition().move(Action.UP);
	}

	@Override
	public void addActionList(List<Action> newActions) {
		for (Action action : newActions) {
			this.acciones.add(action);
		}
	}

	@Override
	public boolean isInPosition(Position p) {
		return isAlive() && (super.isInPosition(p) || (big && p.equals(headPosition())));
	}

	@Override
	public void die() {
		super.die();
		big = false;
		game.killPlayer();
	}

	public void eatMushroom() {
		if (!this.big) {
			big = true;
		}
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

	public void MakeDamage() {
		if (this.big) {
			big = false;
		} else
			this.die();
	}

	@Override
	public boolean receiveInteraction(Goomba goomba) {
		boolean interacted = false;

		if (isInPosition(goomba.getPosition())) {
			if (!isFalling())
				MakeDamage();
			goomba.die();
			interacted = true;
			game.addPoints(100);
		}

		return interacted;
	}

	@Override
	public boolean receiveInteraction(Mushroom mushroom) {
		boolean interacted = false;

		if (isInPosition(mushroom.getPosition())) {
			eatMushroom();
			mushroom.die();
			interacted = true;
		}

		return interacted;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}

	@Override
	public String toString() {
		String size = big ? "BIG" : "SMALL";
		return getPosition().toString() + " " + Messages.MARIO_NAME + " " + getDirection().toString() + " " + size;
	}

	@Override
	public String getIcon() {
		String icon = Messages.MARIO_STOP;

		if (getDirection() == Action.RIGHT) {
			icon = Messages.MARIO_RIGHT;
		} else if (getDirection() == Action.LEFT) {
			icon = Messages.MARIO_LEFT;
		} else if (getDirection() == Action.STOP) {
			icon = Messages.MARIO_STOP;
		} else {
			if (lastDirH == Action.RIGHT) {
				icon = Messages.MARIO_RIGHT;
			} else if (lastDirH == Action.LEFT) {
				icon = Messages.MARIO_LEFT;
			}
		}

		return icon;
	}

	@Override
	protected String getName() {
		return Messages.MARIO_NAME;
	}

	@Override
	protected String getShort() {
		return Messages.MARIO_SHORTCUT;
	}

	@Override
	public GameObject copy() {
		return new Mario(this);
	}

	@Override
	public boolean receiveInteraction(Sparkles sparkles) { //Nose si le da tmb
		boolean interacted = false;
		if(sparkles.isInPosition(getPosition())) {
			interacted = true;
			this.spark();
		}
		
		return interacted;
	}

}
