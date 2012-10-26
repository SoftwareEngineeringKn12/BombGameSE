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
	public static final int RANGE = 3;
	
	/**
	 * Creates an Explosion-object with the specified coordinates and a range of 3.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public Explosion(int x, int y) {
		super(x, y);
	}
	

}
