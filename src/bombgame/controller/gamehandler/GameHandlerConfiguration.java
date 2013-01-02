package bombgame.controller.gamehandler;

import java.util.List;

import bombgame.controller.ai.impl.ManAI;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.player.impl.Player;
import bombgame.entities.impl.Man;

/**
 * Holds the configuration for the GameHandler and constructs
 * a new GameHandler with this configuration.
 * This is the class which creates the concrete objects,
 * in all other classes the interfaces are used.
 * 
 * Creation in App:
 * config = new GHconfig();
 * config.setfield(xw, yw);
 * config.stenumAI(3);
 * config.set...
 * IGH = config.createGH();
 * 
 * @author jens
 *
 */
public class GameHandlerConfiguration {
	
	public final static int TOP_EDGE = 0;
	public final static int BOTTOM_EDGE = 1;
	public final static int LEFT_EDGE = 2;
	public final static int RIGHT_EDGE = 3;
	
	private int field_width;
	
	private int field_height;
	
	private int numberOfAIs;
	
	private GameHandler handler;
	
	private Player player;
	
	private List<ManAI> ais;
	
	/**
	 * Maximal number of AIs
	 */
	private static final int MAXAI = 6;
	
	/**
	 * Maximal width of the game field.
	 */
	private static final int MAX_WIDTH = 100;
	
	/**
	 * Maximal height of the game field.
	 */
	private static final int MAX_HEIGHT = 100;
	
	public GameHandlerConfiguration(int width, int height, int ais) {
		field_width = width;
		field_height = height;
		numberOfAIs = ais;
	}
	
	/**
	 * Creates a new GameHandler with the given configuration.
	 * @return
	 */
	public IGameHandler createGameHandler() {
		
		// Check if configuration is complete
		if (!checkConfiguration()) {
			throw new IllegalArgumentException("GameHandler configuration is not correct.");
		}
		
		// Create player
		player = new Player(new Man(0, 0));
		
		// Create GameHandler
		// (to run a minimal game GameHandler needs the width, height and a player.
		handler = new GameHandler(field_width, field_height);
		
		// Set player
		handler.setPlayer(player);
		
		spawn();
		for (ManAI ai : ais) {
			handler.addAI(ai);
		}
		
		return handler;
	}
	
	/**
	 * Checks if all settings are correct.
	 * @return
	 */
	private boolean checkConfiguration() {
		if (field_width <= 0 || field_width > MAX_WIDTH) {
			return false;
		}
		
		if (field_height <= 0 || field_height > MAX_HEIGHT) {
			return false;
		}
		
		if (numberOfAIs > MAXAI) {
			return false;
		}
			
		return true;
	}
	
	public void setFieldWidth(int width) {
		field_width = width;
	}
	
	public void setFieldheight(int height) {
		field_height = height;
	}
	
	public void setNumberOfAIs(int ais) {
		numberOfAIs = ais;
	}
	
	public int setFieldWidth() {
		return field_width;
	}
	
	public int getFieldheight() {
		return field_height;
	}
	
	public int getNumberOfAIs() {
		return numberOfAIs;
	}
	
	/**
	 * Spawns Men on the Edges of the field with same distance.
	 */
	private void spawn() {
		
		int menperedge = numberOfAIs / 4;
		int rest = numberOfAIs % 4;
		int[] men = {menperedge, menperedge, menperedge, menperedge };
		
		while(rest > 0) {
			men[rest % 4] += 1;
			rest--;
		}
		
		placeMen(men[TOP_EDGE], 1, 0, 0,0);
		placeMen(men[BOTTOM_EDGE], 1, 0, 0, handler.getField()[0].length - 1);
		placeMen(men[LEFT_EDGE], 0, 1,0,0);
		placeMen(men[RIGHT_EDGE], 0, 1, handler.getField().length - 1,0);
		
	}
	
	/**
	 * Places the men at the given edge of the field.
	 */
	private void placeMen(int mencount, int xfac, int yfac, int x, int y) {
		int distx = handler.getField().length / (mencount + 1);
		int disty = handler.getField()[0].length / (mencount + 1);

		
		while(mencount > 0) {
			x += distx * xfac;
			y += disty * yfac;
			ais.add(new ManAI(new Man(x, y), handler));
			mencount--;
		}
	}
}
