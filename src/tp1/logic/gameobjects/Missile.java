package tp1.logic.gameobjects;

import tp1.exceptions.CommandParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Missile extends MovingObject{
	private int cont;
	private boolean exploded;
	
	Missile(){
		super(null, null, Action.RIGHT);
		this.cont = 3;
		this.exploded = false;
	}
	
	public Missile(GameWorld game, Position pos) {
		this(game, pos, Action.RIGHT, 3);
		
	}
	
	public Missile(GameWorld game, Position pos, Action dir, int cont) {
		super(game, pos, dir);
		this.exploded = false;
		this.cont = cont;
	}
	
	public Missile(Missile other) {
		this(other.game, other.getPosition(), other.getDirection(), other.getCont());
	}

	@Override
	protected MovingObject createMovingObject(GameWorld game, Position pos, Action dir, String[] objWords)
			throws ObjectParseException {
		if(objWords.length == 2) {
			dir = Action.RIGHT;
		}
		int cont = 3;
		if(objWords.length == 4) {
			try {
				cont = Integer.parseInt(objWords[3]);
			} catch (NumberFormatException e) {
				throw new ObjectParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(objWords[3]), e);
			}

		}
		
		return new Missile(game, pos, dir, cont);
	}
	
	@Override
	public Missile parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		if (objWords.length > 4)
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", objWords)));

		Missile missile = (Missile) super.parse(objWords, game);

		return missile;
	}
	
	@Override
	public void update() {
		if(cont > 0) {
			moveHorizontal(getDirection());
			requestInteractions();
		}
	}

	@Override
	public String getIcon() {
		return Messages.MISSILE;
	}

	@Override
	protected String getName() {
		
		return "Missile";
	}
	
	public int getCont() {
		return cont;
	}
	
	@Override
	public String toString() {
		return getPosition().toString() + " " + "Missile" + " " + getDirection().toString() + " " + getCont();
	}

	@Override
	protected String getShort() {

		return "MS";
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
	public boolean receiveInteraction(Mushroom mushroom) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Box box) {
		return false;
	}

	@Override
	public GameObject copy() {
		return new Missile(this);
	}
}
