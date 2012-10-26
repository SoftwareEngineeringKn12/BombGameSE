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
		} else if (in.next().equals('j')) {
			man.setPlaceBomb(true);	
		} else {
			man.setDirection(Man.NO_DIR);
		}

		in.close();
	}
	
	/**
	 * Returns the man object of the player
	 * @return
	 */
	/*public Man getMan() {
		return man;
	}
	*/
	
	/**
	 * Returns the status of the man object:
	 * - x and y coordinates
	 * - direction
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("-> Player: ");
		str.append("[" + man.getX() + "]");
		str.append(" [" + man.getY() + "]");
		str.append(" Direction: " + man.getDirection());
		
		return str.toString();
	}
}
