package bombgame.controller;

import java.util.ArrayList;

import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;


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
	private static final int FIELDWIDTH = 10;
	private static final int FIELDHEIGHT = 10;
	
	
	/**
	 * Creates a new GameHandler including a field of 10x10 elements with randomly
	 * generated environment.
	 */
	public GameHandler() {
		field =  MapGenerator.generateTestMap(FIELDWIDTH, FIELDHEIGHT);
		men = new ArrayList<Man>();
		bombs = new ArrayList<Bomb>();
	}
	
	
	/**
	 * Creates a new GameHandler including a field of widthxheight elements with randomly
	 * generated environment.
	 * @param width - width of the new field
	 * @param height - height of the new field
	 */
	public GameHandler(final int width, final int height) {
		field = MapGenerator.generateTestMap(width, height);
		men = new ArrayList<Man>();
		bombs = new ArrayList<Bomb>();
	}
	
	/**
	 * Creates a new GameHandler with the given field.
	 * (for testing purposes)
	 * @param f - field
	 */
	public GameHandler(final GameObject f[][]) {
		field = f;
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
	
	
	/**
	 * This method tries to move the specified Man-object to the direction given by man.getDirection(). This is
	 * only possible if the aimed coordinate is not already used by a Wall-object or is out of the range of the field.
	 * @param man - Man-object that should move
	 */
	public void moveMan( final Man man) {
		
		switch(man.getDirection()) {
		
		case Man.NO_DIR:
			break;
			
		case Man.UP:
			if( man.getY() != 0 && !(field[man.getX()][man.getY() - 1] instanceof Wall) ) {
				
				field[man.getX()][man.getY()] = null;
				man.setPos(man.getX(), man.getY() - 1);
				field[man.getX()][man.getY()] = man;
				
			}
			break;
			
		case Man.DOWN:
			if( man.getY() != (field[0].length - 1) && !(field[man.getX()][man.getY() + 1] instanceof Wall) ) {
				
				field[man.getX()][man.getY()] = null;
				man.setPos(man.getX(), man.getY() + 1);
				field[man.getX()][man.getY()] = man;
				
			}
			break;
			
		case Man.LEFT:
			if( man.getX() != 0 && !(field[man.getX() - 1][man.getY()] instanceof Wall) ) {
				
				field[man.getX()][man.getY()] = null;
				man.setPos(man.getX() - 1, man.getY());
				field[man.getX()][man.getY()] = man;
				
			}
			break;
			
		case Man.RIGHT:
			if( man.getX() != (field.length - 1) && !(field[man.getX() + 1][man.getY()] instanceof Wall) ) {
				
				field[man.getX()][man.getY()] = null;
				man.setPos(man.getX() + 1, man.getY());
				field[man.getX()][man.getY()] = man;
				
			}
			break;
		}
		
		
		
	}
	
	public void calculateExplosion(final Explosion explosion) {
		
		if(explosion.getSpread() != 0) {
			return;
		}
		
		int x;
		int y;
		for(int i = 1; i <= Explosion.RANGE; i++) {
				
			//right
			x = explosion.getX() + i;
			y = explosion.getY();
			if( x < field[1].length && !(field[x][y] instanceof Wall)) {
				addObject(new Explosion(x, y, i));
			}
			
			//left
			x = explosion.getX() - i;
			y = explosion.getY();
			addObject(new Explosion(explosion.getX() - i, explosion.getY(), i));
			if( x < field[1].length && !(field[x][y] instanceof Wall)) {
				addObject(new Explosion(x, y, i));
			}
			
			//down
			x = explosion.getX();
			y = explosion.getY() + i;
			addObject(new Explosion(explosion.getX(), explosion.getY() + i, i));
			if( x < field[1].length && !(field[x][y] instanceof Wall)) {
				addObject(new Explosion(x, y, i));
			}
			
			//up
			x = explosion.getX();
			y = explosion.getY() - i;
			addObject(new Explosion(explosion.getX(), explosion.getY() - i, i));
			if( x < field[1].length && !(field[x][y] instanceof Wall)) {
				addObject(new Explosion(x, y, i));
			}
		}
		
	}
	
	public void moveAll() {
		for (Man man : men) {
			moveMan(man);
		}
	}
	
	public void placeBombs() {
		for (Man man : men) {
			if (man.getPlaceBomb()) {
				addObject(man.placeBomb());
			}
		}
	}
	
	public void updateBombs() {
		for (Bomb bomb : bombs) {
			Explosion expl = bomb.decrementTimer();
			if (expl != null) {
				addObject(expl);
			}
		}
	}
}
