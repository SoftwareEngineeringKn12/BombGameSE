package bombgame.controller.ai;

import bombgame.controller.GameHandler;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;
import junit.framework.TestCase;

public class ManAITest extends TestCase {
	
	private ManAI ai;
	private Man man;
	private GameHandler gh;
	
	public void setUp() {
		gh = new GameHandler(new GameObject[10][10]);
		man = new Man(0,0);
		gh.addObject(man);
		ai = new ManAI(man,gh);
		
	}
	
	public void testCalcNextStep() {
		ai.calcNextStep();
		assertNull(ai.target);
		assertNull(ai.focusedEnemy);
		
		gh.addObject(new Man(9,9));		
		ai.turns = 0;
		ai.calcNextStep();
		assertNotNull(ai.target);
		assertNotNull(ai.focusedEnemy);
		
		ai.target = new Position(man.getX(), man.getY());
		ai.calcNextStep();
		assertNull(ai.target);
		assertFalse(ai.placebomb);
		assertEquals(man.getDirection(), Man.NO_DIR);
		
		ai.path.clear();
		ai.calcNextStep();
		assertFalse(ai.path.isEmpty());
		
		ai.calcNextStep();
		
		ai.path.clear();
		ai.mode = ManAI.FLEE_MODE;
		ai.target = null;
		ai.calcNextStep();
		assertTrue(ai.path.isEmpty());
		assertNull(ai.target);
		
		ai.mode = 123;
		ai.calcNextStep();
		assertTrue(ai.path.isEmpty());
		assertNull(ai.target);
		
		gh.addObject(new Man(3,5));
		ai = new ManAI(man, gh);
		gh.addObject(new Wall(man.getX() + 1, man.getY()));
		gh.addObject(new Wall(man.getX() , man.getY() + 1));
		ai.calcNextStep();
		assertTrue(ai.path.isEmpty());
		assertNotNull(ai.target);
		
		ai.path.push(new Position(man.getX() + 4, man.getY()));
		ai.calcNextStep();
	}

}
