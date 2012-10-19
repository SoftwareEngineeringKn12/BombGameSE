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

}
