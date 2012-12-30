package bombgame.entities.impl;

import bombgame.entities.IGameObject;

/**
 * Abstract class: All Entities inherit from this class.
 * @author JeGa, Rookfighter
 * 
 */
public abstract class GameObject implements IGameObject {
	
	/**
	 * x-coordinate
	 */
	private int x;
	
	/**
	 * y-coordinate
	 */
	private int y;
	
	/**
	 * Creates a GameObject with the specified coordinates. This constructors only purpose is to 
	 * simplify things for sub-classes. These coordinates are not pixel coordinates. They describe
	 * the position in a 2-Dimensional matrix field.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the current x-coordinate.
	 * @return - x-coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the current y-coordinate.
	 * @return - y-coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the coordinates of the GameObject to the specified values. Negative values will throw an Exception.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public void setPos(int x, int y) {
		
		this.x = x;
		this.y = y;
	}

}
