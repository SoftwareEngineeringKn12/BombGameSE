package bombgame;

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
	}

}
