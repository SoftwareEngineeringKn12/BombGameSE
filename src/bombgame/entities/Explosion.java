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
	
	public static final int DURATION = 4;
	
	/**
	 * time the explosion stays in the field
	 */
	private  int timer = DURATION;
	
	/**
	 * Creates an Explosion-object with the specified coordinates and a range of 3.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	public Explosion(int x, int y) {
		super(x, y);
	}
	
	
	/**
	 * Returns the current remaining duration of the Explosion-object.
	 * @return - remaining duration
	 */
	public int getTimer() {
		return timer;
	}
	
	
	/**
	 * Decrements the duration of the Explosion-object
	 */
	public void decrementTimer() {
		timer--;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Explosion: ");
		sb.append("[").append(getX()).append("] [").append(getY()).append("]");
		return sb.toString();
	}

}
