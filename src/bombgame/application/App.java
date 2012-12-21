package bombgame.application;

import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.PlayerTUI;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.Man;
import bombgame.ui.TextUI;
import bombgame.ui.UserInterface;

public final class App {
	
	private App() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//PropertyConfigurator.configureAndWatch("log4j.properties");
		UserInterface ui = new TextUI(new GameHandler(new PlayerTUI(new Man(0,0))));
		boolean cont = true;
		// LOOP
		while(cont) {
			cont = ui.update();
		}
	}

}
