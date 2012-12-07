package bombgame.ui;

import bombgame.controller.GameHandler;
import bombgame.controller.ai.ManAI;
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
public final class TextUI implements UserInterface {
	
	private GameHandler handler;
	
	/**
	 * Creates a GameHandler with standard Constructor.
	 */
	public TextUI() {
		
		handler = new GameHandler();
		printField();
	}
	
	public TextUI(final GameObject[][] field) {
		handler = new GameHandler(field);
	}

	/**
	 * Updates the KI calculation, movement of Man-objects, placement of Bombs
	 * and the calculation of Explosions (in this order).
	 */
	public boolean update() {
		//!! look at the order.
		//scan input false;
		//Scanner in = new Scanner(System.in);
		
		/*
		if(handler.getMen().size() <= 1) {
			return false;
		}
		
		for(ManAI ai : ais) {
			ai.calcNextStep();
		}
		player.move();
		
		handler.moveAll();
		handler.updateBombs();
		handler.placeBombs();
		handler.updateExplosion();*/
		
		handler.updateAll();
		
		printAllPlayers();
		printAI();
		printBombs();
		printExplosions();
		printField();
		return true;
	}
	
	/**
	 * Returns the GameHandler-object currently used by the TextUI.
	 * @return - GameHandler-object used by TextUI
	 */
	public GameHandler getGameHandler() {
		return handler;
	}
	
}


