package bombgame.controller.gamehandler.impl;

import bombgame.controller.Player;
import bombgame.controller.ai.impl.ManAI;
import bombgame.entities.impl.Bomb;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;
import junit.framework.TestCase;

public final class GameToStringTest extends TestCase {

	private GameHandler gh1;
	private GameToString gts;
	
	public void setUp() {
		gh1 = new GameHandler(new GameObject[10][10]);
		gts = new GameToString(gh1);
	}
	
	public void testToString() {
		gh1.addObject(new Explosion(0,0));
		gh1.addObject(new Explosion(9,9));
		
		Player player = new Player(new Man(2,2));
		gh1.setPlayer(player);
		
		Man man2 = new Man(3,3);
		ManAI ai = new ManAI(man2,gh1);
		gh1.addAI(ai);
		
		gh1.addObject(new Wall(4,0));
		
		gh1.addObject(new Bomb(4,3));
		
		StringBuilder sb = new StringBuilder("Players:\n");
		sb.append("-> Player: [2] [2] Direction: ").append(Man.NO_DIR).append("\n");
		sb.append("AI:\n-> AI: [3] [3] T: NULL F: NULL Turn: 0 Direction: ");
		sb.append(man2.getDirection()).append("\n");
		sb.append("Bombs:\n-> Bomb: [4] [3] Timer: 5\n");
		sb.append("Explosions:\n->Explosion: { [0] [0], [1] [0], [0] [1], [2] [0], [0] [2], [3] [0], [0] [3] }\n");
		sb.append("->Explosion: { [9] [9], [8] [9], [9] [8], [7] [9], [9] [7], [6] [9], [9] [6] }\n");
		sb.append("\n X  X  X  X  #  -  -  -  -  - \n X  -  -  -  -  -  -  -  -  - \n X  -  M  -  -  -  -  -  -  - ");
		sb.append("\n X  -  -  M  O  -  -  -  -  - \n -  -  -  -  -  -  -  -  -  - \n -  -  -  -  -  -  -  -  -  - \n");
		sb.append(" -  -  -  -  -  -  -  -  -  X \n -  -  -  -  -  -  -  -  -  X \n -  -  -  -  -  -  -  -  -  X \n");
		sb.append(" -  -  -  -  -  -  X  X  X  X \n");
		assertEquals(sb.toString(),gts.toString());
	}
	
}
