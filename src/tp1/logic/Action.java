//Grupo 15: Pablo Arlandis Ocaña
package tp1.logic;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1, 0), RIGHT(1, 0), DOWN(0, 1), UP(0, -1), STOP(0, 0), GRANADE(1,1);

	private int x;
	private int y;

	private Action(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static Action parseActions(String action) {
		Action movimiento = null;

		if (action != null) {
			String accion = action.toLowerCase();

			switch (accion) {
				case "u":
				case "up":
					movimiento = Action.UP;
					break;
				case "l":
				case "left":
					movimiento = Action.LEFT;
					break;
				case "gr":
				case "granade":
					movimiento = Action.GRANADE;
					break;
				case "r":
				case "right":
					movimiento = Action.RIGHT;
					break;
				case "d":
				case "down":
					movimiento = Action.DOWN;
					break;
				case "s":
				case "stop":
					movimiento = Action.STOP;
					break;
			}
		}

		return movimiento;
	}

	public Action opuesta() {
		Action accion = STOP;
		switch (this) {
			case LEFT:
				accion = RIGHT;
				break;
			case RIGHT:
				accion = LEFT;
				break;
			case UP:
				accion = DOWN;
				break;
			case DOWN:
				accion = UP;
				break;
			case GRANADE: 
				accion = null;
				break;
		}
		return accion;
	}

	public static Action parse(String str) throws ActionParseException {
		if (str == null)
			throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(str));

		Action act = null;
		if (str != null) {
			switch (str.toUpperCase()) {
				case "L":
				case "LEFT":
					act = LEFT;
					break;
				case "R":
				case "RIGHT":
					act = RIGHT;
					break;
				case "GRANADE":
				case "GR": 
					act = GRANADE;
					break;
				case "U":
				case "UP":
					break;
				case "D":
				case "DOWN":
					break;
				case "S":
				case "STOP":
					act = STOP;
					break;
				
				default:
					throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(str));
			}
		}
		return act;
	}

	public boolean isHorizontal() {
		return this == LEFT || this == RIGHT;
	}

	public boolean isVertical() {
		return this == UP || this == DOWN;
	}
}
