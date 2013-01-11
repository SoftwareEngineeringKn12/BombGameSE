package bombgame.controller.ai.impl;

import junit.framework.TestCase;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;

public class FleeTargetFinderTest extends TestCase{

	private FleeTargetFinder ftf;
	private ManAI ai;
	private GameHandler gh;
	
	public void setUp() {
		gh = new GameHandler(new GameObject[15][15]);
		ai = new ManAI(new Man(5,5), gh);
		ftf = new FleeTargetFinder(ai);
	}
	
	public void testSearchTarget() {
		
		ai.setFocusedEnemy(new Man(0,0));
		assertEquals(ftf.searchTarget(), new Position(10,10));
		
		ai.setFocusedEnemy(new Man(5,5));
		assertEquals(ftf.searchTarget(), new Position(10,10));
	}
	
	public void testSearch() {
		gh.addObject(new Wall(10,10));
		gh.addObject(new Wall(9,10));
		assertEquals(ftf.search(1,1), new Position(9,9));
		
		ManAI ai2 = new ManAI(new Man(10,10) ,gh);
		ftf = new FleeTargetFinder(ai2);
		assertEquals(ftf.search(1,1), new Position(14,14));
	}
	
	public void testIsInField() {
		assertFalse(ftf.isInField(-1, 10, gh));
		assertFalse(ftf.isInField(10, -1, gh));
		assertFalse(ftf.isInField(10, 17, gh));
	}
}
