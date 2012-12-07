package bombgame.controller.ai;

import junit.framework.TestCase;
import bombgame.controller.GameHandler;
import bombgame.entities.GameObject;
import bombgame.entities.impl.Bomb;

public class BombCostCalculatorTest extends TestCase {
	
	private GameHandler gh;
	private BombCostCalculator bcc;
	
	public void setUp() {
		gh = new GameHandler(new GameObject[10][10]);
		bcc = new BombCostCalculator(gh);
	}
	
	public void testCalcExtraCost() {
		gh.addObject(new Bomb(0,0));
		assertEquals(BombCostCalculator.BOMB_COST, bcc.calcExtraCost(new Position(1,0)));
		assertEquals(0, bcc.calcExtraCost(new Position(0,9)));
		assertEquals(0, bcc.calcExtraCost(new Position(9,9)));
	}

}
