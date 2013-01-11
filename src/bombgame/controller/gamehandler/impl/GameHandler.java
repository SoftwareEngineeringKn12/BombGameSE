package bombgame.controller.gamehandler.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import bombgame.controller.ai.IManAI;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.controller.player.IPlayer;
import bombgame.entities.IBomb;
import bombgame.entities.IExplosion;
import bombgame.entities.IField;
import bombgame.entities.IGameObject;
import bombgame.entities.IMan;
import bombgame.entities.IWall;
import bombgame.entities.impl.Field;

/**
 * Port class!
 * 
 * @author jeganslo
 *
 */
public final class GameHandler extends Observable implements IGameHandler {

	private IField field;
	
	/**
	 * Human player.
	 */
	private IPlayer player;
	
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
	public GameHandler() {
		this(DEFWIDTH, DEFHEIGHT);
	}	
	
	/**
	 * Creates a new GameHandler including a field of width x height elements with randomly
	 * generated environment.
	 * @param width - width of the new field
	 * @param height - height of the new field
	 * @param player - player that controls a man
	 */
	public GameHandler(final int width, final int height) {
		this.updater = new GameUpdater(this);
		this.calc = new GameCalculator(this);
		this.gameString = new GameToString(this);
		
		this.calc.initializeField(width, height);
		
		this.ais = new LinkedList<IManAI>();
	}
	
	/**
	 * Creates a new GameHandler with the given field.
	 * (for testing purposes (JUnit))
	 * @param f - field
	 */
	public GameHandler(final IField f) {
		field = f;
		this.updater = new GameUpdater(this);
		this.calc = new GameCalculator(this);
		this.gameString = new GameToString(this);
		
		ais = new LinkedList<IManAI>();
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
			field.getBombs().add((IBomb) obj);
		}
		
		if(obj instanceof IExplosion) {
			
			List<IExplosion> exp = calc.calculateExplosion((IExplosion) obj);
			addExplosionList(exp);
			return;
		}
		
		field.getField()[obj.getX()][obj.getY()] = obj;
	}
	
	/**
	 * Removes the given GameObject from the field. If the Object is neither on the field nor in one of the Lists,
	 * this method does nothing.
	 * @param obj - GameObject that should be removed
	 */
	public void removeObject(IGameObject obj) {
		if (obj instanceof IMan) {
			field.getMen().remove((IMan) obj);
		}
		
		if (obj instanceof IBomb) {
			field.getBombs().remove((IBomb) obj);
		}
		if(obj instanceof IExplosion) {
			
			List<IExplosion> list = getExplosion((IExplosion) obj);
			removeExplosionList(list);
			return;
		}
		
		if(field.getField()[obj.getX()][obj.getY()] == obj) {
			field.getField()[obj.getX()][obj.getY()] = null;
		}
	}
	
	/**
	 * Adds the specified List of Explosion-objects to the field.
	 * @param list - ArrayList of Explosion-objects.
	 */
	protected void addExplosionList(List<IExplosion> list) {
		
		field.getExplosionList().add(list);
		
		for(IExplosion e : list) {
			field.getField()[e.getX()][e.getY()] = e;
		}
	}
	
	/**
	 * Removes the specified List of Explosion-object from the field.
	 * @param list - ArrayList of Explosion-objects.
	 */
	protected void removeExplosionList(List<IExplosion> list) {
		
		for(IExplosion e : list) {
			field.getField()[e.getX()][e.getY()] = null;
		}
		
		field.getExplosionList().remove(list);
	}
	
	/**
	 * Calculates a new next by position for the specified Man-object if the current position
	 * is already used.
	 * @param m - spawning Man-object
	 */
	protected void spawnMan(IMan m) {
		if(field.getField()[m.getX()][m.getY()] == null) {
			field.getMen().add(m);
			return;
		}
		// Or equal
		int biggerValue; 
		if (field.getWidth() > field.getHeight()) {
			// Width
			biggerValue = field.getWidth();
		} else {
			// Height
			biggerValue = field.getHeight();
		}
		
		// Run through the whole field to search a spawn point
		// from the initial spawn point
		for(int i = 1; i <= biggerValue; i++) {
			if(m.getX() + i < field.getWidth() && !(field.getField()[m.getX() + i][m.getY()] instanceof IWall)) {
				m.setPos(m.getX() + i, m.getY());
				break;
			}
			if(m.getX() - i >= 0 && !(field.getField()[m.getX() - i][m.getY()] instanceof IWall)) {
				m.setPos(m.getX() - i, m.getY());
				break;
			}
			if(m.getY() + i < field.getHeight() && !(field.getField()[m.getX()][m.getY() + i] instanceof IWall)) {
				m.setPos(m.getX(), m.getY() + i);
				break;
			}
			if(m.getY() - i >= 0 && !(field.getField()[m.getX()][m.getY() - i] instanceof IWall)) {
				m.setPos(m.getX(), m.getY() - i);
				break;
			}
		}
		field.getMen().add(m);
	}
	
	/**
	 * Sets the GameObject 2D array.
	 * @param field
	 */
	protected void setField(IGameObject[][] field) {
		this.field = new Field(field);
		
	}
	
	/**
	 * 
	 */
	public void setPlayer(final IPlayer player) {
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
	 * Returns the human player object.
	 * @return - Human player object
	 */
	public IPlayer getPlayer() {
		return player;
	}
	
	/**
	 * Returns the List of AIs.
	 * @return - List of AIs
	 */
	public List<IManAI> getAIs() {
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
	 * Returns the field.
	 * @return - field
	 */
	@Override
	public IField getField() {
		return field;
	}
	
	/**
	 * Returns the List of Explosion-objects in which the specified Explosion-oject is included.
	 * If the specified object is not found the Method returns null.
	 * @param exp - Explosion-object to be found
	 * @return - ArrayList of Explosion-objects
	 */
	protected List<IExplosion> getExplosion(IExplosion exp){
		for(List<IExplosion> el : field.getExplosionList()) {
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
