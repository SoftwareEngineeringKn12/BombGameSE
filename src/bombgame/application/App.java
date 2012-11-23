package bombgame.application;

import bombgame.ui.TextUI;
import bombgame.ui.UserInterface;

public final class App {
	
	private App() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserInterface ui = new TextUI();
		boolean cont = true;
		// LOOP
		while(cont) {
			cont = ui.update();
		}
	}

}
