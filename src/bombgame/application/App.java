package bombgame.application;

import java.util.Scanner;
import org.apache.log4j.PropertyConfigurator;
import bombgame.controller.gamehandler.GameHandlerConfiguration;
import bombgame.ui.TextUI;
import bombgame.ui.UserInterface;

/**
 * 
 * @author Rookfighter
 *
 */
public final class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Configure log4j
		PropertyConfigurator.configureAndWatch("log4j.properties");
		
		// Scanner for TUI
		Scanner in = new Scanner(System.in);
		
		// Create TUI - gets concrete Player and AI
		// Here than player, man, mazegen -> googleguice
		GameHandlerConfiguration config = new GameHandlerConfiguration(30, 20, 3);
		UserInterface ui = new TextUI(config.createGameHandler());
		
		// LOOP
		boolean cont = true;
		while(cont) {
			cont = ui.update(in.next());
		}
		in.close();
	}

}
