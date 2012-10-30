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
	
	private ArrayList<ArrayList<Explosion>> explosions; 
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
		explosions = new ArrayList<ArrayList<Explosion>>();
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
		explosions = new ArrayList<ArrayList<Explosion>>();
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
		explosions = new ArrayList<ArrayList<Explosion>>();
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
		
		if(obj instanceof Explosion) {
			ArrayList<Explosion> exp = calculateExplosion((Explosion) obj);
			explosions.add(exp);
			for(Explosion e : exp) {
				field[e.getX()][e.getY()] = e;
			}
			return;
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
		if(obj instanceof Explosion) {
			
			ArrayList<Explosion> list = getExplosion((Explosion) obj);
			for(Explosion e : list) {
				
				field[e.getX()][e.getY()] = null;
				
			}
			
			explosions.remove(list);
			return;
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
	 * Returns the List of Lists of Explosions.
	 * @return - List of Lists of Explosions
	 */
	public ArrayList<ArrayList<Explosion>> getExplosionList() {
		return explosions;
	}
	
	/**
	 * Returns the List of Explosion-objects in which the specified Explosion-oject is included.
	 * If the specified object is not found the Method returns null.
	 * @param exp - Explosion-object to be found
	 * @return - ArrayList of Explosion-objects
	 */
	public ArrayList<Explosion> getExplosion(Explosion exp){
		for(ArrayList<Explosion> el : explosions) {
			for(Explosion e : el) {
				if(e==exp) {
					return el;
				}
			}
		}
		
		return null;
		
	}
	
	
	/**
	 * This method tries to move the specified Man-object to the direction given by man.getDirection(). This is
	 * only possible if the aimed coordinate is not already used by a Wall-object or is out of the range of the field.
	 * @param man - Man-object that should move
	 */
	protected void moveMan( final Man man) {
		
		switch(man.getDirection()) {
		
		case Man.NO_DIR:
			break;
			
		case Man.UP:
			if( man.getY() != 0 && !(field[man.getX()][man.getY() - 1] instanceof Wall) ) {
				
				if(field[man.getX()][man.getY()] == man) {
					//only replace with null if man i the user of the field
					field[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX(), man.getY() - 1);
				
				if(field[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field[man.getX()][man.getY()] = man;
				}
				
			}
			break;
			
		case Man.DOWN:
			if( man.getY() != (field[0].length - 1) && !(field[man.getX()][man.getY() + 1] instanceof Wall) ) {
				
				if(field[man.getX()][man.getY()] == man) {
					//only replace with null if man i the user of the field
					field[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX(), man.getY() + 1);
				
				if(field[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field[man.getX()][man.getY()] = man;
				}
				
			}
			break;
			
		case Man.LEFT:
			if( man.getX() != 0 && !(field[man.getX() - 1][man.getY()] instanceof Wall) ) {
				
				if(field[man.getX()][man.getY()] == man) {
					//only replace with null if man i the user of the field
					field[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX() - 1, man.getY());
				
				if(field[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field[man.getX()][man.getY()] = man;
				}
				
			}
			break;
			
		case Man.RIGHT:
			if( man.getX() != (field.length - 1) && !(field[man.getX() + 1][man.getY()] instanceof Wall) ) {
				
				if(field[man.getX()][man.getY()] == man) {
					//only replace with null if man i the user of the field
					field[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX() + 1, man.getY());
				
				if(field[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field[man.getX()][man.getY()] = man;
				}
				
			}
			break;
		}
		
		
		
	}
	
	/**
	 * Calculates the spread of the specified Explosion-object.
	 * @param explosion - source of explosion
	 * @return - ArrayList of all Explosions included in the spread
	 */
	protected ArrayList<Explosion> calculateExplosion(final Explosion explosion) {
		
		ArrayList<Explosion> list = new ArrayList<Explosion>();
		list.add(explosion);
		
		boolean free[] = {true, true, true, true};
		for(int i = 1; i <= Explosion.RANGE; i++) {
				
			//right
			free[0] = nextExplosion(explosion.getX() + i, explosion.getY(),free[0], list);
			
			//left
			free[1] = nextExplosion(explosion.getX() - i, explosion.getY(),free[1], list);
			
			//down
			free[2] = nextExplosion(explosion.getX(), explosion.getY() + i,free[2], list);
			
			//up
			free[3] = nextExplosion(explosion.getX(), explosion.getY() - i,free[3], list);
		}
		
		return list;
		
	}
	
	/**
	 * Creates a new Explosion-object with the specified coordinates, adds that object to the specified ArrayList
	 * and returns true, if the coordinate is not blocked by a Wall-object and free is true.
	 * Else it returns false.
	 * @param x - x-coordinate of the new explosion
	 * @param y - y coordinate of the new explosion
	 * @param free - determines if the explosion ist sill spreading
	 * @param list - list to which explosion will be added
	 * @return - returns true if Explosion-object was successfully created
	 */
	protected boolean nextExplosion(int x, int y, boolean free, ArrayList<Explosion> list) {
		if( free && x < field[1].length && x >= 0 && y < field.length && y >= 0 && !(field[x][y] instanceof Wall)) {
			
			list.add(new Explosion(x, y));
			
			return true;
			
		} else {
			
			return false;
			
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
		//new ArrayList is needed to remove a bomb during iteration
				ArrayList<Bomb> bs = new ArrayList<Bomb>(bombs);
		for (Bomb bomb : bs) {
			Explosion expl = bomb.decrementTimer();
			if (expl != null) {
				removeObject(bomb);
				addObject(expl);
			}
		}
	}
}
