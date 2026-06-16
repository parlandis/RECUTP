//Grupo 15: Pablo Arlandis Ocaña
package tp1.logic;

import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import tp1.exceptions.*;

public class Game implements GameModel, GameStatus, GameWorld {

	private int remainingTime;
	private int lives;
	private int points;
	private int nLevel;

	private GameObjectContainer gameObjects;

	private boolean win;
	private boolean end;
	private boolean loose;

	private Mario mario;

	private GameConfiguration fileloader;

	public Game(int nLevel) {
		this(nLevel, 0, 3);
		this.remainingTime = 100;
	}

	private Game(int nLevel, int points, int lives) {

		this.lives = lives;
		this.points = points;
		this.win = false;
		this.end = false;
		this.loose = false;
		this.nLevel = nLevel;
		this.gameObjects = new GameObjectContainer();
		intitLevel(nLevel);
	}
	
	public void teleport(Position posIni, Position posfin) {
		gameObjects.teleport(posIni, posfin);
	}

	public void update() {
		if (remainingTime > 0) {
			remainingTime--;
			gameObjects.update();

		} else
			end = true;
	}

	@Override
	public void save(String fileName) throws GameModelException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write(this.remainingTime + " " + this.points + " " + this.lives);
			writer.newLine();
			writer.write(this.gameObjects.ObjectsInStr());

		} catch (IOException e) {
			throw new GameModelException(Messages.ERROR.formatted(fileName), e);
		}
	}

	@Override
	public void load(String fileName) throws GameLoadException {

		this.fileloader = new FileGameConfiguration(fileName, this);
		initFromConfig(this.fileloader);
	}

	private void initFromConfig(GameConfiguration cfg) {
		this.remainingTime = cfg.getRemainingTime();
		this.points = cfg.points();
		this.lives = cfg.numLives();
		this.gameObjects = new GameObjectContainer();

		this.mario = cfg.getMario();
		if (this.mario != null) {
			this.gameObjects.add(this.mario);
		}

		for (GameObject obj : cfg.getNPCObjects()) {
			this.gameObjects.add(obj);
		}
	}

	public void resetConfig() {
		this.win = false;
		this.end = false;
		this.mario = null;
		if (this.fileloader != null) {
			int point = this.points;
			int live = this.lives;
			initFromConfig(this.fileloader);
			this.points = point;
			this.lives = live;
		} else
			intitLevel(this.nLevel);
	}

	public void reset() {
		this.win = false;
		this.end = false;

		if (this.fileloader != null) {
			int point = this.points;
			int live = this.lives;
			initFromConfig(this.fileloader);
			this.points = point;
			this.lives = live;
		} else {
			intitLevel(this.nLevel);
		}
	}

	public boolean reset(int nLevel) {
		boolean resetado = true;

		if (nLevel < -1 || nLevel > 2) {
			resetado = false;
		} else {
			this.nLevel = nLevel;
			if (this.fileloader != null) {
				this.points = this.fileloader.points();
				this.lives = this.fileloader.numLives();
				intitLevel(nLevel);
			} else {
				reset();
			}
		}

		return resetado;
	}

	public void exit() {
		this.end = true;
	}

	public boolean isFinished() {
		return this.playerLoses() || this.playerWins() || this.gameEnd();
	}

	@Override
	public void addActionList(List<Action> actions) {
		if (mario != null) {
			mario.addActionList(actions);
		}
	}

	@Override
	public void addObject(String[] objWords) throws OffBoardException, GameParseException {
		try {
			Mario mario = new Mario().parse(objWords, this);
			GameObject obj = mario;

			if (mario != null) {
				this.mario = mario;
				this.gameObjects.add(obj);
			} else {

				obj = GameObjectFactory.parse(objWords, this);
				this.gameObjects.add(obj);
			}
		} catch (ObjectParseException e) {
			throw e;
		} catch (OffBoardException e) {
			throw e;
		} catch (PositionParseException e) {
			throw new ObjectParseException(Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", objWords)), e);
		}

	}

	@Override
	public boolean isSolid(Position pos) {
		return gameObjects.isSolid(pos);
	}

	public boolean isValidPosition(Position pos) {
		return pos.isValid() && !this.gameObjects.isAlive(pos);
	}

	@Override
	public void doInteractionsFrom(GameItem item) {
		gameObjects.doInteractionFrom(item);
	}

	@Override
	public void addToGame(GameObject obj) {
		gameObjects.add(obj);
	}

	@Override
	public void killPlayer() {
		this.lives--;
		if (this.lives > 0) {
			reset();
		} else
			end = true;
	}

	@Override
	public void addPoints(int points) {
		this.points += points;
	}

	@Override
	public void winGame() {
		this.points += this.remainingTime * 10;
		this.remainingTime = 0;
		this.win = true;
		this.end = true;

	}

	public boolean playerWins() {
		return this.win;
	}

	public boolean playerLoses() {
		return this.lives == 0 || loose;
	}

	public boolean gameEnd() {
		return end;
	}

	public int remainingTime() {
		return this.remainingTime;
	}

	public int points() {
		return this.points;
	}

	public int numLives() {
		return lives;
	}

	@Override
	public String toString() {
		return "Game(level=" + nLevel +
				", time=" + remainingTime +
				", points=" + points +
				", lives=" + lives +
				", win=" + win +
				", end=" + end + ")";
	}

	public String positionToString(int col, int row) {
		Position pos = new Position(row, col);

		return this.gameObjects.positionToString(pos);
	}

	private void intitLevel(int nLevel) {
		switch (nLevel) {
			case 0:
				initLevel0();
				break;
			case 1:
				initLevel1();
				break;
			case -1:
				initLevelVacio();
				break;
			case 2:
				initLevel2();
				break;
			default:
		}
	}

	private void initLevelVacio() {
		gameObjects = new GameObjectContainer();
		this.lives = 3;
		this.points = 0;
		this.remainingTime = 100;
	}

	private void initLevel1() {
		initLevel0();
		this.nLevel = 1;

		gameObjects.add(new Goomba(this, new Position(12, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(4, 6)));
	}

	private void initLevel0() {
		this.remainingTime = 100;

		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for (int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13, col)));
			gameObjects.add(new Land(this, new Position(14, col)));
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y - 3, 9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y - 3, 12)));
		for (int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y - 2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y - 1, col)));
		}

		gameObjects.add(new Land(this, new Position(9, 2)));
		gameObjects.add(new Land(this, new Position(9, 5)));
		gameObjects.add(new Land(this, new Position(9, 6)));
		gameObjects.add(new Land(this, new Position(9, 7)));
		gameObjects.add(new Land(this, new Position(5, 6)));

		// Salto final
		int tamX = 8, tamY = 8;
		int posIniX = Game.DIM_X - 3 - tamX, posIniY = Game.DIM_Y - 3;

		for (int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col + 1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY - fila, posIniX + col)));
			}
		}

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y - 3, Game.DIM_X - 1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y - 3, 0));
		gameObjects.add(this.mario);
		gameObjects.add(new Goomba(this, new Position(0, 19)));
	}

	private void initLevel2() {
		initLevel1();
		this.nLevel = 2;

		gameObjects.add(new Box(this, new Position(9, 4)));
		gameObjects.add(new Mushroom(this, new Position(12, 8)));
		gameObjects.add(new Mushroom(this, new Position(2, 20)));
	}

}
