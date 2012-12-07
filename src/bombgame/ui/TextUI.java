package bombgame.ui;

import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.entities.GameObject;

/**
 * TextUI brings all components together.
 * @author JeGa, Rookfighter
 *
 */
public final class TextUI implements UserInterface {
	
	private IGameHandler handler;
	
	/**
	 * Creates a GameHandler with standard Constructor.
	 */
	public TextUI() {
		handler = new GameHandler();
	}
	
	public TextUI(final GameObject[][] field) {
		handler = new GameHandler(field);
	}

	/**
	 * Updates the KI calculation, movement of Man-objects, placement of Bombs
	 * and the calculation of Explosions (in this order).
	 */
	public boolean update() {
		//scan input false;
		
		handler.updateAll();
		System.out.println(handler);
		
		return true;
	}
	
	/**
	 * Returns the GameHandler-object currently used by the TextUI.
	 * @return - GameHandler-object used by TextUI
	 */
	public IGameHandler getGameHandler() {
		return handler;
	}
	
}