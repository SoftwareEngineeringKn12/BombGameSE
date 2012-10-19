package bombgame.entities;

public final class Man extends GameObject {

	
	
	
	public Man(int x, int y) {
		super(x, y);
	}
	
	public Bomb placeBomb() {
		return new Bomb(getX(), getY());
	}

	
	
}
