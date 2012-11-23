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
		man = new Man(1,1);
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
		gh.addObject(new Wall(man.getX() -1 , man.getY()));
		gh.addObject(new Wall(man.getX() , man.getY() - 1));
		ai.calcNextStep();
		assertTrue(ai.path.isEmpty());
		assertNotNull(ai.target);
		
		ai.path.push(new Position(man.getX() + 4, man.getY()));
		ai.calcNextStep();
	}
	
	public void testCheckTargetReached() {
		ai.target = new Position(man.getX(),man.getY() + 1);
		assertFalse(ai.checkTargetReached());
	}
	
	public void testCalcManDirection() {
		ai.path.clear();
		ai.path.push(new Position(man.getX() - 1, man.getY()));
		ai.calcManDirection();
		assertEquals(man.getDirection(),Man.LEFT);
		
		ai.path.clear();
		ai.path.push(new Position(man.getX() , man.getY() + 1));
		ai.calcManDirection();
		assertEquals(man.getDirection(),Man.DOWN);
		
		ai.path.clear();
		ai.path.push(new Position(man.getX(), man.getY() - 1));
		ai.calcManDirection();
		assertEquals(man.getDirection(),Man.UP);
		
		ai.path.clear();
		ai.path.push(new Position(man.getX(), man.getY()));
		ai.calcManDirection();
		assertEquals(man.getDirection(),Man.NO_DIR);
		
		
		
	}
	
	public void testFocusEnemy() {
		gh.addObject(new Man(9,9));
		for(int i = 0; i < 20; i++) {
			ai.focusEnemy();
		}
		assertNotNull(ai.focusedEnemy);
		
	}
	
	public void testGetNodes() {
		assertNotNull(ai.getNodes());
	}
	
	public void testGetHandler() {
		assertNotNull(ai.getHandler());
	}
	
	public void testSetFocusedEnemy() {
		Man m = new Man (3,3);
		ai.setFocusedEnemy(m);
		assertEquals(m , ai.focusedEnemy);
	}
	
	public void testToString() {
		String s = "-> AI: [1] [1] T: NULL F: NULL Turn: 0 Direction: 0";
		assertEquals(ai.toString(), s);
		ai.target = new Position(2,2);
		ai.focusedEnemy = new Man(3,3);
		s = "-> AI: [1] [1] T: [2] [2] F: [3] [3] Turn: 0 Direction: 0";
		assertEquals(ai.toString(), s);
	}

}
