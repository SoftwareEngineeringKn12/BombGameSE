package bombgame.controller;

import bombgame.entities.Man;
import junit.framework.TestCase;

public final class PlayerTUITest extends TestCase {

	PlayerTUI player;
	Man man;
	
	public void setUp() {
		man = new Man(0, 0);
		player = new PlayerTUI(man);
	}
	
	/*public void testMove() {
		player.move();
		// User-input - then prints the man status
	}*/
	
	public void testGetMan() {
		assertEquals(man, player.getMan());
	}
	
	public void testToString() {
		StringBuilder str = new StringBuilder();
		
		str.append("-> Player: ");
		str.append("[" + man.getX() + "]");
		str.append(" [" + man.getY() + "]");
		str.append(" Direction: " + man.getDirection());
		
		assertEquals(str.toString(), player.toString());
	}
}
