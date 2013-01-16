package bombgame.ui.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import bombgame.controller.gamehandler.IGameHandler;
import bombgame.entities.IBomb;
import bombgame.entities.IExplosion;
import bombgame.entities.IField;
import bombgame.entities.IMan;

/**
 * Defines the game component of the gui.
 * @author Jega, Rookfighter
 *
 */
public final class GraphicalUIGame extends BasicGameState {
	
	public static final int ID = 2;
	
	// Can put this in player
	public static final char UP = 'w';
	public static final char LEFT = 'a';
	public static final char DOWN = 's';
	public static final char RIGHT = 'd';
	public static final char BOMB = 'j';
	
	private static final int UPDATE_INTERVAL = 150;
	
	private IGameHandler handler;
	private IField field;
	private char userinput = '?';
	
	private static final String WALL = "res/wall.png";
	private static final String PATH = "res/path.png";
	private static final String MAN = "res/man.png";
	private static final String BOMBS = "res/bomb.png";
	private static final String EXPLOSION = "res/explosion.png";
	private static final String MUSIC = "res/dstbreakout.ogg";
	private Music music;
	private Image wallimg;
	private Image pathimg;
	private Image manimg;
	private Image bombimg;
	private Image explosionimg;
	
	private int widthpx;
	private int heightpx;
	
	private StateBasedGame game;
	
	/**
	 * Creates an interface for a bomberman game.
	 * @param handler - handler of the game.
	 */
	public GraphicalUIGame(IGameHandler handler) {
		this.handler = handler;
		field = handler.getField();
	}
	
	/**
	 * Initializes the interface.
	 */
	public void init(GameContainer container, StateBasedGame game)
	throws SlickException {
	
		this.game = game;
		
		// Tile size
		int width = field.getWidth();
		int height = field.getHeight();
		
		// pixel size of each element
		widthpx = container.getWidth() / width;
		heightpx = container.getHeight() / height;
		
		// load images
		wallimg = new Image(WALL);
		pathimg = new Image(PATH);
		manimg = new Image(MAN);
		bombimg = new Image(BOMBS);
		explosionimg = new Image(EXPLOSION);
		music = new Music(MUSIC);
		music.loop();
		
		container.setMinimumLogicUpdateInterval(UPDATE_INTERVAL);
	}
	
	/**
	 * Renders the images for the gui.
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	throws SlickException {
		
		// Go through the complete field
		for (int i = 0; i < field.getHeight(); i++) {
			for (int j = 0; j < field.getWidth(); j++) {
					int x = j * widthpx;
					int y = i * heightpx;
		
				if (field.getField()[j][i] == null) {
					pathimg.draw(x, y, widthpx, heightpx);
				} else if (field.getField()[j][i] instanceof IMan) {
					manimg.draw(x, y, widthpx, heightpx);
				} else if (field.getField()[j][i] instanceof IBomb) {
					bombimg.draw(x, y, widthpx, heightpx);
				} else if (field.getField()[j][i] instanceof IExplosion) {
					explosionimg.draw(x, y, widthpx, heightpx);
				} else {
					wallimg.draw(x, y, widthpx, heightpx);
				}
			}
		}
	}
	
	/**
	 * Updates the logic of the game.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
	throws SlickException {
	
		if(handler.gameOver()) {
			game.enterState(GraphicalUIGameOver.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		if(handler.getPlayer() != null) {
			handler.getPlayer().move(userinput);
		}
		handler.updateAll();
	}
		
	/**
	 * Returns the ID of this component.
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Checks key inputs.
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_W) {
			userinput = UP;
		}
		if (key == Input.KEY_A) {
			userinput = LEFT;
		}
		if (key == Input.KEY_S) {
			userinput = DOWN;
		}
		if (key == Input.KEY_D) {
			userinput = RIGHT;
		}
		if (key == Input.KEY_SPACE) {
			userinput = BOMB;
		}
		if (key == Input.KEY_ESCAPE) {
			game.enterState(GraphicalUIMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}
	
	/**
	 * Check which Keys were released.
	 */
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_W && userinput == UP) {
			userinput = '?';
		}
		if (key == Input.KEY_A && userinput == LEFT) {
			userinput = '?';
		}
		if (key == Input.KEY_S && userinput == DOWN) {
			userinput = '?';
		}
		if (key == Input.KEY_D && userinput == RIGHT) {
			userinput = '?';
		}
	}

}