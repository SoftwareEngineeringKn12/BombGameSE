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
		
		// Create and set AIs
		for (int i = 0; i < MAXAI; i++) {
			// new AI with spawn
			//handler.addAI(ai);
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
	
	//abstract void spawn(Man man);
}
