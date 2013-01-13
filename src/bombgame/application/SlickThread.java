package bombgame.application;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import bombgame.ui.gui.GraphicalUI;

public class SlickThread implements Runnable{

	private AppGameContainer container;
	private static final String ICON = "res/icon.png";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public SlickThread(GraphicalUI gui) {
		try {
			container = new AppGameContainer(gui);
			container.setDisplayMode(WIDTH, HEIGHT, false);
			container.setIcon(ICON);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
