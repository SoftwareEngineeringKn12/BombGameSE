package bombgame.entities;

public final class Bomb extends GameObject {
	
	private int delay = 5;

	public Bomb(int x, int y) {
		super(x, y);
	}
	
	public Explosion explode() {
		return new Explosion(getX(), getY());
	}
	
	public int getDelay() {
		return delay;
	}

}
