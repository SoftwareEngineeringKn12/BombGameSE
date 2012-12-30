package bombgame.controller.ai.impl;

import bombgame.controller.ai.impl.Position;
import junit.framework.TestCase;

public class PositionTest extends TestCase{
	
	private Position pos;

	public void setUp() {
		pos = new Position(1,3);
	}
	
	public void testGetX() {
		assertEquals(pos.getX(),1);
	}
	
	public void testGetY() {
		assertEquals(pos.getY(),3);
	}
	
	public void testToString() {
		String s = "[1][3]";
		assertEquals(pos.toString(), s);
	}
	
	public void testEquals() {
		Position postmp = new Position(1,3);
		assertTrue(postmp.equals(pos));
		postmp = new Position(2,3);
		assertFalse(postmp.equals(pos));
		postmp = new Position(1,5);
		assertFalse(postmp.equals(pos));
		String s = "blas";
		assertFalse(pos.equals(s));
		
	}
	
	public void testHashCode() {
		Position pos = new Position(1,0);
		assertEquals(pos.hashCode(), 961);
	}

}
