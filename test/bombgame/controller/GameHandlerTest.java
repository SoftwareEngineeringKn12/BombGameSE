package bombgame.controller;

import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;
import junit.framework.TestCase;

public final class GameHandlerTest extends TestCase {

	GameHandler gh;
	
	public void setUp() {
		gh = new GameHandler();
	}
	
	public void testGameHandlerConstructor() {
		gh = new GameHandler(10, 10);
		//!!
	}
	
	public void testGetField() {
		assertNotNull(gh.getField());
	}
	
	public void testGetMen() {
		assertNotNull(gh.getMen());
	}
	
	public void testGetBombs() {
		assertNotNull(gh.getBombs());
	}
	
	public void testAddObject() {
		GameObject o1 = new Wall(3,4);
		gh.addObject(o1);
		assertSame(o1, gh.getField()[3][4]);
		
		GameObject o2 = new Man(2,7);
		gh.addObject(o2);
		assertSame(o2, gh.getField()[2][7]);
		
		GameObject o3 = new Bomb(1,8);
		gh.addObject(o3);
		assertSame(o3, gh.getField()[1][8]);
		
		//because (3/4) already used by o1, don't add 
		GameObject o4 = new Explosion(3,4);
		gh.addObject(o4);
		assertNotSame(o4, gh.getField()[3][4]);
		
		GameObject o5 = new Explosion(2,4);
		gh.addObject(o5);
		assertSame(o5, gh.getField()[2][4]);
		
	}
	
	public void testRemoveObject() {
		GameObject o1 = new Wall(3,4);
		gh.addObject(o1);
		gh.removeObject(o1);
		assertNull(gh.getField()[3][4]);
		
		
		GameObject o2 = new Man(6,3);
		gh.addObject(o2);
		gh.removeObject(o2);
		assertNull(gh.getField()[6][3]);
		
		GameObject o3 = new Bomb(2,7);
		gh.addObject(o3);
		gh.removeObject(o3);
		assertNull(gh.getField()[2][7]);
		
		GameObject o4 = new Explosion(1,5);
		gh.addObject(o4);
		gh.removeObject(o4);
		assertNull(gh.getField()[1][5]);
		
		
	}
}
