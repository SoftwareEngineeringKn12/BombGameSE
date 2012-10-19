package bombgame.entities;

public final class Explosion extends GameObject {

	private final int range;
	
	public Explosion(int x, int y) {
		super(x, y);
		range = 3;
	}
	
	public int getRange() {
		return range;
	}

}
