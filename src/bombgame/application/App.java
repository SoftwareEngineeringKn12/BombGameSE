package bombgame.application;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.Player;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.Man;
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
		UserInterface ui = new TextUI(new GameHandler(new Player(new Man(0,0))));
		
		// LOOP
		boolean cont = true;
		while(cont) {
			cont = ui.update(in.next());
		}
		in.close();
	}

}
