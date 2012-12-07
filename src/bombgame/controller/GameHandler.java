package bombgame.controller;

import java.util.ArrayList;
import java.util.List;
import bombgame.controller.MazeGen.Cell;
import bombgame.controller.ai.ManAI;
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
	 * Range in which player is spawned
	 */
	private static final int SPAWN_RANGE = 7;
	
	/**
	 * explosion direction up
	 */
	private static final int EXP_UP = 3;
	
	/**
	 * explosion direction down
	 */
	private static final int EXP_DOWN = 2;
	
	/**
	 * explosion direction left
	 */
	private static final int EXP_LEFT = 1;
	
	/**
	 * explosion direction right
	 */
	private static final int EXP_RIGHT = 0;
	
	/**
	 * matrix holding all GameObjects in the game.
	 * The array indices specify the position on the field.
	 */
	private GameObject field[][];
	
	/**
	 * List holding all Man-objects in the game.
	 */
	private List<Man> men;
	
	/**
	 * List holding all Bomb-objects in the game.
	 */
	private List<Bomb> bombs;
	
	/**
	 * list of explosions
	 */
	private List<List<Explosion>> explosions;
	
	/**
	 * Human player.
	 */
	private PlayerTUI player;
	
	/**
	 * All ais
	 */
	private ManAI[] ais;
	
	/**
	 * default field width
	 */
	private static final int DEFWIDTH = 30;
	
	/**
	 * default field height
	 */
	private static final int DEFHEIGHT = 20;
	
	
	/**
	 * Creates a new GameHandler including a field of FIELDWIDTH x FIELDHEIGHT elements with randomly
	 * generated environment.
	 */
	public GameHandler() {
		this(DEFWIDTH, DEFHEIGHT);
	}
	
	
	/**
	 * Creates a new GameHandler including a field of width x height elements with randomly
	 * generated environment.
	 * @param width - width of the new field
	 * @param height - height of the new field
	 */
	public GameHandler(final int width, final int height) {
		initializeField(width, height);
		men = new ArrayList<Man>();
		bombs = new ArrayList<Bomb>();
		explosions = new ArrayList<List<Explosion>>();
		ais = new ManAI[0];
		
		Man man = new Man(0,0);
		addObject(man);
		player = new PlayerTUI(man);
		
	}
	
	
	/**
	 * Creates a new GameHandler with the given field.
	 * (for testing purposes)
	 * @param f - field
	 */
	public GameHandler(final GameObject f[][]) {
		field = new GameObject[f.length][f[0].length];
		for( int i = 0; i < f.length; i++) {
			for( int j = 0; j < f.length; j++) {
				field[i][j] = f[i][j];
			}
		}
		men = new ArrayList<Man>();
		bombs = new ArrayList<Bomb>();
		explosions = new ArrayList<List<Explosion>>();
	}
	
	
	/**
	 * Adds the given GameObject to the field and the specified List, if Position is not already in use except Bomb-objects.
	 * They can also be placed onto another GameObject (e.g. a Man-object).
	 * @param obj - GameObject that should be added to the field
	 */
	public void addObject(GameObject obj) {
		
		if (obj instanceof Man) {
			spawnMan((Man) obj);
		}
		
		if (obj instanceof Bomb) {
			bombs.add((Bomb) obj);
		}
		
		if(obj instanceof Explosion) {
			
			List<Explosion> exp = calculateExplosion((Explosion) obj);
			addExplosionList(exp);
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
			
			List<Explosion> list = getExplosion((Explosion) obj);
			removeExplosionList(list);
			return;
		}
		
		if(field[obj.getX()][obj.getY()] == obj) {
			field[obj.getX()][obj.getY()] = null;
		}
	}
	
	/**
	 * Adds the specified List of Explosion-objects to the field.
	 * @param list - ArrayList of Explosion-objects.
	 */
	private void addExplosionList(List<Explosion> list) {
		
		explosions.add(list);
		
		for(Explosion e : list) {
			field[e.getX()][e.getY()] = e;
		}
	}
	
	/**
	 * Removes the specified List of Explosion-object from the field.
	 * @param list - ArrayList of Explosion-objects.
	 */
	private void removeExplosionList(List<Explosion> list) {
		
		for(Explosion e : list) {
			field[e.getX()][e.getY()] = null;
		}
		
		explosions.remove(list);
	}
	
	/**
	 * Calculates a new next by position for the specified Man-object if the current position
	 * is already used.
	 * @param m - spawning Man-object
	 */
	private void spawnMan(Man m) {
		if(field[m.getX()][m.getY()] == null) {
			men.add(m);
			return;
		}
		
		
		for(int i = 1; i < SPAWN_RANGE; i++ ) {
			if(m.getX() + i < field.length && !(field[m.getX() + i][m.getY()] instanceof Wall)) {
				m.setPos(m.getX() + i, m.getY());
				break;
			}
			if(m.getX() - i >= 0 && !(field[m.getX() - i][m.getY()] instanceof Wall)) {
				m.setPos(m.getX() - i, m.getY());
				break;
			}
			if(m.getY() + i < field[0].length && !(field[m.getX()][m.getY() + i] instanceof Wall)) {
				m.setPos(m.getX(), m.getY() + i);
				break;
			}
			if(m.getY() - i >= 0 && !(field[m.getX()][m.getY() - i] instanceof Wall)) {
				m.setPos(m.getX(), m.getY() - i);
				break;
			}
		}
		men.add(m);
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
	public List<Man> getMen() {
		return men;
	}
	
	
	/**
	 * Returns the List of Bomb-objects.
	 * @return - List of Bomb-objects
	 */
	public List<Bomb> getBombs() {
		return bombs;
	}
	
	
	/**
	 * Returns the List of Lists of Explosions.
	 * @return - List of Lists of Explosions
	 */
	public List<List<Explosion>> getExplosionList() {
		return explosions;
	}
	
	
	/**
	 * Returns the List of Explosion-objects in which the specified Explosion-oject is included.
	 * If the specified object is not found the Method returns null.
	 * @param exp - Explosion-object to be found
	 * @return - ArrayList of Explosion-objects
	 */
	public List<Explosion> getExplosion(Explosion exp){
		for(List<Explosion> el : explosions) {
			for(Explosion e : el) {
				if(e==exp) {
					return el;
				}
			}
		}
		
		return null;
		
	}
	
	
	//!! DONE
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
					//only replace with null if man is the user of the field
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
					//only replace with null if man is the user of the field
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
					//only replace with null if man is the user of the field
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
					//only replace with null if man is the user of the field
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
	
	
	//!! DONE
	/**
	 * Calculates the spread of the specified Explosion-object.
	 * @param explosion - source of explosion
	 * @return - ArrayList of all Explosions included in the spread
	 */
	protected List<Explosion> calculateExplosion(final Explosion explosion) {
		
		List<Explosion> list = new ArrayList<Explosion>();
		list.add(explosion);
		
		boolean free[] = {true, true, true, true};
		for(int i = 1; i <= Explosion.RANGE; i++) {
				
			//right
			free[EXP_RIGHT] = nextExplosion(explosion.getX() + i, explosion.getY(),free[EXP_RIGHT], list);
			
			//left
			free[EXP_LEFT] = nextExplosion(explosion.getX() - i, explosion.getY(),free[EXP_LEFT], list);
			
			//down
			free[EXP_DOWN] = nextExplosion(explosion.getX(), explosion.getY() + i,free[EXP_DOWN], list);
			
			//up
			free[EXP_UP] = nextExplosion(explosion.getX(), explosion.getY() - i,free[EXP_UP], list);
		}
		
		return list;
		
	}
	
	
	//!! DONE
	/**
	 * Creates a new Explosion-object with the specified coordinates, adds that object to the specified ArrayList
	 * and returns true, if the coordinate is not blocked by a Wall-object and free is true.
	 * Else it returns false.
	 * @param x - x-coordinate of the new explosion
	 * @param y - y coordinate of the new explosion
	 * @param free - determines if the explosion is sill spreading
	 * @param list - list to which explosion will be added
	 * @return - returns true if Explosion-object was successfully created
	 */
	protected boolean nextExplosion(int x, int y, boolean free, List<Explosion> list) {
		if( free && x < field.length && x >= 0 && y < field[0].length && y >= 0 && !(field[x][y] instanceof Wall)) {
			
			list.add(new Explosion(x, y));
			
			return true;
			
		} else {
			
			return false;
			
		}
	}
	
	
	/**
	 * Moves all Man-objects kept in men.
	 */
	public void moveAll() {
		//new ArrayList is needed to remove a man during iteration
		List<Man> m = new ArrayList<Man>(men);
		for (Man man : m) {
			if(checkHit(man)) {
				removeObject(man);
			} else {
				moveMan(man);
			}
		}
	}
	
	
	/**
	 * Checks if any Man-object from men wants to place a bomb. If so a Bomb-object is added.
	 */
	public void placeBombs() {
		for (Man man : men) {
			if (man.getPlaceBomb()) {
				addObject(man.placeBomb());
			}
		}
	}
	
	
	/**
	 * Decrements the timer of the Bomb-object kept in bombs. If they explode the Bomb-object is removed
	 * and an Explosion-object is added.
	 */
	public void updateBombs() {
		//new ArrayList is needed to remove a bomb during iteration
		List<Bomb> bs = new ArrayList<Bomb>(bombs);
		for (Bomb bomb : bs) {
			Explosion expl = bomb.decrementTimer();
			if (expl != null) {
				removeObject(bomb);
				addObject(expl);
			}
		}
	}
	
	/**
	 * Decrements the duration of the explosions until they reach a duration of 0. Then they will be removed.
	 */
	public void updateExplosion() {
		//new ArrayList is needed to remove a Explosionlist during iteration
		List<List<Explosion>> explist = new ArrayList<List<Explosion>>(explosions); 
		for(List<Explosion> explosion : explist) {
			Explosion exp = explosion.get(0);
			exp.decrementTimer();
			
			if(exp.getTimer() <= 0) {
				removeExplosionList(explosion);
			}
		}
	}
	
	/**
	 * Checks if the specified Man-object is hit by an Explosion. Returns true if the man is on a field
	 * which is already use by an Explosion-object, else returns false.
	 * @param man - Man-object to check
	 * @return - returns true if man is hit
	 */
	private boolean checkHit(Man man) {
		return field[man.getX()][man.getY()] instanceof Explosion;
	}
	
	//!! DONE
	/**
	 * Creates with MazeGen a random generated, non-perfect maze.
	 * This maze is stored in field as the playing field.
	 * If the parameters are both 0, then the FIELDWIDTH and FIELDHEIGHT
	 * are used.
	 * @param width - width of field
	 * @param height - height of field
	 */
	private void initializeField(final int width, final int height) {
		MazeGen generator = new MazeGen(width, height);
		field = new GameObject[width][height];

		generator.genNonPerfectMaze();
		//System.out.println(generator);
		Cell[][] cellArray = generator.getMaze();
		
		int xLength = cellArray.length;
		int yLength = cellArray[0].length;
		
		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < xLength; j++) {
				if (cellArray[j][i].getWall()) {
					field[j][i] = new Wall(j, i);
				} else {
					field[j][i] = null;
				}
			}
		}
	}
	
	
	/**
	 * Returns a String-representation of the explosionlist of this GameHandler-object.
	 * @return
	 */
	public String explosionListToString() {
		StringBuilder sb = new StringBuilder();
		for(List<Explosion> list : explosions) {
			sb.append("->Explosion: { ");
			int i = 0;
			for(Explosion exp : list) {
				if(i < list.size() - 1) {
					sb.append("[").append(exp.getX()).append("] [").append(exp.getY()).append("], ");
				} else {
					sb.append("[").append(exp.getX()).append("] [").append(exp.getY()).append("] }\n");
				}
				i++;
			}
		}
		
		return sb.toString();
	}
	
	public PlayerTUI getPlayer() {
		return player;
	}
	
	public ManAI[] getAis() {
		return ais;
	}
	
	public void updateAll() {
		//!! look at the order.
		//scan input false;
		//Scanner in = new Scanner(System.in);
		
		// TODO endgame
		
		for(ManAI ai : ais) {
			ai.calcNextStep();
		}
		player.move();
		
		moveAll();
		updateBombs();
		placeBombs();
		updateExplosion();
	}
}
