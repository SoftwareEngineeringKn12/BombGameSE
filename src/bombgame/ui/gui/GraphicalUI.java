package bombgame.ui.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import bombgame.controller.gamehandler.IGameHandler;

/**
 * Creates a graphical interface for a bombermangame.
 * @author Jega
 *
 */
public final class GraphicalUI extends StateBasedGame {
	
	/**
	 * Handler of the game.
	 */
	private IGameHandler handler;
	
	/**
	 * Creates a frame with the given title and handler.
	 * @param title - title of frame
	 * @param handler - handler of the game
	 */
	public GraphicalUI(String title, IGameHandler handler) {
		super(title);
	
		this.handler = handler;
	}
	
	/**
	 * Adds the different components to teh Interface.
	 */
	public void initStatesList(GameContainer container) {
		addState(new GraphicalUIMenu());
		addState(new GraphicalUIGame(handler));
		addState(new GraphicalUIGameOver(handler));
	}

}