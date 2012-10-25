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

		if (in.next().equals('W')) {
			// man.move
		} else if (in.next().equals('S')) {

		} else if (in.next().equals('A')) {

		} else if (in.next().equals('D')) {

		}

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
