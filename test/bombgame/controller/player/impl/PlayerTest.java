package bombgame.controller.player.impl;

import bombgame.controller.player.impl.Player;
import bombgame.entities.impl.Man;
import junit.framework.TestCase;

public final class PlayerTest extends TestCase {

	Player player;
	Man man;
	
	public void setUp() {
		man = new Man(0, 0);
		player = new Player(man);
	}
	
	public void testMove() {
		/*InputStream in = System.in;
		System.setIn(new ByteArrayInputStream("w".getBytes()));
		System.setIn(in);*/
		
		player.move('w');
		assertEquals(player.getMan().getDirection(), Man.UP);
		
		player.move('s');
		assertEquals(player.getMan().getDirection(), Man.DOWN);
		
		player.move('d');
		assertEquals(player.getMan().getDirection(), Man.RIGHT);
		
		player.move('a');
		assertEquals(player.getMan().getDirection(), Man.LEFT);
		
		player.move('j');
		assertEquals(player.getMan().getPlaceBomb(), true);
		
		player.move('x');
		assertEquals(player.getMan().getDirection(), Man.NO_DIR);
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
