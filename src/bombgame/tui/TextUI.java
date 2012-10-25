package bombgame.tui;

import bombgame.controller.GameHandler;
import bombgame.controller.PlayerTUI;
import bombgame.entities.Bomb;
import bombgame.entities.Man;

/**
 * TextUI brings all components together.
 * @author jens
 *
 */
public final class TextUI {
	
	private GameHandler handler;
	private PlayerTUI player;
	
	TextUI() {
		Man manPlayer = new Man(0, 0);
		handler = new GameHandler();
		player = new PlayerTUI(manPlayer);
		
		handler.addObject(manPlayer);
	}

	/**
	 * Updates to the next move.
	 * (Used in "game loop")
	 * @return false: Exit game
	 */
	public boolean update() {
		//player.move();
		return false;
	}
	
	public void printField() {
		
	}
	
	/**
	 * Prints all players (including AI).
	 */
	public void printAllPlayers() {
		System.out.println("Players:");
		player.printStatus();
		// ki.printStatus() if more ki -> loop from list
	}
	
	/**
	 * Prints the location of all bombs in game.
	 */
	public void printBombs() {
		System.out.println("Bombs:");
		for (Bomb bomb : handler.getBombs()) {
			System.out.println("[" + bomb.getX() + "] [" + bomb.getY() + "]");
		}
	}
}
