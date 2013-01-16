package bombgame.controller.gamehandler.impl;

import java.util.ArrayList;

import bombgame.entities.IExplosion;
import bombgame.entities.impl.Bomb;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.Field;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;
import junit.framework.TestCase;

public final class GameCalculatorTest extends TestCase {

	private GameCalculator gc1;
	private GameHandler gh1;
	private GameCalculator gc2;
	private GameHandler gh2;
	
	public void setUp() {
		gh1 = new GameHandler(new Field(new GameObject[3][3]));
		gc1 = new GameCalculator(gh1);
		
		gh2 = new GameHandler(new Field(new GameObject[10][10]));
		gc2 = new GameCalculator(gh2);
		
		
	}
	
	public void testMoveMan() {
		
		Man man = new Man(0,1);
		
		gh1.addObject(man);
		gh1.addObject(new Wall(0,0));
		gh1.addObject(new Wall(0,2));
		
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.NO_DIR);
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.LEFT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.RIGHT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.UP);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.DOWN);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.DOWN);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.UP);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.RIGHT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 2);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.RIGHT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 2);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.LEFT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		Wall w = new Wall(0,1);
		gh1.addObject(w);
		
		man.setDirection(Man.LEFT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 1);
		
		gh1.removeObject(w);
		
		man.setDirection(Man.LEFT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		gh1.addObject(new Wall(1,1));
		
		man.setDirection(Man.RIGHT);
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(-1);
		gc1.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		
		//#############
		man = new Man(1,1);
		gh2.addObject(man);
		gh2.addObject(new Man(0,1));
		gh2.addObject(new Man(1,0));
		gh2.addObject(new Man(1,2));
		gh2.addObject(new Man(2,1));
		
		
		man.setDirection(Man.LEFT);
		gc2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.UP);
		gc2.moveMan(man);
		man.setDirection(Man.RIGHT);
		gc2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 0);
		
		man.setDirection(Man.RIGHT);
		gc2.moveMan(man);
		man.setDirection(Man.DOWN);
		gc2.moveMan(man);
		assertEquals(man.getX(), 2);
		assertEquals(man.getY(), 1);
		
		man.setDirection(Man.DOWN);
		gc2.moveMan(man);
		man.setDirection(Man.LEFT);
		gc2.moveMan(man);
		assertEquals(man.getX(), 1);
		assertEquals(man.getY(), 2);
		
		man.setDirection(Man.LEFT);
		gc2.moveMan(man);
		man.setDirection(Man.UP);
		gc2.moveMan(man);
		assertEquals(man.getX(), 0);
		assertEquals(man.getY(), 1);
	}
	
	// Calc expl
	
	public void testNextExplosion() {
		gh2.addObject(new Wall(3,4));
		ArrayList<IExplosion> al = new ArrayList<IExplosion>();
		assertFalse(gc2.nextExplosion(-1, 0, true, al));
		assertFalse(gc2.nextExplosion(15, 0, true, al));
		assertFalse(gc2.nextExplosion(-0, -1, true, al));
		assertFalse(gc2.nextExplosion(0, 15, true, al));
		
		assertTrue(gc2.nextExplosion(0,0,true,al));
		assertTrue(gc2.nextExplosion(0,9,true,al));
		assertTrue(gc2.nextExplosion(9,0,true,al));
		assertTrue(gc2.nextExplosion(9,9,true,al));
		
		assertFalse(gc2.nextExplosion(0,0,false,al));
		
		gh2.addObject(new Wall(1,1));
		assertFalse(gc2.nextExplosion(1,1,true,al));
		
		gh2.addObject(new Bomb(2,2));
		assertTrue(gc2.nextExplosion(2,2,true,al));
	}
	
	public void testCalculateExplosion(){
		
		assertNotNull(gc2.calculateExplosion(new Explosion(2,2)));
		
	}
	
	public void testInitializeField() {
		gc1.initializeField(4, 4);
	}
	
}
