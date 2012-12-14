package bombgame.controller.gamehandler.impl;

import bombgame.controller.PlayerTUI;
import bombgame.controller.ai.ManAI;
import bombgame.entities.impl.Bomb;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import junit.framework.TestCase;

public final class GameToStringTest extends TestCase {

	private GameHandler gh1;
	private GameToString gts;
	
	public void setUp() {
		gh1 = new GameHandler(new GameObject[10][10]);
		gts = new GameToString(gh1);
	}
	
	public void testExplosionToString() {
		gh1.addObject(new Explosion(0,0));
		gh1.addObject(new Explosion(9,9));
		String s1 = gh1.explosionListToString();
		String s2 = "->Explosion: { [0] [0], [1] [0], [0] [1], [2] [0], [0] [2], [3] [0], [0] [3] }\n->Explosion: { [9] [9], [8] [9], [9] [8], [7] [9], [9] [7], [6] [9], [9] [6] }\n";
		assertEquals(s1,s2);
	}
	
	public void testToString() {
		gh1.addObject(new Explosion(0,0));
		gh1.addObject(new Explosion(9,9));
		
		Man man = new Man(2,2);
		gh1.addObject(man);
		PlayerTUI player = new PlayerTUI(man);
		gh1.setPlayer(player);
		
		Man man2 = new Man(3,3);
		gh1.addObject(man2);
		ManAI ai = new ManAI(man2,gh1);
		gh1.addAI(ai);
		
		gh1.addObject(new Bomb(4,3));
		
		StringBuilder sb = new StringBuilder("Players:\n");
		sb.append("-> Player: [2] [2] Direction: ").append(Man.NO_DIR).append("\n");
		sb.append("AI:\n");
		sb.append("Bombs:\n");
		sb.append("Explosions:\n->Explosion: { [0] [0], [1] [0], [0] [1], [2] [0], [0] [2], [3] [0], [0] [3] }\n->Explosion: { [9] [9], [8] [9], [9] [8], [7] [9], [9] [7], [6] [9], [9] [6] }\n");
		gts.toString();
	}
	
}
