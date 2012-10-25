package bombgame.entities;

/**
 * This class determines the range of Explosions. These are placed by Man-objects.
 * @author JeGa, Rookfighter
 *
 */
public final class Explosion extends GameObject {
	
	/**
	 * spreading range of the explosion
	 */
	private final int range = 3;
	
	/**
	 * Creates an Explosion-object with the specified coordinates and a range of 3.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public Explosion(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Returns the range of the Explosion-object.
	 * @return
	 */
	public int getRange() {
		return range;
	}

}
