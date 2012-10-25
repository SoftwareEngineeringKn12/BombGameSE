package bombgame.controller;

import java.util.ArrayList;

import bombgame.entities.Bomb;
import bombgame.entities.GameObject;
import bombgame.entities.Man;


/**
 * This class handles the game mechanics and manages the objects on the field. The field is built as a 2D matrix of GameObjects.
 * @author JeGa, Rookfighter
 *
 */
public final class GameHandler {
	
	/**
	 * matrix holding all GameObjects in the game.
	 * The array indices specify the position on the field.
	 */
	private GameObject field[][];
	
	/**
	 * List holding all Man-objects in the game.
	 */
	private ArrayList<Man> men;
	
	/**
	 * List holding all Bomb-objects in the game.
	 */
	private ArrayList<Bomb> bombs;
	
	/**
	 * Field width and height
	 */
	private static final int fieldWidth = 10;
	private static final int fieldHeight = 10;
	
	
	/**
	 * Creates a new GameHandler including a field of 10x10 elements.
	 */
	public GameHandler() {
		//field = new GameObject[fieldWidth][fieldHeight];
		field = MapGenerator.generateTestMap(fieldWidth, fieldHeight);
		men = new ArrayList<Man>();
		bombs = new ArrayList<Bomb>();
	}
	
	
	/**
	 * Creates a new GameHandler including a field of widthxheight elements.
	 * @param width - width of the new field
	 * @param height - height of the new field
	 */
	public GameHandler(final int width, final int height) {
		//field = new GameObject[width][height];
		field = MapGenerator.generateTestMap(width, height);
		men = new ArrayList<Man>();
		bombs = new ArrayList<Bomb>();
	}
	
	
	/**
	 * Adds the given GameObject to the field and the specified List, if Position is not already in use except Bomb-objects.
	 * They can also be placed onto another GameObject (e.g. a Man-object).
	 * @param obj - GameObject that should be added to the field
	 */
	public void addObject(GameObject obj) {
		if (field[obj.getX()][obj.getY()] != null && !(obj instanceof Bomb)) { 
			return;
		}
		
		if (obj instanceof Man) {
			men.add((Man) obj);
		}
		
		if (obj instanceof Bomb) {
			bombs.add((Bomb) obj);
		}
		
		field[obj.getX()][obj.getY()] = obj;
	}
	
	
	/**
	 * Removes the given GameObject from the field. If the Object is neither on the field nor in one of the Lists,
	 * this method does nothing.
	 * @param obj - GameObject that should be removed
	 */
	public void removeObject(GameObject obj) {
		if (obj instanceof Man) {
			men.remove((Man) obj);
		}
		
		if (obj instanceof Bomb) {
			bombs.remove((Bomb) obj);
		}
		
		field[obj.getX()][obj.getY()] = null;
	}
	
	
	/**
	 * Returns the matrix of the field.
	 * @return - matrix of the field
	 */
	public GameObject[][] getField() {
		return field;
	}
	
	
	/**
	 * Returns the List of Man-objects.
	 * @return - List of Man-objects
	 */
	public ArrayList<Man> getMen() {
		return men;
	}
	
	
	/**
	 * Returns the List of Bomb-objects.
	 * @return - List of Bomb-objects
	 */
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}
}
