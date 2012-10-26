package bombgame.controller;

import bombgame.entities.Man;
import junit.framework.TestCase;

public final class PlayerTUITest extends TestCase {

	PlayerTUI player;
	
	public void setUp() {
		player = new PlayerTUI(new Man(0, 0));
	}
	
	public void testMove() {
		player.move();
		// User-input - then prints the man status
	}
	
	/*
	public void testToString() {
		StringBuilder str = new StringBuilder();
		
		str.append("-> Player: ");
		str.append("[" + man.getX() + "]");
		str.append(" [" + man.getY() + "]");
		str.append(" Direction: " + man.getDirection());
		
		assertEquals();
	}*/
	
}
