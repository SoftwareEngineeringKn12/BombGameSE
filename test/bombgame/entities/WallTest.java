package bombgame.entities;

import junit.framework.TestCase;

public final class WallTest extends TestCase {

	private Wall w;
	
	public void setUp() {
		w = new Wall(2, 2);
	}
	
	public void testGetX() {
		assertEquals(2,w.getX());
	}
	
	public void testGetY() {
		assertEquals(2, w.getY());
	}
	
}
