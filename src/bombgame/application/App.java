package bombgame.application;

import org.apache.log4j.PropertyConfigurator;

import bombgame.controller.config.GameHandlerConfiguration;
import bombgame.ui.TextUI;
import bombgame.ui.gui.GraphicalUI;

/**
 * 
 * @author Rookfighter
 *
 */
public final class App {
	
	
	private Thread tuithread;
	
	private Thread guithread;
	
	/**
	 * Configures log4j, creates Scanner and TUI.
	 */
	public App() {
		// Configure log4j
		PropertyConfigurator.configureAndWatch("log4j.properties");
		
		// Create TUI & GUI
		GameHandlerConfiguration config = new GameHandlerConfiguration(30, 20, 3);
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
