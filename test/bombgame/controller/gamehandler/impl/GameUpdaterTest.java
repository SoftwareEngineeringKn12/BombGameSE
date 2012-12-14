package bombgame.controller.gamehandler.impl;

import bombgame.entities.impl.Bomb;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.Man;
import junit.framework.TestCase;

public final class GameUpdaterTest extends TestCase {

	public void setUp() {
		
	}
	
	// Get handler
	
	public void testPlaceBombs() {
		Man m = new Man(0,0);
		m.setPlaceBomb(true);
		gh1.addObject(new Man(1,1));
		gh1.addObject(m);
		gh1.placeBombs();
		assertEquals(gh1.getBombs().size(),1);
		
	}
	
	public void testUpdateBombs() {
		Bomb b = new Bomb(1,1);
		gh1.addObject(b);
		while(b.getTimer() > 0) {
			gh1.updateBombs();
		}
		gh1.updateBombs();
		
		assertEquals(gh1.getBombs().size(),0);
	}
	
	public void testUpdateExplosion() {
		
		Explosion exp = new Explosion(1,1);
		gh1.addObject(exp);
		
		while(exp.getTimer() > 0) {
			gh1.updateExplosion();
		}
		
		assertEquals(gh1.getExplosionList().size(),0);
		
		
	}
	
	// Update man
	
	// Check hit
	
	// Update AIs
	
}
