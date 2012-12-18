package bombgame.controller.ai.impl;


import bombgame.controller.ai.ClosedListSelector;
import bombgame.controller.gamehandler.IGameHandler2D;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.Wall;

/**
 * This class decides if a given Position should be moved to the closedlist or not.
 * If a position is blocked by a Wall or is threatened by an Explosion it should be moved to
 * the closedlist.
 * @author Rookfighter
 *
 */
public final class AISelector implements ClosedListSelector{

	/**
	 * Observed GameHandler
	 */
	private final IGameHandler2D handler;
	
	/**
	 * Creates a Selector observing the given GameHandler.
	 * @param handler - GameHandler observed by this Selector
	 */
	public AISelector(final IGameHandler2D handler) {
		this.handler = handler;
	}
	
	/**
	 * Checks if the given Position is already in use. If the position is used by a Wall or an Explosion, which
	 * has a higher timer than the given pathcost return true. Else false.
	 */
	@Override
	public boolean moveToClosedList(Position pos, int pathcost) {
		//if Wall is blocking the way immediately put to closed list
		if(handler.getField()[pos.getX()][pos.getY()] instanceof Wall) {
				return true;
		}
				
		//if an Explosion is in the way
		if(handler.getField()[pos.getX()][pos.getY()] instanceof Explosion) {
			Explosion exp = (Explosion) handler.getField()[pos.getX()][pos.getY()];
			if(exp.getTimer() >= pathcost) {
				return true;
			}
					
				}
		return false;
	}

}
