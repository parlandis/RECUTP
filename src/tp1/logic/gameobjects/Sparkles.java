package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Sparkles extends MovingObject {
	private int vel;

	public Sparkles() {
		super(null, null, Action.RIGHT);
		this.vel = 2;
	}
	
	public Sparkles(GameWorld game, Position pos) {
		this(game, pos, Action.RIGHT, 2);
	}
	
	protected Sparkles(GameWorld game, Position pos, Action dir, int vel) {
		super(game, pos, dir);
		this.vel = vel;
	}

	public Sparkles(Sparkles other) {
		this(other.game, other.getPosition(), other.getDirection(), other.vel);
	}

	@Override
	protected MovingObject createMovingObject(GameWorld game, Position pos, Action dir, String[] objWords)
			throws ObjectParseException {
		if (objWords.length == 2)
			dir = Action.RIGHT;
		int vel = 2;
		if(objWords.length == 4) {
			try {
				vel = Integer.parseInt(objWords[3]);
			} catch (NumberFormatException e) {
				throw new NumberFormatException();
			}
		}

		return new Sparkles(game, pos, dir, vel);
	}
	
	@Override
	public Sparkles parse(String[] objWords, GameWorld game) throws ObjectParseException, OffBoardException {
		if (objWords.length > 4)
			throw new ObjectParseException(Messages.OBJECT_PARSE_TOO_MANY_ARGS.formatted(String.join(" ", objWords)));

		Sparkles sparkles = (Sparkles ) super.parse(objWords, game);

		return sparkles ;
	}
	
	private void moveHS(Action dir) {
		move(dir);
		if(!getPosition().isValid())super.die();
	}
	
	public void update() {
		int cont = 0; 
		while(cont < vel && this.isAlive()) {
			moveHS(getDirection());
			requestInteractions();
			cont++;
		}
		vel++;
	}
	
	@Override 
	public String getIcon() {
		return "💡";
	}

	@Override
	protected String getName() {
		return "Sparkles";
	}

	@Override
	protected String getShort() {
		return "SS";
	}
	
	
	
	@Override
	public boolean interactWith(GameItem other) {
		return other.isInPosition(getPosition()) && other.isAlive() && other.receiveInteraction(this);
	}
	
	@Override
	public boolean receiveInteraction(Mario mario) {
		boolean interacted = false;
		if(mario.isInPosition(getPosition())) {
			interacted = true;
			this.spark();
		}
		
		return interacted;
	}
	
	
	@Override
	public boolean receiveInteraction(Goomba goomba) {
		boolean interacted = false;
		if(goomba.isInPosition(getPosition())) {
			interacted = true;
			this.spark();
		}
		
		return interacted;
	}

	@Override
	public boolean receiveInteraction(Land land) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor exitDoor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveInteraction(Mushroom mushroom) {
		boolean interacted = false;
		if(mushroom.isInPosition(getPosition())) {
			interacted = true;
			this.spark();
		}
		
		return interacted;
	}

	@Override
	public boolean receiveInteraction(Box box) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameObject copy() {
		return new Sparkles(this);
	}
	
	@Override
	public boolean receiveInteraction(Sparkles sparkles) {
		boolean interacted = false;
		if(sparkles.isInPosition(getPosition())) {
			interacted = true;
			sparkles.die();
		}
		return interacted;
	}
	
	
}
