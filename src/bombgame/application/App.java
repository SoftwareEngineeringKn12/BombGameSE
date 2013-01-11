package bombgame.application;

import java.util.Scanner;
import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.config.GameHandlerConfiguration;
import bombgame.ui.TextUI;
import bombgame.ui.UserInterface;

/**
 * 
 * @author Rookfighter
 *
 */
public final class App {
	
	/**
	 * Scanner for TUI
	 */
	private Scanner in;
	
	/**
	 * TUI
	 */
	private UserInterface tui;
	
	/**
	 * Configures log4j, creates Scanner and TUI.
	 */
	public App() {
		// Configure log4j
		PropertyConfigurator.configureAndWatch("log4j.properties");
		
		// Scanner for TUI
		in = new Scanner(System.in);
		
		// Create TUI
		GameHandlerConfiguration config = new GameHandlerConfiguration(30, 20, 3);
		tui = new TextUI(config.createGameHandler());
	}
	
	/**
	 * Starts the TUI loop
	 */
	public void start() {
		// LOOP
		boolean cont = true;
		while(cont) {
			cont = tui.update(in.next());
		}
		in.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		App application = new App();
		application.start();
	}

}
