package bombgame.controller.gamehandler.impl;

import bombgame.entities.impl.Explosion;
import junit.framework.TestCase;

public final class GameToStringTest extends TestCase {

	public void setUp() {
		
	}
	
	public void testExplosionToString() {
		gh1.addObject(new Explosion(0,0));
		gh1.addObject(new Explosion(9,9));
		String s1 = gh1.explosionListToString();
		String s2 = "->Explosion: { [0] [0], [1] [0], [0] [1], [2] [0], [0] [2], [3] [0], [0] [3] }\n->Explosion: { [9] [9], [8] [9], [9] [8], [7] [9], [9] [7], [6] [9], [9] [6] }\n";
		assertEquals(s1,s2);
	}
	
}
