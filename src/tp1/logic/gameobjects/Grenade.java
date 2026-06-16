package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Grenade extends MovingObject {
	private int turnos;
	private boolean exploded; 
	
	Grenade(){
		super(null, null, Action.STOP);
		this.turnos = 3;
		this.exploded = false;
	}
	
	

	public Grenade(GameWorld game, Position pos, Action direction, int turnos) {
		super(game, pos, direction);
		this.turnos = turnos;
		this.exploded = false;
	}
	
	public Grenade(Grenade other) {
		this(other.game, other.getPosition(), other.getDirection(), other.getTurnos());
	}
	
	private int getTurnos() {
		return this.turnos;
	}



	@Override
	protected MovingObject createMovingObject(GameWorld game, Position pos, Action dir, String[] objWords)
			throws ObjectParseException {
		int turnos = 2;
		if (objWords.length == 2) {
			dir = Action.STOP;
		}
		if(objWords.length == 4) {
			try {
				turnos = Integer.parseInt(objWords[3]);
			} catch (NumberFormatException e) {
				throw new ObjectParseException(Messages.INVALID_TURNOS.formatted(objWords[3]), e);
			}
		}
		
			
		return new Grenade(game, pos, dir, turnos);
	}
	
	@Override
	public Grenade parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		if (objWords.length > 4)
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", objWords)));
		return (Grenade) super.parse(objWords, game);
	}
	
	public void moveInit() {
		moveHorizontal(getDirection());
		requestInteractions();
		if(isAlive()) {
			moveHorizontal(getDirection());
			requestInteractions();
		}
		turnos--;
	}
	
	
	@Override
	public void update() {
		//TODO: 
		if(turnos > 0) {
			super.update();
		}
		else {
			exploded = true;
			requestInteractions();
			super.die();
		}
		turnos--;
	}
	
	@Override
	public String getIcon() {
		return Messages.GRENADE;
	}
	
	public boolean isExploded() {
		return exploded;
	}
	
	@Override
	public boolean interactWith(GameItem other) {
		boolean interact = false;
		if(!this.exploded) { // Forma normal no va a interacturar
			interact = other.isInPosition(getPosition()) && other.isAlive() && other.receiveInteraction(this); 
		}
		interact = other.menor1(getPosition()) && other.isAlive() && other.receiveInteraction(this);
		return interact;
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
	public String toString() {
		return getPosition().toString() + " " + Messages.GRENADE_NAME + " " + getDirection().toString() +" "+ turnos;
	}

	@Override
	protected String getName() {
		return Messages.GRENADE_NAME;
	}

	@Override
	protected String getShort() {
		return Messages.GRENADE_SHORTCUT;
	}

	@Override
	public GameObject copy() {
		return new Grenade(this);
	}

	@Override
	public boolean receiveInteraction(Grenade grenade) {
		return false;
	}
	
	
	


	
}
