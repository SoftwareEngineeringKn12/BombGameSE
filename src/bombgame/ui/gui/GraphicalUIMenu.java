package bombgame.ui.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public final class GraphicalUIMenu extends BasicGameState implements ComponentListener {

	public static final int ID = 1;
	
	private static final String CURSOR = "res/cursor.png";
	private static final String MENU_START_IMAGE = "res/menu_start.png";
	private static final String MENU_EXIT_IMAGE = "res/menu_exit.png";
	
	private MouseOverArea menu_start;
	//private MouseOverArea menu_configuration;
	//private MouseOverArea menu_instructions;
	private MouseOverArea menu_exit;
	//private int numberOfMenuItems = 2;
	//private int selected;
	
	private GameContainer container;
	private StateBasedGame game;
	
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	
		this.container = container;
		this.game = game;
		//selected = 1;
		
		// Menus
		Image start = new Image(MENU_START_IMAGE);
		Image exit = new Image(MENU_EXIT_IMAGE);
		
		menu_start = new MouseOverArea(container, start, container.getWidth()
		/ 2 - start.getWidth() / 2, container.getHeight() / 2
		- (start.getHeight()), this);
		menu_start.setNormalColor(new Color(1, 1, 1, 0.7f));
		menu_start.setMouseOverColor(new Color(1, 1, 1, 0.9f));
		
		menu_exit = new MouseOverArea(container, exit, container.getWidth() / 2
		- exit.getWidth() / 2, container.getHeight() / 2, this);
		menu_exit.setNormalColor(new Color(1, 1, 1, 0.7f));
		menu_exit.setMouseOverColor(new Color(1, 1, 1, 0.9f));
		
		// Set Cursor
		container.setMouseCursor(CURSOR, 0, 0);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		menu_start.render(container, g);
		menu_exit.render(container, g);
		}
		
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		
	}
	
	public int getID() {
		return ID;
	}
	
	public void componentActivated(AbstractComponent source) {
		
		if (source == menu_start) {
			game.enterState(GraphicalUIGame.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			// Create new game
			// deactivate menu
			// ...
		} else if (source == menu_exit) {
			container.exit();
		}
	}
}