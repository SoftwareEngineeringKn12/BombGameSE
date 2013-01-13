package bombgame.application;

import java.util.Scanner;

import bombgame.ui.TextUI;
import bombgame.ui.UserInterface;

public class TUIThread implements Runnable{

	
	/**
	 * Scanner for TUI
	 */
	private Scanner in;
	
	/**
	 * TUI
	 */
	private UserInterface tui;
	
	
	public TUIThread(TextUI tui) {
		this.tui = tui;
		in = new Scanner(System.in);
	}
	
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
