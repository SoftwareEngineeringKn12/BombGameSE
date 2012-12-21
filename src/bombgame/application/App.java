package bombgame.application;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.PlayerTUI;
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
	
	private App() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PropertyConfigurator.configureAndWatch("log4j.properties");
		UserInterface ui = new TextUI(new GameHandler(new PlayerTUI(new Man(0,0))));
		boolean cont = true;
		// LOOP
		while(cont) {
			cont = ui.update(in.next());
		}
		in.close();
	}

}
