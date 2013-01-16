package bombgame.ui.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bombgame.controller.gamehandler.IGameHandler;

/**
 * Creates a gameoverscreen.
 * @author Rookfighter
 *
 */
public class GraphicalUIGameOver extends BasicGameState{
	
	/**
	 * ID of this state.
	 */
	public static final int ID = 3;
	
	/**
	 * Gameover path.
	 */
	private static final String GAMEOVER = "res/gameover.png";
	
	/**
	 * aiwin path.
	 */
	private static final String AIWIN = "res/winnerai.png";
	
	/**
	 * playerwin path.
	 */
	private static final String PLAYERWIN = "res/winnerspieler.png";
	
	/**
	 * width of the emblems.
	 */
	private static final int WIDTH = 500;
	
	/**
	 * height of the emblems.
	 */
	private static final int HEIGHT = 142;
	
	/**
	 * gameover image.
	 */
	private static Image gameover;
	
	/**
	 * aiwin image.
	 */
	private static Image aiwin;
	
	/**
	 * playerwin image.
	 */
	private static Image playerwin;
	
	/**
	 * determines wether player won or not.
	 */
	private boolean win;
	
	/**
	 * handler of the GUI
	 */
	private IGameHandler handler;

	/**
	 * 
	 * @param handler
	 */
	public GraphicalUIGameOver(IGameHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * Initializes the gameover screen.
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		gameover = new Image(GAMEOVER);
		aiwin = new Image(AIWIN);
		playerwin = new Image(PLAYERWIN);
		
	}

	/**
	 * Renders the emblems.
	 */
	@Override
	public void render(GameContainer cont, StateBasedGame game, Graphics arg2)
			throws SlickException {
		
		if(handler.getPlayer() != null) {
			this.win = handler.getField().getMen().contains(handler.getPlayer().getMan());
		}
		
		gameover.draw((cont.getWidth()-WIDTH) / 2 ,(cont.getHeight() - HEIGHT) / 4, WIDTH, HEIGHT);
		if(win) {
			playerwin.draw((cont.getWidth()-WIDTH) / 2, (cont.getHeight() - HEIGHT) / 4 + HEIGHT, WIDTH, HEIGHT);
		} else {
			aiwin.draw((cont.getWidth()-WIDTH) / 2, (cont.getHeight() - HEIGHT) / 4 + HEIGHT, WIDTH, HEIGHT);
		}
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
	}

	/**
	 * Returns the ID of this state.
	 */
	@Override
	public int getID() {
		return ID;
	}

}
