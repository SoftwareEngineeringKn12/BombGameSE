package bombgame.entities.impl;

import bombgame.entities.GameObject;


/**
 * This class represents the only moving GameObject in this game. Objects created with 
 * this class are further obtained by a player or the AI.
 * @author JeGa, Rookfighter
 *
 */
public final class Man extends GameObject {

	/**
	 * no direction (= standing still)
	 */
	public static final int NO_DIR = 0;
	
	/**
	 * direction up
	 */
	public static final int UP = 1;
	
	/**
	 * direction down
	 */
	public static final int DOWN = 2;
	
	/**
	 * direction right
	 */
	public static final int RIGHT = 3;
	
	/**
	 * direction left
	 */
	public static final int LEFT = 4;
	
	/**
	 * specifies the direction the man will move in the next turn
	 */
	private int direction;
	
	/**
	 * Indicates if the man places a bomb in his turn.
	 */
	private boolean placeBomb = false;
	
	
	/**
	 * Creates a Man-object with the specified coordinates.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public Man(int x, int y) {
		super(x, y);
		direction = 0;
	}
	
	
	/**
	 * Returns a new Bomb-object with the current coordinates of the Man-object.
	 * @return - created Bomb-object
	 */
	public Bomb placeBomb() {
		placeBomb = false;
		return new Bomb(getX(), getY());
	}
	
	
	/**
	 * Returns the current direction of the Man-object. This indicates the Movementdirection the Man will take
	 * next turn.
	 * @return - current direction
	 */
	public int getDirection() {
		return direction;
	}
	
	
	/**
	 * Sets the direction of the Man-object to the specified value.
	 * @param dir - value to set direction
	 */
	public void setDirection(int dir) {		
		direction = dir;
	}
	
	/**
	 * 
	 * @return status of placeBomb (if the man wants to place a bomb).
	 */
	public boolean getPlaceBomb() {
		return placeBomb;
	}
	
	/**
	 * Sets placeBomb variable.
	 */
	public void setPlaceBomb(boolean placeBomb) {
		this.placeBomb = placeBomb;
	}
}
