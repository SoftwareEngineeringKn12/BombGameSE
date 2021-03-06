package bombgame.entities.impl;

import bombgame.entities.IWall;

/**
 * This class represents a Wall-element. Its only purpose is to be a solid barrier.
 * @author JeGa, Rookfighter
 *
 */
public final class Wall extends GameObject implements IWall {
	
	/**
	 * Creates a Wall-object with the specified Coordinates.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public Wall(int x, int y) {
		super(x, y);
	}

}
