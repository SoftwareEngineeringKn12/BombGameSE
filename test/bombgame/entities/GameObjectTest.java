package bombgame.entities;

import bombgame.entities.GameObject;
import bombgame.entities.Wall;
import junit.framework.TestCase;

public class GameObjectTest extends TestCase {
	
	GameObject go;
	
	public void setUp() {
		go = new Wall(2, 2);
	}
	
	public void testGetX() {
		assertEquals(2,go.getX());
	}
	
	public void testGetY() {
		assertEquals(2, go.getY());
	}
	
	public void testSetPos() {
		go.setPos(1, 1);
		assertEquals(1,go.getX());
		assertEquals(1,go.getY());
	}

}
