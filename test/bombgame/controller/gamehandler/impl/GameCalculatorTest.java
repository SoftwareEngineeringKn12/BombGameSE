package bombgame.controller.gamehandler.impl;

import java.util.ArrayList;

import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;
import junit.framework.TestCase;

public final class GameCalculatorTest extends TestCase {

	public void setUp() {
		
	}
	
	public void testMoveMan() {
		
		Man man = new Man(0,1);
		
		gh2.addObject(man);
		gh2.addObject(new Wall(0,0));
		gh2.addObject(new Wall(0,2));
		
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.NO_DIR);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.LEFT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.RIGHT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.DOWN);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.UP);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.RIGHT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		Wall w = new Wall(0,1);
		gh2.addObject(w);
		
		man.setDirection(Man.LEFT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		gh2.removeObject(w);
		
		man.setDirection(Man.LEFT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		gh2.addObject(new Wall(1,1));
		
		man.setDirection(Man.RIGHT);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(-1);
		gh2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		
		//#############
		man = new Man(1,1);
		gh1.addObject(man);
		gh1.addObject(new Man(0,1));
		gh1.addObject(new Man(1,0));
		gh1.addObject(new Man(1,2));
		gh1.addObject(new Man(2,1));
		
		
		man.setDirection(Man.LEFT);
		gh1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gh1.moveMan(man);
		man.setDirection(Man.RIGHT);
		gh1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.RIGHT);
		gh1.moveMan(man);
		man.setDirection(Man.DOWN);
		gh1.moveMan(man);
		assertEquals(man.getX(), 2);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gh1.moveMan(man);
		man.setDirection(Man.LEFT);
		gh1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.LEFT);
		gh1.moveMan(man);
		man.setDirection(Man.UP);
		gh1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
	}
	
	// Calc expl
	
	public void testNextExplosion() {
		gh1.addObject(new Wall(3,4));
		ArrayList<Explosion> al = new ArrayList<Explosion>();
		assertFalse(gh1.nextExplosion(-1, 0, true, al));
		assertFalse(gh1.nextExplosion(15, 0, true, al));
		assertFalse(gh1.nextExplosion(-0, -1, true, al));
		assertFalse(gh1.nextExplosion(0, 15, true, al));
	}
	
}
