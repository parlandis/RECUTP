//Grupo 15: Pablo Arlandis Ocaña 
package tp1.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.GameLoadException;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {

	private int time;
	private int points;
	private int lives;

	private Mario mario;
	private List<GameObject> npcs;

	public FileGameConfiguration(String filename, GameWorld game) throws GameLoadException {
		this.npcs = new ArrayList<GameObject>();
		this.mario = null;

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String status = br.readLine();
			if (status == null)
				throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(filename));

			parseGameStatus(status);

			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty()) {
					continue;
				}

				String[] objWords = line.split("\\s+");

				Mario m = new Mario().parse(objWords, game);
				if (m != null) {
					this.mario = m;

				} else {
					GameObject obj = GameObjectFactory.parse(objWords, game);
					this.npcs.add(obj);
				}
			}

			if (this.mario == null) {
				throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(filename));
			}

		} catch (FileNotFoundException fnfe) {
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(filename), fnfe);
		} catch (IOException ioe) {
			throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(filename), ioe);
		} catch (GameLoadException gle) {
			throw gle;
		} catch (Exception e) {
			throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(filename), e);
		}
	}

	private void parseGameStatus(String status) throws GameLoadException {
		try {
			String[] conf = status.trim().split("\\s+");

			if (conf.length != 3)
				throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(status));

			this.time = Integer.parseInt(conf[0]);
			this.points = Integer.parseInt(conf[1]);
			this.lives = Integer.parseInt(conf[2]);

		} catch (NumberFormatException e) {
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(status));
		}
	}

	@Override
	public int getRemainingTime() {
		return this.time;
	}

	@Override
	public int points() {
		return this.points;
	}

	@Override
	public int numLives() {
		return this.lives;
	}

	@Override
	public Mario getMario() {
		return (Mario) this.mario.copy();
	}

	@Override
	public List<GameObject> getNPCObjects() {
		List<GameObject> copyNPCS = new ArrayList<>();
		for (GameObject obj : npcs) {
			copyNPCS.add(obj.copy());
		}
		return copyNPCS;
	}
}
