package bombgame.application;

import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.config.GameHandlerConfiguration;
import bombgame.ui.TextUI;
import bombgame.ui.gui.GraphicalUI;

/**
 * Starts the game.
 * @author Rookfighter
 *
 */
public final class App {
	
	/**
	 * Width of the field.
	 */
	private static final int WIDTH = 30;
	
	/**
	 * Height of the field.
	 */
	private static final int HEIGHT = 20;
	
	/**
	 * Number of AIs.
	 */
	private static final int AIS = 4;
	
	/**
	 * Thread that holds TUI.
	 */
	private Thread tuithread;
	
	/**
	 * Thread that holds GUI.
	 */
	private Thread guithread;
	
	/**
	 * Configures log4j, creates Scanner and TUI.
	 */
	public App() {
		// Configure log4j
		PropertyConfigurator.configureAndWatch("log4j.properties");
		
		// Create TUI & GUI
		GameHandlerConfiguration config = new GameHandlerConfiguration(WIDTH, HEIGHT, AIS);
		TextUI tui = new TextUI(config.createSingleGameHandler());
		GraphicalUI gui = new GraphicalUI("BombGameSE", config.createSingleGameHandler());
		
		tuithread = new Thread(new TUIThread(tui));
		guithread = new Thread(new SlickThread(gui));
	}
	
	/**
	 * Starts the TUI loop
	 */
	public void start() {
		tuithread.start();
		guithread.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		App application = new App();
		application.start();
	}

}
