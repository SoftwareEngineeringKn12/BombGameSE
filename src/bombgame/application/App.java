package bombgame.application;

import bombgame.tui.TextUI;

public final class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TextUI tui = new TextUI();
		// LOOP
		while(tui.update()) { };
	}

}
