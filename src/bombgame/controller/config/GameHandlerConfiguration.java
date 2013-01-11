package bombgame.controller.config;

import java.util.LinkedList;
import java.util.List;

import bombgame.controller.ai.impl.ManAI;
import bombgame.controller.gamehandler.IGameHandler;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.player.impl.Player;
import bombgame.entities.IField;
import bombgame.entities.impl.Man;

/**
 * Holds the configuration for the GameHandler and constructs
 * a new GameHandler with this configuration.
 * This is the class which creates the concrete objects,
 * in all other classes the interfaces are used.
 * 
 * @author jens
 *
 */
public final class GameHandlerConfiguration {
	
	public static final int TOP_EDGE = 0;
	public static final int BOTTOM_EDGE = 1;
	public static final int LEFT_EDGE = 2;
	public static final int RIGHT_EDGE = 3;
	
	public static final int EDGES = 4;
	
	private int fieldwidth;
	
	private int fieldheight;
	
	private int numberOfAIs;
	
	private GameHandler handler;
	
	private IField field;
	
	private Player player;
	
	private List<ManAI> ais;
	
	/**
	 * Maximal number of AIs
	 */
	public static final int MAXAI = 6;
	
	/**
	 * Maximal width of the game field.
	 */
	public static final int MAX_WIDTH = 100;
	
	/**
	 * Maximal height of the game field.
	 */
	public static final int MAX_HEIGHT = 100;
	
	public GameHandlerConfiguration(int width, int height, int numberAis) {
		fieldwidth = width;
		fieldheight = height;
		numberOfAIs = numberAis;
		
		ais = new LinkedList<ManAI>();
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
		player = new Player(new Man(fieldwidth/2, fieldheight/2));
		
		// Create GameHandler
		// (to run a minimal game GameHandler needs the width, height and a player.
		handler = new GameHandler(fieldwidth, fieldheight);
		field = handler.getField();
		
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
	protected boolean checkConfiguration() {
		boolean ret = true;
		
		if (fieldwidth <= 0 || fieldwidth > MAX_WIDTH) {
			ret = false;
		}
		
		if (fieldheight <= 0 || fieldheight > MAX_HEIGHT) {
			ret = false;
		}
		
		if (numberOfAIs <= 0 || numberOfAIs > MAXAI) {
			ret = false;
		}
			
		return ret;
	}
	
	public void setFieldWidth(int width) {
		fieldwidth = width;
	}
	
	public void setFieldHeight(int height) {
		fieldheight = height;
	}
	
	public void setNumberOfAIs(int ais) {
		numberOfAIs = ais;
	}
	
	public int getFieldWidth() {
		return fieldwidth;
	}
	
	public int getFieldHeight() {
		return fieldheight;
	}
	
	public int getNumberOfAIs() {
		return numberOfAIs;
	}
	
	/**
	 * Spawns Men on the Edges of the field with same distance.
	 */
	private void spawn() {
		
		ais.clear();
		
		int menperedge = numberOfAIs / EDGES;
		int rest = numberOfAIs % EDGES;
		int[] men = {menperedge, menperedge, menperedge, menperedge };
		
		while(rest > 0) {
			men[rest % EDGES] += 1;
			rest--;
		}
		
		placeMen(men[TOP_EDGE], 1, 0, 0,0);
		placeMen(men[BOTTOM_EDGE], 1, 0, 0, field.getHeight() - 1);
		placeMen(men[LEFT_EDGE], 0, 1,0,0);
		placeMen(men[RIGHT_EDGE], 0, 1, field.getWidth()- 1,0);
		
	}
	
	/**
	 * Places the men at the given edge of the field.
	 */
	private void placeMen(int mencount, int xfac, int yfac, int x, int y) {
		int distx = field.getWidth() / (mencount + 1);
		int disty = field.getHeight() / (mencount + 1);

		
		while(mencount > 0) {
			x += distx * xfac;
			y += disty * yfac;
			ais.add(new ManAI(new Man(x, y), field));
			mencount--;
		}
	}
}
