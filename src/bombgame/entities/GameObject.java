package bombgame.entities;

public abstract class GameObject {
	
	private int x;
	private int y;
	
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPos(int x, int y) {
		//if (x < 0 || y < 0)
			//throw new IndexOutOfBoundsException("No negative coordinates.");
		
		this.x = x;
		this.y = y;
	}

}
