package bombgame.entities.impl;

import bombgame.entities.IBomb;

/**
 * This class represents a Bomb that has a delay until it explodes and then creates an Explosion-object.
 * @author JeGa, Roofighter
 *
 */
public final class Bomb extends GameObject implements IBomb {
	
	/**
	 * Delay until bomb explodes.
	 */
	private static final int delay = 5;
	
	/**
	 * Timer for the bomb.
	 * Bomb explodes at timer = 0
	 */
	private int timer = delay;

	/**
	 * Creates a Bomb-object with the specified coordinates.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public Bomb(int x, int y) {
		super(x, y);
	}
	
	
	/**
	 * Returns am Bomb-object with the same coordinates as the Bomb.
	 * @return - new Explosion-object
	 */
	public Explosion explode() {
		return new Explosion(getX(), getY());
	}
	
	
	/**
	 * Returns explosion-delay of the Bomb-object
	 * @return delay value
	 */
	public int getDelay() {
		return delay;
	}
	
	/**
	 * returns actual timer value.
	 * @return timer value
	 */
	public int getTimer() {
		return timer;
	}
	
	/**
	 * Decrements the timer variable and 'explodes'
	 * when the value is 0.
	 */
	public Explosion decrementTimer() {
		timer--;
		if (timer == 0) {
			return explode();
		}
		return null;
	}
	
	/**
	 * Returns the status of the bomb object.
	 * - x and y coordinates
	 * - timer value
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("-> Bomb: ");
		str.append("[" + getX() + "]");
		str.append(" [" + getY() + "]");
		str.append(" Timer: " + timer);
		
		return str.toString();
	}

}
