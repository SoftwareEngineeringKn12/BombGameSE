package bombgame.application;

import bombgame.tui.TextUI;

public final class App {
	
	private App() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TextUI tui = new TextUI();
		
		// LOOP
		while(tui.update()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

}
