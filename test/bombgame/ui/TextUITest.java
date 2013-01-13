package bombgame.ui;

import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.player.impl.Player;
import bombgame.entities.IGameObject;
import bombgame.entities.impl.Field;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.ui.TextUI;
import junit.framework.TestCase;

public final class TextUITest extends TestCase {

	private TextUI tui1;
	private TextUI tui2;
	private GameHandler gh;
	
	public void setUp() {
		// Configure log4j
		PropertyConfigurator.configureAndWatch("log4j.properties");
				
		gh = new GameHandler();
		gh.setPlayer(new Player(new Man(0, 0)));
		tui1 = new TextUI(gh);
		
		IGameObject field[][] = new GameObject[2][2];
		GameHandler gh2 = new GameHandler(new Field(field));
		gh2.setPlayer(new Player(new Man(0, 0)));
		gh2.addObject(new Man(1,1));
		tui2 = new TextUI(gh2);
	}
	
	public void testTextUI() {
		assertEquals(tui1.getGameHandler(), gh);
	}
	
	public void testUpdate() {
		
		tui2.update("d");
		tui2.update("s");
		
		assertEquals(tui2.getGameHandler().getPlayer().getMan().getX(), 1);
		assertEquals(tui2.getGameHandler().getPlayer().getMan().getY(), 1);
		try{ tui1.update(gh, new Man(0,0));
		} catch (IllegalArgumentException e) {}
		
		
		GameHandler gh2 = new GameHandler(new Field(new GameObject[2][2]));
		TextUI tui3 = new TextUI(gh2);
		tui3.update("j");
		
	}
}
