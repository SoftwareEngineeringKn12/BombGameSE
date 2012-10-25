package bombgame.entities;

import bombgame.entities.Bomb;
import bombgame.entities.Man;
import junit.framework.TestCase;

public final class ManTest extends TestCase {

	Man man;
	
	public void setUp() {
		man = new Man(2, 2);
	}
	
	public void testPlaceBomb() {
		Bomb bomb = man.placeBomb();
		assertEquals(2, bomb.getX());
		assertEquals(2, bomb.getY());
	}
	
	public void testGetDirection() {
		assertEquals(0, man.getDirection());
	}
	
	public void testSetDirection() {
		man.setDirection(1);
		assertEquals(1, man.getDirection());
		
	}
}
