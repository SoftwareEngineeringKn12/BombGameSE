package bombgame.controller.ai.impl;

import junit.framework.TestCase;
import bombgame.controller.ai.ClosedListSelector;
import bombgame.controller.ai.impl.AISelector;
import bombgame.controller.ai.impl.Position;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Wall;

public class ClosedListSelectorTest extends TestCase{

	private GameHandler gh;
	private ClosedListSelector cls;
	
	public void setUp() {
		gh = new GameHandler(new GameObject[10][10]);
		cls = new AISelector(gh);
	}
	
	public void testMoveToClosedList() {
		gh.addObject(new Wall(0,0));
		assertTrue(cls.moveToClosedList(new Position(0,0), 0));
		assertFalse(cls.moveToClosedList(new Position(9,9), 3));
	}
}
