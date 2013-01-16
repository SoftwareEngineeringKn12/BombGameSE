package bombgame.controller.ai.impl;

import bombgame.controller.ai.IPosition;

/**
 * This Class holds a x- and a y-coordinate and defines a Position in a 2 dimensional
 * area.
 * @author Rookfighter
 *
 */
 public final class Position implements IPosition{
	
	 
	 /**
	 * value for calculating hashCode
	 */
	private static final int HASHVAL = 31;
		
	 /**
	  * x-coordinate
	  */
	private final int x;
	
	/**
	 * y-coordinate
	 */
	private final int y;
	
	/**
	 * Creates a Position with the specified coordinates
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	 Position( int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	 /**
	  * Returns the x-coordinate.
	  * @return - x-coordinate
	  */
	 public int getX() {
		return x;
	}
	
	 /**
	  * Returns the y-coordinate.
	  * @return - y-coordinate
	  */
	 public int getY() {
		return y;
	}
	
	 /**
	  * Returns a String representation of a Position.
	  */
	@Override
	 public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(x).append("][").append(y).append("]");
		return sb.toString();
	}
	
	
	/**
	 * Returns the hashcode of the Position.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash = HASHVAL * this.x;
		hash = (hash + this.y ) * HASHVAL; 
		return hash;
	}
	
	
	/**
	 * Returns true if the Positions have the same coordinates.
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Position)) {
			return false;
		}
		Position pos = (Position) obj;
		return this.getX() == pos.getX() && this.getY() == pos.getY();
	}
}
