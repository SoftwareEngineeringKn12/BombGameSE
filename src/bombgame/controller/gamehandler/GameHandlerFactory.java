package bombgame.controller.gamehandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bombgame.controller.ai.impl.ManAI;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.controller.player.impl.Player;
import bombgame.entities.impl.Man;

/**
 * This Factory produces GameHandler.
 * @author Rookfighter
 *
 */
public class GameHandlerFactory {

	public final static int AT_EDGES = 0;
	
	public final static int TOP_EDGE = 0;
	public final static int BOTTOM_EDGE = 1;
	public final static int LEFT_EDGE = 2;
	public final static int RIGHT_EDGE = 3;
	
	/**
	 * Creates a standard GameHandler with a field size of 30x20.
	 * @return - standard GameHandler
	 */
	public static GameHandler createStandardBombermanHandler() {
		
		int width = 30;
		int height = 20;
		
		Player player = new Player(new Man(0,0));
		
		GameHandler gh = new GameHandler(width , height, player);
		
		gh.addAI(new ManAI(new Man(0, height -1), gh));
		gh.addAI(new ManAI(new Man(width -1, height -1), gh));
		gh.addAI(new ManAI(new Man(width -1, 0), gh));
		
		return gh;
		
	}
	
	/**
	 * Creates a GameHandler with the specified width and height and the specified amount of AIs. This also includes one Player.
	 * The Spawnscheme determines in which form the Men are spawned.
	 * @param width - width of the field
	 * @param height - height of the field
	 * @param ais - amount of AIs
	 * @param spawnscheme - scheme for spawning
	 * @return - created GameHandler
	 */
	public static GameHandler createBombermanHandler(int width, int height, int ais, int spawnscheme) {
		
		switch(spawnscheme) {
		
		case AT_EDGES:
			return spawnAtEdges(width, height, ais + 1);
		default:
			
		}
		
		return null;
	}
	
	/**
	 * Spawns the specified amount of Man objects in the edges of the field with a size of widthxheight.
	 * @param width - width of the field
	 * @param height - height of the field
	 * @param mancount - amount of men
	 * @return - created GameHandler
	 */
	private static GameHandler spawnAtEdges(int width, int height, int mancount) {
		
		ArrayList<Man> menlist = new ArrayList<Man>();
		
		int menperedge = mancount / 4;
		int rest = mancount % 4;
		int[] men = {menperedge, menperedge, menperedge, menperedge };
		
		while(rest > 0) {
			men[rest % 4] += 1;
			rest--;
		}
		
		placeMen(men[TOP_EDGE], 1, 0, menlist, width ,height);
		placeMen(men[BOTTOM_EDGE], 1, 0, menlist, width ,height);
		placeMen(men[LEFT_EDGE], 0, 1, menlist, width ,height);
		placeMen(men[RIGHT_EDGE], 0, 1, menlist, width ,height);
		
		Random rand = new Random();
		int playernr = rand.nextInt(4);
		Player player = new Player(menlist.remove(playernr));
		
		GameHandler gh = new GameHandler(width, height, player);
		
		for(Man m : menlist) {
			gh.addAI(new ManAI(m,gh));
		}
		
		return gh;
	}
	
	/**
	 * Spawns men in same distance from each other.
	 * @param mencount - amount of men
	 * @param xfac - xfactor
	 * @param yfac - yfactor
	 * @param menlist - list of men
	 * @param width - width of the field
	 * @param height - height of the field
	 */
	private static void placeMen(int mencount, int xfac, int yfac, List<Man> menlist, int width , int height) {
		
		int distx = width / (mencount + 1);
		int disty = height / (mencount + 1);
		int x = 0;
		int y = 0;
		
		while(mencount > 0) {
			x += distx * xfac;
			y += disty * yfac;
			menlist.add(new Man(x, y));
			mencount--;
		}

	}
	
}
