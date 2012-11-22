package bombgame.controller.ai;

import bombgame.controller.GameHandler;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import junit.framework.TestCase;

public class HistoryTargetFinderTest extends TestCase{
	
	private HistoryTargetFinder htf;
	private ManAI ai;
	private GameHandler gh;
	
	public void setUp() {
		gh = new GameHandler(new GameObject[10][10]);
		ai = new ManAI(new Man(0,0), gh);
		htf = new HistoryTargetFinder(ai);
	}
	
	public void testSearchTarget() {
		ai.setFocusedEnemy(new Man(5,5));
		assertEquals(new Position(5,5), htf.searchTarget());
		
		/*ai.addHistoryValue(Man.LEFT);
		assertEquals(new Position(4,5), htf.searchTarget());
		
		ai.addHistoryValue(Man.UP);
		ai.addHistoryValue(Man.UP);
		assertEquals(new Position(5,4), htf.searchTarget());
		
		ai.addHistoryValue(Man.DOWN);
		ai.addHistoryValue(Man.DOWN);
		ai.addHistoryValue(Man.DOWN);
		assertEquals(new Position(5,6), htf.searchTarget());
		
		ai.addHistoryValue(Man.RIGHT);
		ai.addHistoryValue(Man.RIGHT);
		ai.addHistoryValue(Man.RIGHT);
		ai.addHistoryValue(Man.RIGHT);
		assertEquals(new Position(6,5), htf.searchTarget());*/
	}

}
