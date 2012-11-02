package bombgame.entities;

import bombgame.entities.Explosion;
import junit.framework.TestCase;

public final class ExplosionTest extends TestCase {
	Explosion ex;
	
	public void setUp() {
		ex = new Explosion(1, 1);
	}
	
	
	public void testGetDuration() {
		assertEquals(ex.getDuration(),4);
	}
	
	public void testDecrementDuration() {
		ex.decrementDuration();
		assertEquals(ex.getDuration(), 3);
	}
}
