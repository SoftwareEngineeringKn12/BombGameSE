package bombgame.entities;

import bombgame.entities.Explosion;
import junit.framework.TestCase;

public final class ExplosionTest extends TestCase {
	Explosion ex;
	
	public void setUp() {
		ex = new Explosion(1, 1);
	}
	
	public void testGetRange() {
		assertEquals(3, ex.getRange());
	}
}
