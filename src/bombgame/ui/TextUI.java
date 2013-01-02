package bombgame.ui;

import java.util.Observable;
import java.util.Observer;
import org.apache.log4j.Logger;
import bombgame.controller.gamehandler.IGameHandler;

/**
 * TextUI brings all components together.
 * @author JeGa, Rookfighter
 * 
 */
public final class TextUI implements UserInterface, Observer {
	
	/**
	 * The GameHandler -> controller for game
	 */
	private IGameHandler handler;
	
	/**
	 * Logger logs complete game actions
	 */
	private static Logger logger = Logger.getLogger(TextUI.class);
	
	/**
	 * Creates a GameHandler with standard Constructor.
	 */
	public TextUI(IGameHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
	}
	
	/**
	 * Returns GameHandler for unit tests.
	 * @return - the game handler
	 */
	protected IGameHandler getGameHandler() {
		return handler;
	}

	/**
	 * Updates the KI calculation, movement of Man-objects, placement of Bombs,
	 * calculation of Explosions, ...
	 */
	@Override
	public boolean update(String str) {
		if (handler.getPlayer() != null) {
			handler.getPlayer().move(str.charAt(0));
		}
		
		handler.updateAll();
		
		return true;
	}

	/**
	 * Notified from GameHandler after update all.
	 * Logs the game status.
	 */
	@Override
	public void update(Observable obs, Object message) {
		if(!(message instanceof String)) {
			throw new IllegalArgumentException();
		}
		
		String s = (String) message;
		logger.info(s);
	}
	
}