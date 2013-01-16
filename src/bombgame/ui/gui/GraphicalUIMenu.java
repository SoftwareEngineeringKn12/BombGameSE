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

/**
 * Creates a main menu.
 * @author Jega
 *
 */
public final class GraphicalUIMenu extends BasicGameState implements ComponentListener {

	public static final int ID = 1;
	
	private static final float NORMAL_COL = 0.7f;
	private static final float MOUSEOVER_COL = 0.9f;
	
	private static final String CURSOR = "res/cursor.png";
	private static final String MENU_START_IMAGE = "res/menu_start.png";
	private static final String MENU_EXIT_IMAGE = "res/menu_exit.png";
	
	private MouseOverArea menustart;
	private MouseOverArea menuexit;
	
	private GameContainer container;
	private StateBasedGame game;
	
	/**
	 * Initializes the menu.
	 */
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	
		this.container = container;
		container.getGraphics().setBackground(Color.black);
		this.game = game;
		
		// Menus
		Image start = new Image(MENU_START_IMAGE);
		Image exit = new Image(MENU_EXIT_IMAGE);
		
		menustart = new MouseOverArea(container, start, container.getWidth()
		/ 2 - start.getWidth() / 2, container.getHeight() / 2
		- (start.getHeight()), this);
		menustart.setNormalColor(new Color(1, 1, 1, NORMAL_COL));
		menustart.setMouseOverColor(new Color(1, 1, 1, MOUSEOVER_COL));
		
		menuexit = new MouseOverArea(container, exit, container.getWidth() / 2
		- exit.getWidth() / 2, container.getHeight() / 2, this);
		menuexit.setNormalColor(new Color(1, 1, 1, NORMAL_COL));
		menuexit.setMouseOverColor(new Color(1, 1, 1, MOUSEOVER_COL));
		
		// Set Cursor
		container.setMouseCursor(CURSOR, 0, 0);
	}
	
	/**
	 * Renders the buttons of the menu.
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		menustart.render(container, g);
		menuexit.render(container, g);
	}
	
	/**
	 * Does nothing.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		
	}
	
	/**
	 * Returns the ID of this state.
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Checks button hits.
	 */
	public void componentActivated(AbstractComponent source) {
		
		if (source.equals(menustart)) {
			game.enterState(GraphicalUIGame.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		} else if (source.equals(menuexit)) {
			container.exit();
		}
	}
}