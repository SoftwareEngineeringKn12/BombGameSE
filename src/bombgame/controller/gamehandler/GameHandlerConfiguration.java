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
 * --> Factory method pattern for different spawn possibilities.
 * create subclasses with different spawn method
 * (the spawnMan method in gamehandler checks if it is possible to set the man
 * at this position)
 * ----> subclass: GameHandlerConfiguration_spawnEdge
 * 
 * @author jens
 *
 */
public abstract class GameHandlerConfiguration {
	
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
	
	/**
	 * Creates a new GameHandler with the given configuration.
	 * @return
	 */
	public IGameHandler createGameHandler() {
		
		// Check if configuration is complete
		if (!checkConfiguration()) {
			//throw new wahtever Exception("GameHandler configuration is not correct.");
		}
		
		// Create player
		player = new Player(new Man(0, 0));
		
		// Create GameHandler
		// (to run a minimalistic game GameHandler needs the width, height and a player.
		handler = new GameHandler(field_width, field_height, player);
		
		// Set AI
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
	
	/**
	 * Factory method.
	 */
	abstract void spawn(Man man);
	/**
	 * Hier bin ihc nicht sicher, ob das wirklcih ein anwendungsfall
	 * fuer eine fabric ist, weil wie haben ja keine vererbung, also keien
	 * verschiedenen Man implementierungen oder ähnlich. also kann man
	 * das ja einfach nur mir verscheidenen methoden machen und dann im konstruktor
	 * die art des spawnens bestimmen. würd wahrscheinlcih dann die struktur auch kalrer machen.
	 */
}
