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
	
}
