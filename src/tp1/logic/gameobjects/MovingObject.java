//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.*;
import tp1.view.Messages;

public abstract class MovingObject extends GameObject {
	private Action direction;
	private boolean isFalling;

	public MovingObject(GameWorld game, Position pos, Action direction) {
		super(game, pos);
		this.isFalling = false;
		this.direction = direction;
	}

	@Override
	protected GameObject newObj(GameWorld game, Position pos, String[] objWords) throws ObjectParseException {
		Action dir = null;

		if (objWords.length > 2) {
			try {
				dir = Action.parse(objWords[2]);
				if (dir == null) {
					throw new ObjectParseException(
							Messages.INVALID_MOVING_OBJECT_DIRECTION.formatted(String.join(" ", objWords)));
				}

			} catch (ActionParseException ape) {
				throw new ObjectParseException(
						Messages.UNKNOWN_MOVING_OBJECT_DIRECTION.formatted(String.join(" ", objWords)), ape);
			}
			// No modificar this.direction aquí; dir ya se pasa a createMovingObject
		}

		return createMovingObject(game, pos, dir, objWords);
	}

	protected abstract MovingObject createMovingObject(GameWorld game, Position pos, Action dir, String[] objWords)
			throws ObjectParseException;

	@Override
	public MovingObject parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		MovingObject obj = (MovingObject) super.parse(objWords, game);
		return obj;
	}

	@Override
	public boolean isSolid(Position pos) {
		return isSolidAt(pos);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	protected boolean canMove(Position p) {
		return isValidPosition(p) && !isSolidAt(p);
	}

	protected boolean canMove(Action dir) {
		return false;
	}

	protected boolean isFalling() {
		return isFalling;
	}
	
	//Reverse apartado a
	@Override
	public void reverse() {
		this.direction = this.direction.opuesta(); 
	}

	@Override
	public void update() {
		fall();
		if (isAlive()) {
			if (!isFalling) {
				moveHorizontal(this.direction);
			}
			if (!isValidPosition(getPosition())) {
				die();
			} else
				requestInteractions();
		}
	}

	protected boolean fall() {
		Position down = getPosition().move(Action.DOWN);
		boolean stoped = false;
		if (!isValidPosition(down)) {
			move(Action.DOWN);
			die();
		} else {
			if (!isSolidAt(down)) {
				move(Action.DOWN);
				this.isFalling = true;
			} else {
				stoped = true;
				this.isFalling = false;
			}
		}

		return stoped;
	}

	protected void jump() {
		if (isSolidAt(getPosition().move(Action.DOWN))) {
			this.isFalling = false;
		}
		Position up = getPosition().move(Action.UP);
		if (canMove(up)) {
			move(Action.UP);
		} else
			this.direction = Action.UP.opuesta();
	}

	protected void moveHorizontal(Action dir) {
		Position newPos = getPosition().move(dir);

		if (canMove(newPos)) {
			this.direction = dir;
			move(dir);
		} else {
			this.direction = dir.opuesta();
		}
	}

	protected boolean stay() {
		boolean haySuelo = false;
		if (isSolidAt(getPosition().move(Action.DOWN))) {
			this.direction = Action.STOP;
			haySuelo = true;
		}
		return haySuelo;
	}

	protected Action getDirection() {
		return this.direction;
	}

	@Override
	public String getIcon() {
		return null;
	}

	protected boolean isValidPosition(Position suelo) {
		return suelo.isValid();
	}

	@Override
	public boolean receiveInteraction(Goomba goomba) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Mario mario) {
		return false;
	}
}
