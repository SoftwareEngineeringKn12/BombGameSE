package bombgame.controller.ai.impl;

import bombgame.controller.ai.impl.AISelector;
import bombgame.controller.ai.impl.Position;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.Field;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Wall;
import junit.framework.TestCase;

public class AISelectorTest extends TestCase{

	
	private GameHandler gh;
	private AISelector as;
	
	public void setUp() {
		gh = new GameHandler(new Field(new GameObject[10][10]));
		as = new AISelector(gh.getField());
	}
	
	public void testMoveToClosedList() {
		gh.addObject(new Wall(0,0));
		assertTrue(as.moveToClosedList(new Position(0,0), 0));
		gh.addObject(new Explosion(2,2));
		assertTrue(as.moveToClosedList(new Position(1,2), 1));
		assertFalse(as.moveToClosedList(new Position(1,2), 15));
		assertFalse(as.moveToClosedList(new Position(9,9), 3));
	}
}
