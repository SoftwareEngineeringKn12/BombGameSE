package bombgame.entities;

import bombgame.entities.Explosion;
import junit.framework.TestCase;

public final class ExplosionTest extends TestCase {
	Explosion ex;
	
	public void setUp() {
		ex = new Explosion(1, 1);
	}
	
	
	public void testGetDuration() {
		assertEquals(ex.getTimer(),4);
	}
	
	public void testDecrementDuration() {
		ex.decrementTimer();
		assertEquals(ex.getTimer(), 3);
	}
}
