package bombgame.entities;

public final class Bomb extends GameObject {

	public Bomb(int x, int y) {
		super(x, y);
	}
	
	public Explosion explode() {
		return new Explosion(getX(), getY());
	}

}
