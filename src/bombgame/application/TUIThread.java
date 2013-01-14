package bombgame.application;

import java.util.Scanner;

import bombgame.ui.TextUI;
import bombgame.ui.UserInterface;

/**
 * Creates a Thread for the TUI.
 * @author Rookfighter
 *
 */
public class TUIThread implements Runnable{

	
	/**
	 * Scanner for TUI
	 */
	private Scanner in;
	
	/**
	 * TUI
	 */
	private UserInterface tui;
	
	
	/**
	 * Creates a Thread with for the given TUI.
	 * @param tui
	 */
	public TUIThread(TextUI tui) {
		this.tui = tui;
		in = new Scanner(System.in);
	}
	
	/**
	 * Starts the TUI.
	 */
	@Override
	public void run() {
		// LOOP
		boolean cont = true;
		while(cont) {
			cont = tui.update(in.next());
		}
		in.close();
	}

}
