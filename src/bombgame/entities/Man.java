package bombgame.entities;

public final class Man extends GameObject {

	public static final int NO_DIR = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int LEFT = 4;
	
	private int direction;
	
	public Man(int x, int y) {
		super(x, y);
		direction = 0;
	}
	
	public Bomb placeBomb() {
		return new Bomb(getX(), getY());
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int dir) {
		direction = dir;
	}
	
}
