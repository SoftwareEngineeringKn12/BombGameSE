package bombgame.entities;

import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import junit.framework.TestCase;

public final class BombTest extends TestCase {
	
	Bomb bomb;
	
	public void setUp() {
		bomb = new Bomb(2, 2);
	}
	
	public void testExplode() {
		Explosion expl = bomb.explode();
		assertEquals(2, expl.getX());
		assertEquals(2, expl.getY());
	}
	
	public void testGetDelay() {
		assertEquals(5, bomb.getDelay());
	}
	
	public void testGetTimer() {
		assertEquals(bomb.getDelay(), bomb.getTimer());
	}
	
	// Changed new test needed!!
	/*
	public void testDecrementTimer() {
		// normal
		bomb.decrementTimer();
		assertEquals(bomb.getDelay()-1, bomb.getTimer());
		
		// explode
		for(int i = 0; i < bomb.getDelay(); i++) {
			bomb.decrementTimer();
		}
		
		// check explode!
	}*/
	
	public void testToString() {
		StringBuilder str = new StringBuilder();
		
		str.append("-> Bomb: ");
		str.append("[" + bomb.getX() + "]");
		str.append(" [" + bomb.getY() + "]");
		str.append(" Timer: " + bomb.getTimer());
		
		assertEquals(bomb.toString(), str.toString());
	}
}
