package bombgame.ui;

import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;



import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.entities.impl.GameObject;

/**
 * TextUI brings all components together.
 * @author JeGa, Rookfighter
 *
 */
public final class TextUI implements UserInterface, Observer {
	
	private IGameHandler handler;
	
	private static Logger logger = Logger.getRootLogger();
	
	/**
	 * Creates a GameHandler with standard Constructor.
	 */
	public TextUI(GameHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
		
	    ConsoleAppender consoleAppender = new ConsoleAppender( );
	    logger.addAppender( consoleAppender );
	}
	
	protected TextUI(final GameObject[][] field) {
		handler = new GameHandler(field);
	}

	/**
	 * Updates the KI calculation, movement of Man-objects, placement of Bombs
	 * and the calculation of Explosions (in this order).
	 */
	public boolean update() {
		//scan input false;
		
		handler.updateAll();
		
		return true;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(!(arg1 instanceof String)) {
			return;
		}
		String s = (String) arg1;
		logger.info(s);
		logger.debug(s);
		logger.warn(s);
		
	}
	
}