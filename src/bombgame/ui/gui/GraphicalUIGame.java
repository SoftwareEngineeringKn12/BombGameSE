package bombgame.ui.gui;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
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
	
	private IGameHandler handler;
	private IField field;
	private char userinput = '?';
	
	private static final String WALL = "res/wall.png";
	private static final String PATH = "res/path.png";
	private static final String MAN = "res/man.png";
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
	
	private Music music;
	private Image wallimg;
	private Image pathimg;
	private Image manimg;
	private Image bombimg;
	
	private Animation anim_man_right;
	private Animation anim_man_left;
	private Animation anim_man_up;
	private Animation anim_man_down;
	private ParticleSystem system;
	private ConfigurableEmitter emitter;
	private File xmlFile = new File("res/explode.xml");
	
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
		bombimg = new Image(BOMBS);
		music = new Music(MUSIC);
		music.loop();
		
		Image i1 = new Image(MANRIGHT1);
		Image i2 = new Image(MANRIGHT2);
		anim_man_right = new Animation(new Image[]{i1, i2}, 250);
		i1 = new Image(MANLEFT1);
		i2 = new Image(MANLEFT2);
		anim_man_left = new Animation(new Image[]{i1, i2}, 250);
		i1 = new Image(MANUP1);
		i2 = new Image(MANUP2);
		anim_man_up = new Animation(new Image[]{i1, i2}, 250);
		i1 = new Image(MANDOWN1);
		i2 = new Image(MANDOWN2);
		anim_man_down = new Animation(new Image[]{i1, i2}, 250);
		
		Image pimage = new Image(MAN, false);
		system = new ParticleSystem(pimage);
		try {
			emitter = ParticleIO.loadEmitter(xmlFile);
		} catch (Exception e) {
			System.exit(0);
		}
		
		system.setRemoveCompletedEmitters(true);
		system.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		system.setUsePoints(false);
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
				} else if (field.getField()[j][i] instanceof IBomb) {
					bombimg.draw(x, y, widthpx, heightpx);
				} else if (field.getField()[j][i] instanceof IExplosion) {
					ConfigurableEmitter emitter_tmp = null;
					
					emitter_tmp = emitter.duplicate();
					emitter_tmp.setPosition(x+15, y+15);
					
					pathimg.draw(x, y, widthpx, heightpx);
					system.addEmitter(emitter_tmp);
				} else {
					wallimg.draw(x, y, widthpx, heightpx);
				}
			}
		}
		
		system.render();
	}
	
	/**
	 * Updates the logic of the game.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
	throws SlickException {
		
		container.setMinimumLogicUpdateInterval(UPDATE_INTERVAL);
		system.update(delta);
	
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
