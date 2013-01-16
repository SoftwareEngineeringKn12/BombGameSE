package bombgame.entities.impl;

import bombgame.entities.IGameObject;
import junit.framework.TestCase;

public class FieldTest extends TestCase{
	
	private Field field;
	private Field field2;
	
	public void setUp() {
		field = new Field(5,10);
		field2 = new Field(new IGameObject[10][5]);
	}
	
	public void testGetHeight() {
		assertEquals(field.getHeight(), 10);
	}
	
	public void testGetWidth() {
		assertEquals(field.getWidth(), 5);
	}
	
	public void testGetField() {
		assertNotNull(field.getField());
	}
	
	public void testGetMen() {
		assertNotNull(field2.getMen());
	}
	
	public void testGetBombs() {
		assertNotNull(field.getBombs());
	}
	
	public void testGetExplosionList() {
		assertNotNull(field.getExplosionList());
	}

}
