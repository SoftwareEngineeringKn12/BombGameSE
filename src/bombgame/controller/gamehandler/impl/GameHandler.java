package bombgame.controller.gamehandler.impl;

import java.util.ArrayList;
import java.util.List;
import bombgame.controller.PlayerTUI;
import bombgame.controller.ai.ManAI;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;

/**
 * Port class!
 * 
 * @author jeganslo
 *
 */
public final class GameHandler implements IGameHandler {

	/**
	 * Range in which player is spawned
	 */
	private static final int SPAWN_RANGE = 7;
	
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
	 * GameUpdater which updates all GameObjects -> Man, Bombs, ...
	 */
	private GameUpdater updater;
	
	/**
	 * GameCalculator which calculates all logical things (movement ...).
	 */
	private GameCalculator calc;
	
	/**
	 * Used to get a string representing the whole game.
	 */
	private GameToString gameString;
	
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
		this.updater = new GameUpdater(this);
		this.calc = new GameCalculator(this);
		this.gameString = new GameToString(this);
			
		calc.initializeField(width, height);
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
	 * (for testing purposes (JUnit))
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
		ais = new ManAI[0];
		
		this.updater = new GameUpdater(this);
		this.calc = new GameCalculator(this);
		this.gameString = new GameToString(this);
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
			
			List<Explosion> exp = calc.calculateExplosion((Explosion) obj);
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
	protected void removeExplosionList(List<Explosion> list) {
		
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
	 * Sets the GameObject 2D array.
	 * @param field
	 */
	protected void setField(GameObject[][] field) {
		
		this.field = new GameObject[field.length][field[0].length];
		
		for(int i = 0; i < field.length; i++) {
			System.arraycopy(field[i], 0, this.field[i], 0, field[i].length);
		}
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
	 * Returns the human player object.
	 * @return - Human player object
	 */
	public PlayerTUI getPlayer() {
		return player;
	}
	
	/**
	 * Returns the List of AIs.
	 * @return - List of AIs
	 */
	public ManAI[] getAIs() {
		return ais;
	}
	
	/**
	 * Returns the GameUpdater object.
	 * @return - GameUpdater object
	 */
	protected GameUpdater getUpdater() {
		return updater;
	}
	
	/**
	 * Returns the GameCalculator object.
	 * @return - GameCalculator object
	 */
	protected GameCalculator getCalculator() {
		return calc;
	}
	
	/**
	 * Returns the List of Explosion-objects in which the specified Explosion-oject is included.
	 * If the specified object is not found the Method returns null.
	 * @param exp - Explosion-object to be found
	 * @return - ArrayList of Explosion-objects
	 */
	protected List<Explosion> getExplosion(Explosion exp){
		for(List<Explosion> el : explosions) {
			for(Explosion e : el) {
				if(e==exp) {
					return el;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Calls GameToString to convert the game into a String
	 * for console output.
	 */
	@Override
	public String toString() {
		return gameString.toString();
	}
}
