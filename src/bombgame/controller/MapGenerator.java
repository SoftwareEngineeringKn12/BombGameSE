package bombgame.controller;

import java.util.Random;
import bombgame.entities.GameObject;
import bombgame.entities.Wall;

/**
 * Methods to generate a 2D Matrix (field) of GameObject
 * @author jens
 *
 */
public final class MapGenerator {
	
	private MapGenerator() { }
	
	/**
	 * Test Method - only for testing (no nice algorithm)
	 * @return 2-Dimensional GameObject Array
	 */
	public static GameObject[][] generateTestMap(int width, int height) {
		GameObject[][] field = new GameObject[width][height];
		Random rand = new Random();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if ((rand.nextInt() % 2) == 0)
					field[i][j] = new Wall(i, j);
			}
		}
		
		return field;
	}
}
