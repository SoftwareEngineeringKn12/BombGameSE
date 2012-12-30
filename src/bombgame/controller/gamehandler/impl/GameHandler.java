package bombgame.controller.gamehandler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import bombgame.controller.Player;
import bombgame.controller.ai.IManAI;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.controller.gamehandler.IGameHandler2D;
import bombgame.entities.IBomb;
import bombgame.entities.IExplosion;
import bombgame.entities.IGameObject;
import bombgame.entities.IMan;
import bombgame.entities.IWall;
import bombgame.entities.impl.GameObject;

/**
 * Port class!
 * 
 * @author jeganslo
 *
 */
public final class GameHandler extends Observable implements IGameHandler2D, IGameHandler {

	/**
	 * Range in which player is spawned
	 */
	private static final int SPAWN_RANGE = 7;
	
	/**
	 * matrix holding all GameObjects in the game.
	 * The array indices specify the position on the field.
	 */
	private IGameObject field[][];
	
	/**
	 * List holding all Man-objects in the game.
	 */
	private List<IMan> men;
	
	/**
	 * List holding all Bomb-objects in the game.
	 */
	private List<IBomb> bombs;
	
	/**
	 * list of explosions
	 */
	private List<List<IExplosion>> explosions;
	
	/**
	 * Human player.
	 */
	private Player player;
	
	/**
	 * All ais
	 */
	private List<IManAI> ais;
	
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
	 * @param player - player that controls a man
	 */
	public GameHandler(Player player) {
		this(DEFWIDTH, DEFHEIGHT, player);
	}	
	
	/**
	 * Creates a new GameHandler including a field of width x height elements with randomly
	 * generated environment.
	 * @param width - width of the new field
	 * @param height - height of the new field
	 * @param player - player that controls a man
	 */
	public GameHandler(final int width, final int height, Player player) {
		this.updater = new GameUpdater(this);
		this.calc = new GameCalculator(this);
		this.gameString = new GameToString(this);
		
		this.calc.initializeField(width, height);
		
		this.men = new ArrayList<IMan>();
		this.bombs = new ArrayList<IBomb>();
		this.explosions = new ArrayList<List<IExplosion>>();
		this.ais = new ArrayList<IManAI>();
		
		setPlayer(player);
	}
	
	/**
	 * Creates a new GameHandler with the given field.
	 * (for testing purposes (JUnit))
	 * @param f - field
	 */
	public GameHandler(final IGameObject f[][]) {
		field = new GameObject[f.length][f[0].length];
		for( int i = 0; i < f.length; i++) {
			for( int j = 0; j < f.length; j++) {
				field[i][j] = f[i][j];
			}
		}		
		this.updater = new GameUpdater(this);
		this.calc = new GameCalculator(this);
		this.gameString = new GameToString(this);
		
		men = new ArrayList<IMan>();
		bombs = new ArrayList<IBomb>();
		explosions = new ArrayList<List<IExplosion>>();
		ais = new ArrayList<IManAI>();
	}
	
	/**
	 * Adds the given GameObject to the field and the specified List, if Position is not already in use except Bomb-objects.
	 * They can also be placed onto another GameObject (e.g. a Man-object).
	 * @param obj - GameObject that should be added to the field
	 */
	public void addObject(IGameObject obj) {
		
		if (obj instanceof IMan) {
			spawnMan((IMan) obj);
		}
		
		if (obj instanceof IBomb) {
			bombs.add((IBomb) obj);
		}
		
		if(obj instanceof IExplosion) {
			
			List<IExplosion> exp = calc.calculateExplosion((IExplosion) obj);
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
	public void removeObject(IGameObject obj) {
		if (obj instanceof IMan) {
			men.remove((IMan) obj);
		}
		
		if (obj instanceof IBomb) {
			bombs.remove((IBomb) obj);
		}
		if(obj instanceof IExplosion) {
			
			List<IExplosion> list = getExplosion((IExplosion) obj);
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
	protected void addExplosionList(List<IExplosion> list) {
		
		explosions.add(list);
		
		for(IExplosion e : list) {
			field[e.getX()][e.getY()] = e;
		}
	}
	
	/**
	 * Removes the specified List of Explosion-object from the field.
	 * @param list - ArrayList of Explosion-objects.
	 */
	protected void removeExplosionList(List<IExplosion> list) {
		
		for(IExplosion e : list) {
			field[e.getX()][e.getY()] = null;
		}
		
		explosions.remove(list);
	}
	
	/**
	 * Calculates a new next by position for the specified Man-object if the current position
	 * is already used.
	 * @param m - spawning Man-object
	 */
	protected void spawnMan(IMan m) {
		if(field[m.getX()][m.getY()] == null) {
			men.add(m);
			return;
		}
				
		for(int i = 1; i < SPAWN_RANGE; i++ ) {
			if(m.getX() + i < field.length && !(field[m.getX() + i][m.getY()] instanceof IWall)) {
				m.setPos(m.getX() + i, m.getY());
				break;
			}
			if(m.getX() - i >= 0 && !(field[m.getX() - i][m.getY()] instanceof IWall)) {
				m.setPos(m.getX() - i, m.getY());
				break;
			}
			if(m.getY() + i < field[0].length && !(field[m.getX()][m.getY() + i] instanceof IWall)) {
				m.setPos(m.getX(), m.getY() + i);
				break;
			}
			if(m.getY() - i >= 0 && !(field[m.getX()][m.getY() - i] instanceof IWall)) {
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
	protected void setField(IGameObject[][] field) {
		
		this.field = new GameObject[field.length][field[0].length];
		
		for(int i = 0; i < field.length; i++) {
			System.arraycopy(field[i], 0, this.field[i], 0, field[i].length);
		}
	}
	
	/**
	 * 
	 */
	public void setPlayer(final Player player) {
		this.player = player;
		addObject(player.getMan());
	}
	
	/**
	 * 
	 */
	public void addAI(IManAI ai) {
		ais.add(ai);
		addObject(ai.getMan());
	}
	
	/**
	 * Returns the matrix of the field.
	 * @return - matrix of the field
	 */
	public IGameObject[][] getField() {
		return field;
	}
		
	/**
	 * Returns the List of Man-objects.
	 * @return - List of Man-objects
	 */
	public List<IMan> getMen() {
		return men;
	}
		
	/**
	 * Returns the List of Bomb-objects.
	 * @return - List of Bomb-objects
	 */
	public List<IBomb> getBombs() {
		return bombs;
	}
	
	/**
	 * Returns the human player object.
	 * @return - Human player object
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the List of Lists of Explosions.
	 * @return - List of Lists of Explosions
	 */
	protected List<List<IExplosion>> getExplosionList() {
		return explosions;
	}
	
	/**
	 * Returns the List of AIs.
	 * @return - List of AIs
	 */
	protected List<IManAI> getAIs() {
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
	protected List<IExplosion> getExplosion(IExplosion exp){
		for(List<IExplosion> el : explosions) {
			for(IExplosion e : el) {
				if(e==exp) {
					return el;
				}
			}
		}
		
		return null;
	}

	/**
	 * Updates the whole game.
	 */
	public void updateAll() {
		updater.updateAIs();
		updater.updateMen();
		updater.updateBombs();
		updater.placeBombs();
		updater.updateExplosion();
		setChanged();
		notifyObservers(toString());
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
