package bombgame.tui;

import bombgame.controller.GameHandler;
import bombgame.controller.PlayerTUI;
import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;

/**
 * TextUI brings all components together.
 * @author JeGa, Rookfighter
 *
 */
public final class TextUI {
	
	private GameHandler handler;
	private PlayerTUI player;
	// KI - Maybbe List to store ki
	
	/**
	 * Creates a GameHandler with standard Constructor.
	 */
	public TextUI() {
		Man manPlayer = new Man(0, 0); // Man for player
		
		handler = new GameHandler();
		player = new PlayerTUI(manPlayer);
		// new KI
		handler.addObject(manPlayer);
	}

	/**
	 * Updates the KI calculation, movement of Man-objects, placement of Bombs
	 * and the calculation of Explosions (in this order).
	 */
	public void update() {
		//!! look at the order.
		
		//calcKI()
		player.move();
		
		handler.moveAll();
		handler.updateBombs();
		handler.placeBombs();
		
		printAllPlayers();
		printBombs();
		printField();
	}
	
	/**
	 * Prints the current Map on the standard output.
	 */
	public void printField() {
		StringBuilder sb = new StringBuilder();
		GameObject[][] field = handler.getField();
		
		//print offscreen
		for(int i = 0; i < field[0].length; i++) {
			
			for(int j = 0; j < field.length; j++) {
				
				if(field[j][i] instanceof Wall) {
					
					sb.append(" # ");
					
				} else if(field[j][i] instanceof Man) {
				
					sb.append(" M ");
					
				} else if(field[j][i] instanceof Bomb) {
					
					sb.append(" O ");
					
				}  else if(field[j][i] instanceof Explosion) {
					
					sb.append(" X ");
					
				} else {
					
					sb.append(" - ");
					
				}
			}
			
			//next line
			sb.append("\n");
			
		}
		
		//print on screen
		System.out.println(sb.toString());
	}
	
	
	/**
	 * Prints coordinates and direction of all Man-objects (AI and Player)
	 * on the standard output.
	 */
	public void printAllPlayers() {
		System.out.println("Players:");
		System.out.println(player);
		// syso(ki) if more ki -> loop from list
	}
	
	/**
	 * Prints the location of all bombs in game.
	 */
	public void printBombs() {
		System.out.println("Bombs:");
		for (Bomb bomb : handler.getBombs()) {
			System.out.println(bomb);
		}
	}
	
}


