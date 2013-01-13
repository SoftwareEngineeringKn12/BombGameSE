package bombgame.ui.gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bombgame.controller.config.GameHandlerConfiguration;
import bombgame.controller.gamehandler.IGameHandler;

public final class GraphicalUI extends StateBasedGame {
	
	private static final String ICON = "res/icon.png";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private IGameHandler handler;
	
	public GraphicalUI(String title, IGameHandler handler) {
		super(title);
	
		this.handler = handler;
	}
	
	public void initStatesList(GameContainer container) {
		addState(new GraphicalUIMenu());
		addState(new GraphicalUIGame(handler));
	}
	
	public static void main(String[] args) {
		GameHandlerConfiguration config = new GameHandlerConfiguration(40, 24, 3);
		
		try {
			AppGameContainer container = new AppGameContainer(new GraphicalUI(
			"BombGameSE", config.createGameHandler()));
			container.setDisplayMode(WIDTH, HEIGHT, false);
			container.setIcon(ICON);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}