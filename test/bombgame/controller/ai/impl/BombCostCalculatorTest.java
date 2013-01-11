package bombgame.controller.ai.impl;

import junit.framework.TestCase;
import bombgame.controller.ai.impl.BombCostCalculator;
import bombgame.controller.ai.impl.Position;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.Bomb;
import bombgame.entities.impl.Field;
import bombgame.entities.impl.GameObject;

public class BombCostCalculatorTest extends TestCase {
	
	private GameHandler gh;
	private BombCostCalculator bcc;
	
	public void setUp() {
		gh = new GameHandler(new Field(new GameObject[10][10]));
		bcc = new BombCostCalculator(gh.getField());
	}
	
	public void testCalcExtraCost() {
		gh.addObject(new Bomb(0,0));
		assertEquals(BombCostCalculator.BOMB_COST, bcc.calcExtraCost(new Position(1,0)));
		assertEquals(0, bcc.calcExtraCost(new Position(0,9)));
		assertEquals(0, bcc.calcExtraCost(new Position(9,9)));
	}

}
