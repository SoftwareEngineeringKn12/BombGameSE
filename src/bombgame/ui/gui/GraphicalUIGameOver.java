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
 * @author Fabian
 *
 */
public class GraphicalUIGameOver extends BasicGameState{
	public static final int ID = 3;
	private static final String GAMEOVER = "res/GameOver.png";
	private static final String AIWIN = "res/aiwin.png";
	private static final String PLAYERWIN = "res/playerwin.png";
	private static final int WIDTH = 500;
	private static final int HEIGHT = 142;
	
	private static Image gameover;
	private static Image aiwin;
	private static Image playerwin;
	private boolean win;

	public GraphicalUIGameOver(IGameHandler handler) {
		this.win = handler.getField().getMen().contains(handler.getPlayer().getMan());
		
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		gameover = new Image(GAMEOVER);
		aiwin = new Image(AIWIN);
		playerwin = new Image(PLAYERWIN);
		
	}

	@Override
	public void render(GameContainer cont, StateBasedGame game, Graphics arg2)
			throws SlickException {
		gameover.draw((cont.getWidth()-WIDTH) / 2 ,(cont.getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
		if(win) {
			playerwin.draw((cont.getWidth()-WIDTH) / 2, (cont.getHeight() - HEIGHT) / 2 + HEIGHT, WIDTH, HEIGHT);
		} else {
			aiwin.draw((cont.getWidth()-WIDTH) / 2, (cont.getHeight() - HEIGHT) / 2 + HEIGHT, WIDTH, HEIGHT);
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
	}

	@Override
	public int getID() {
		return ID;
	}

}
