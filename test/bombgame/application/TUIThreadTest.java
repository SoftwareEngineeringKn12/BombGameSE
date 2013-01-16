package bombgame.application;

import java.io.ByteArrayInputStream;

import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.Field;
import bombgame.entities.impl.GameObject;
import bombgame.ui.TextUI;
import junit.framework.TestCase;

public class TUIThreadTest extends TestCase{

	private TUIThread tt;
	
	public void setUp() {
		PropertyConfigurator.configureAndWatch("log4j.properties");
		
		String data = "?";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		GameHandler gh = new GameHandler(new Field(new GameObject[2][2]));
		TextUI tui = new TextUI(gh);
		tt = new TUIThread(tui);
	}
	
	public void testRun() {
		tt.run();
	}
}
