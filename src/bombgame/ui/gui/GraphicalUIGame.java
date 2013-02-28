package bombgame.ui.gui;

import org.newdawn.slick.Animation;
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
import bombgame.entities.impl.Man;

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
	private static final int UPDATE_INTERVAL_RESET = 10;
	private static final int FPS = 60;
	private static final int ANIM_LENGTH = 250;
	
	private IGameHandler handler;
	private IField field;
	private char userinput = '?';
	
	private static final String WALL = "res/wall.png";
	private static final String PATH = "res/path.png";
	private static final String MAN = "res/man.png";
	private static final String PLAYER_MAN = "res/player_man.png";
	private static final String BOMBS = "res/bomb.png";
	private static final String EXPLOSION = "res/explosion.png";
	private static final String MUSIC = "res/dstbreakout.ogg";
	// Man walk
	private static final String MANRIGHT1 = "res/man_walk1_right.png";
	private static final String MANRIGHT2 = "res/man_walk2_right.png";
	private static final String MANLEFT1 = "res/man_walk1_left.png";
	private static final String MANLEFT2 = "res/man_walk2_left.png";
	private static final String MANUP1 = "res/man_walk1_up.png";
	private static final String MANUP2 = "res/man_walk2_up.png";
	private static final String MANDOWN1 = "res/man_walk1_down.png";
	private static final String MANDOWN2 = "res/man_walk2_down.png";
	// Player man walk
	private static final String PLAYER_MANRIGHT1 = "res/player_man_walk1_right.png";
	private static final String PLAYER_MANRIGHT2 = "res/player_man_walk2_right.png";
	private static final String PLAYER_MANLEFT1 = "res/player_man_walk1_left.png";
	private static final String PLAYER_MANLEFT2 = "res/player_man_walk2_left.png";
	private static final String PLAYER_MANUP1 = "res/player_man_walk1_up.png";
	private static final String PLAYER_MANUP2 = "res/player_man_walk2_up.png";
	private static final String PLAYER_MANDOWN1 = "res/player_man_walk1_down.png";
	private static final String PLAYER_MANDOWN2 = "res/player_man_walk2_down.png";
	
	private Music music;
	private Image wallimg;
	private Image pathimg;
	private Image manimg;
	private Image playermanimg;
	private Image bombimg;
	private Image explosion;
	
	private Animation anim_man_right;
	private Animation anim_man_left;
	private Animation anim_man_up;
	private Animation anim_man_down;
	// Player
	private Animation player_anim_man_right;
	private Animation player_anim_man_left;
	private Animation player_anim_man_up;
	private Animation player_anim_man_down;
	
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
		
		container.setTargetFrameRate(FPS);
		
		// pixel size of each element
		widthpx = container.getWidth() / width;
		heightpx = container.getHeight() / height;
		
		// load images
		wallimg = new Image(WALL);
		pathimg = new Image(PATH);
		manimg = new Image(MAN);
		playermanimg = new Image(PLAYER_MAN);
		bombimg = new Image(BOMBS);
		explosion = new Image(EXPLOSION);
		music = new Music(MUSIC);
		music.loop();
		
		Image i1 = new Image(MANRIGHT1);
		Image i2 = new Image(MANRIGHT2);
		anim_man_right = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
		i1 = new Image(MANLEFT1);
		i2 = new Image(MANLEFT2);
		anim_man_left = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
		i1 = new Image(MANUP1);
		i2 = new Image(MANUP2);
		anim_man_up = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
		i1 = new Image(MANDOWN1);
		i2 = new Image(MANDOWN2);
		anim_man_down = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
		
		// Player
		i1 = new Image(PLAYER_MANRIGHT1);
		i2 = new Image(PLAYER_MANRIGHT2);
		player_anim_man_right = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
		i1 = new Image(PLAYER_MANLEFT1);
		i2 = new Image(PLAYER_MANLEFT2);
		player_anim_man_left = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
		i1 = new Image(PLAYER_MANUP1);
		i2 = new Image(PLAYER_MANUP2);
		player_anim_man_up = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
		i1 = new Image(PLAYER_MANDOWN1);
		i2 = new Image(PLAYER_MANDOWN2);
		player_anim_man_down = new Animation(new Image[]{i1, i2}, ANIM_LENGTH);
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
					IMan man = (IMan) field.getField()[j][i];
					
					if (handler.getPlayer().getMan().equals(man)) {
						if (man.getDirection() == Man.RIGHT) {
							player_anim_man_right.draw(x, y, widthpx, heightpx);
						} else if (man.getDirection() == Man.LEFT) {
							player_anim_man_left.draw(x, y, widthpx, heightpx);
						} else if (man.getDirection() == Man.UP) {
							player_anim_man_up.draw(x, y, widthpx, heightpx);
						} else if (man.getDirection() == Man.DOWN) {
							player_anim_man_down.draw(x, y, widthpx, heightpx);
						} else {
							playermanimg.draw(x, y, widthpx, heightpx);
						}
					} else {
						if (man.getDirection() == Man.RIGHT) {
							anim_man_right.draw(x, y, widthpx, heightpx);
						} else if (man.getDirection() == Man.LEFT) {
							anim_man_left.draw(x, y, widthpx, heightpx);
						} else if (man.getDirection() == Man.UP) {
							anim_man_up.draw(x, y, widthpx, heightpx);
						} else if (man.getDirection() == Man.DOWN) {
							anim_man_down.draw(x, y, widthpx, heightpx);
						} else {
							manimg.draw(x, y, widthpx, heightpx);
						}
					}
					
				} else if (field.getField()[j][i] instanceof IBomb) {
					bombimg.draw(x, y, widthpx, heightpx);
				} else if (field.getField()[j][i] instanceof IExplosion) {
					explosion.draw(x, y, widthpx, heightpx);
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
		
		container.setMinimumLogicUpdateInterval(UPDATE_INTERVAL);
	
		if(handler.gameOver()) {
			container.setMinimumLogicUpdateInterval(UPDATE_INTERVAL_RESET);
			game.enterState(GraphicalUIGameOver.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		} else if(handler.getPlayer() != null) {
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
