package bombgame.ui;

import java.util.Observable;
import java.util.Observer;
import org.apache.log4j.Logger;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.entities.impl.GameObject;

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
	public TextUI(GameHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
		
	    
	}
	
	/**
	 * Creates a GameHandler for unit tests
	 * @param field - GameObject field
	 */
	protected TextUI(final GameObject[][] field) {
		handler = new GameHandler(field);
	}

	/**
	 * Updates the KI calculation, movement of Man-objects, placement of Bombs,
	 * calculation of Explosions, ...
	 */
	@Override
	public boolean update(String str) {
		//!! to interface: handler.getPlayer().move(str);
		
		handler.getPlayer().move(str);
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
			return;
		}
		
		String s = (String) message;
		logger.info(s);
	}
	
}