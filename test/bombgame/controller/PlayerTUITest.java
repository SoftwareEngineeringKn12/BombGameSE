package bombgame.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import bombgame.entities.impl.Man;
import junit.framework.TestCase;

public final class PlayerTUITest extends TestCase {

	Player player;
	Man man;
	
	public void setUp() {
		man = new Man(0, 0);
		player = new Player(man);
	}
	
	public void testMove() {
		InputStream in = System.in;
		
		System.setIn(new ByteArrayInputStream("w".getBytes()));
		player.move();
		assertEquals(player.getMan().getDirection(), Man.UP);
		
		System.setIn(new ByteArrayInputStream("s".getBytes()));
		player.move();
		assertEquals(player.getMan().getDirection(), Man.DOWN);
		
		System.setIn(new ByteArrayInputStream("d".getBytes()));
		player.move();
		assertEquals(player.getMan().getDirection(), Man.RIGHT);
		
		System.setIn(new ByteArrayInputStream("a".getBytes()));
		player.move();
		assertEquals(player.getMan().getDirection(), Man.LEFT);
		
		System.setIn(new ByteArrayInputStream("j".getBytes()));
		player.move();
		assertEquals(player.getMan().getPlaceBomb(), true);
		
		System.setIn(new ByteArrayInputStream("x".getBytes()));
		player.move();
		assertEquals(player.getMan().getDirection(), Man.NO_DIR);
		
		System.setIn(in);
	}
	
	public void testGetMan() {
		assertEquals(man, player.getMan());
	}
	
	public void testToString() {
		StringBuilder str = new StringBuilder();
		
		str.append("-> Player: ");
		str.append("[" + man.getX() + "]");
		str.append(" [" + man.getY() + "]");
		str.append(" Direction: " + man.getDirection());
		
		assertEquals(str.toString(), player.toString());
	}
}
