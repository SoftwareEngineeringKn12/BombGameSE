package bombgame.controller;

import java.util.Scanner;
import bombgame.entities.Man;

/**
 * This class represents the human player. It has one Man variable which is the
 * players figure.
 * 
 * @author jens
 * 
 */
public final class PlayerTUI {

	private Man man;

	public PlayerTUI(Man man) {
		this.man = man;
	}

	/**
	 * Moves the man Object according to the user-input.
	 */
	public void move() {
		Scanner in = new Scanner(System.in);

		if (in.next().equals('w')) {
			man.setDirection(1);
		} else if (in.next().equals('s')) {
			man.setDirection(2);
		} else if (in.next().equals('d')) {
			man.setDirection(3);
		} else if (in.next().equals('a')) {
			man.setDirection(4);
		}
		
		// Bomb

		in.close();
		printStatus();
	}

	/**
	 * Prints the status of the man object:
	 * x and y coordinates, direction
	 */
	public void printStatus() {
		System.out.println("-> Player position: [" + man.getX() + "] ["
				+ man.getY() + "Direction: " + man.getDirection());
	}
}
