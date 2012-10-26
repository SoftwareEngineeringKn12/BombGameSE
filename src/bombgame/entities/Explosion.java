package bombgame.entities;

/**
 * This class determines the range of Explosions. These are placed by Man-objects.
 * @author JeGa, Rookfighter
 *
 */
public final class Explosion extends GameObject {
	
	
	//public static final int NO_DIR = 0;
	
	//public static final int UP = 1;
	
	//public static final int DOWN = 2;
	
	//public static final int RIGHT = 3;
	
	//public static final int LEFT = 4;
	
	/**
	 * spreading range of the explosion
	 */
	public static final int RANGE = 3;
	
	private int spread;
	
	//private int direction;
	
	/**
	 * Creates an Explosion-object with the specified coordinates and a range of 3.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public Explosion(int x, int y) {
		super(x, y);
		spread = 0;
	}
	
	public Explosion(int x, int y, int spread) {
		super(x,y);
		this.spread = spread;
	}
	
	public int getSpread() {
		return spread;
	}

}
