package bombgame.ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;

import bombgame.entities.impl.Bomb;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;
import bombgame.ui.TextUI;
import junit.framework.TestCase;

public final class TextUITest extends TestCase {

	private TextUI tui1;
	private TextUI tui2;
	
	
	public void setUp() {
		tui2 = new TextUI(new GameObject[10][10]);
	}
	
	/*public void testPrintField() {
		PrintStream original = System.out;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		String separator = System.getProperty("line.separator");
		
		tui2.printField();
		assertEquals(tui2.createTUI() + separator, os.toString());
		
		tui2.getGameHandler().addObject(new Bomb(1,2));
		tui2.getGameHandler().addObject(new Explosion(3,5));
		tui2.getGameHandler().addObject(new Man(2,1));
		tui2.getGameHandler().addObject(new Wall(5,6));
		
		os = new ByteArrayOutputStream();
		ps = new PrintStream(os);
		System.setOut(ps);
		
		tui2.printField();
		assertEquals(tui2.createTUI() + separator, os.toString());
		
		System.setOut(original);
	}
	
	public void testPrintAllPlayers() {
		PrintStream original = System.out;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		String separator = System.getProperty("line.separator");
		
		tui1.printAllPlayers();
		assertEquals("Players:" + separator + tui1.getPlayer().toString() + separator,os.toString());
		
		System.setOut(original);
		
	}
	
	public void testPrintBombs() {
		PrintStream original = System.out;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		String separator = System.getProperty("line.separator");
		
		StringBuilder result = new StringBuilder("Bombs:");
		result.append(separator);
		
		tui2.getGameHandler().addObject(new Bomb(2,3));
		
		for(Bomb b : tui2.getGameHandler().getBombs()) {
			result.append(b.toString()).append(separator);
		}
		
		tui2.printBombs();
		
		assertEquals(result.toString(),os.toString());
		
		System.setOut(original);
	}
	
	public void testGetPlayer() {
		assertNotNull(tui1.getPlayer());
	}
	
	public void testSetPlayer() {
		tui2.setPlayer(new Man(3,4));
		assertNotNull(tui2.getPlayer());
	}
	public void testGetGameHandler() {
		assertNotNull(tui1.getGameHandler());
	}
	
	public void testUpdate() {
		
		InputStream stdin = System.in;
		ByteArrayInputStream myin = new ByteArrayInputStream("j".getBytes());
		System.setIn(myin);
		assertTrue(tui1.update());
		System.setIn(stdin);
		assertFalse(tui2.update());
	}*/
	
}
