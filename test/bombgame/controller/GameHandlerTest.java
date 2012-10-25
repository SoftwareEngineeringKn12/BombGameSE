package bombgame.controller;

import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;
import junit.framework.TestCase;

public final class GameHandlerTest extends TestCase {

	GameHandler gh1;
	GameHandler gh2;
	
	public void setUp() {
		gh1 = new GameHandler();
		gh2 = new GameHandler(2, 3);
	}
	
	public void testGetField() {
		assertNotNull(gh1.getField());
	}
	
	public void testGetMen() {
		assertNotNull(gh1.getMen());
	}
	
	public void testGetBombs() {
		assertNotNull(gh1.getBombs());
	}
	
	public void testAddObject() {
		GameObject o1 = new Wall(3,4);
		gh1.addObject(o1);
		assertSame(o1, gh1.getField()[3][4]);
		
		GameObject o2 = new Man(2,7);
		gh1.addObject(o2);
		assertSame(o2, gh1.getField()[2][7]);
		
		GameObject o3 = new Bomb(1,8);
		gh1.addObject(o3);
		assertSame(o3, gh1.getField()[1][8]);
		
		//because (3/4) already used by o1, don't add 
		GameObject o4 = new Explosion(3,4);
		gh1.addObject(o4);
		assertNotSame(o4, gh1.getField()[3][4]);
		
		GameObject o5 = new Explosion(2,4);
		gh1.addObject(o5);
		assertSame(o5, gh1.getField()[2][4]);
	}
	
	public void testRemoveObject() {
		GameObject o1 = new Wall(3,4);
		gh1.addObject(o1);
		gh1.removeObject(o1);
		assertNull(gh1.getField()[3][4]);
		
		GameObject o2 = new Man(6,3);
		gh1.addObject(o2);
		gh1.removeObject(o2);
		assertNull(gh1.getField()[6][3]);
		
		GameObject o3 = new Bomb(2,7);
		gh1.addObject(o3);
		gh1.removeObject(o3);
		assertNull(gh1.getField()[2][7]);
		
		GameObject o4 = new Explosion(1,5);

		gh1.addObject(o4);
		gh1.removeObject(o4);
		assertNull(gh1.getField()[1][5]);
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
	}
}
