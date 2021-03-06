package bombgame.controller.ai.impl;


import bombgame.controller.ai.impl.HistoryTargetFinder;
import bombgame.controller.ai.impl.ManAI;
import bombgame.controller.ai.impl.Position;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.Field;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;
import junit.framework.TestCase;

public class HistoryTargetFinderTest extends TestCase{
	
	private HistoryTargetFinder htf;
	private ManAI ai;
	private GameHandler gh;
	
	public void setUp() {
		gh = new GameHandler(new Field(new GameObject[10][10]));
		ai = new ManAI(new Man(0,0), gh.getField());
		htf = new HistoryTargetFinder(ai);
	}
	
	public void testSearchTarget() {
		ai.setFocusedEnemy(new Man(5,5));
		assertEquals(new Position(5,5), htf.searchTarget());
		
	}
	
	public void testCalculateTarget() {
		
		htf.calculateTarget(new Position(1,1), Man.RIGHT);
		assertEquals(htf.getTarget(), new Position(2,1));
		
		htf.calculateTarget(new Position(1,1), Man.LEFT);
		assertEquals(htf.getTarget(), new Position(0,1));
		
		htf.calculateTarget(new Position(1,1), Man.UP);
		assertEquals(htf.getTarget(), new Position(1,0));
		
		htf.calculateTarget(new Position(1,1), Man.DOWN);
		assertEquals(htf.getTarget(), new Position(1,2));
		
	}
	
	public void testFindTarget() {
		
		htf.findTarget(new Position(0,0), -1, 0);
		assertEquals(htf.getTarget(), new Position(0,0));
		
		htf.findTarget(new Position(0,0), 0, -1);
		assertEquals(htf.getTarget(), new Position(0,0));
		
		htf.findTarget(new Position(9,9), 1, 0);
		assertEquals(htf.getTarget(), new Position(9,9));
		
		htf.findTarget(new Position(9,9), 0, 1);
		assertEquals(htf.getTarget(), new Position(9,9));
		
		
		gh.addObject(new Wall(1,0));
		htf.findTarget(new Position(0,0), 1, 0);
		assertEquals(htf.getTarget(), new Position(0,0));
		
		gh.addObject(new Wall(1,2));
		ai = new ManAI(new Man(0,1), gh.getField());
		htf = new HistoryTargetFinder(ai);
		
		htf.findTarget(new Position(0,1),1,0);
		assertEquals(htf.getTarget(), new Position(2,1));
	}
	
	public void testGetMaxIndex() {
		int[] ia = {1,2,3,4,5,6,7,8,9,0};
		assertEquals(htf.getMaxIndex(ia), 8);
	}
	
	public void testSum() {
		int[] ia = {1,2,3,4,5,6,7,8,9,0};
		assertEquals(htf.sum(ia), 45);
	}

}
