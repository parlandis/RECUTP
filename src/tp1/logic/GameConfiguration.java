package tp1.logic;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;
import java.util.List;

public interface GameConfiguration {
	public int getRemainingTime();

	public int points();

	public int numLives();

	public Mario getMario();

	public List<GameObject> getNPCObjects();
}
