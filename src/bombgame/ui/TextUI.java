package bombgame.ui;

import java.util.Observable;
import java.util.Observer;

import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.entities.GameObject;

/**
 * TextUI brings all components together.
 * @author JeGa, Rookfighter
 *
 */
public final class TextUI implements UserInterface, Observer {
	
	private IGameHandler handler;
	
	/**
	 * Creates a GameHandler with standard Constructor.
	 */
	public TextUI(GameHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
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
		System.out.println(s);
		
	}
	
}