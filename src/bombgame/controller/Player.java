package bombgame.controller;

import bombgame.entities.IMan;
import bombgame.entities.impl.Man;

/**
 * This class represents the human player. It has one Man variable which is the
 * players figure.
 * 
 * @author jens
 * 
 */
public final class Player {

	private IMan man;

	/**
	 * Creates new player with given man.
	 * @param man
	 */
	public Player(IMan man) {
		this.man = man;
	}

	/**
	 * Moves the man Object according to the user-input.
	 */
	public void move(char c) {
		if (c == 'w') {
			man.setDirection(Man.UP);
		} else if (c == 's') {
			man.setDirection(Man.DOWN);
		} else if (c == 'd') {
			man.setDirection(Man.RIGHT);
		} else if (c == 'a') {
			man.setDirection(Man.LEFT);
		} else if (c == 'j') {
			man.setPlaceBomb(true);	
			man.setDirection(Man.NO_DIR);
		} else {
			man.setDirection(Man.NO_DIR);
		}
	}
	
	/**
	 * Returns the man object of the player
	 * @return
	 */
	public IMan getMan() {
		return man;
	}
	
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
