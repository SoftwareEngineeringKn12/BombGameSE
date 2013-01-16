package bombgame.controller.ai;



/**
 * Has a selection Algorithm to move Objects to the closedlist.
 * @author Fabian
 *
 */
public interface ClosedListSelector {
	
	/**
	 * Returns true if the given Position should be moved to the closedlist.
	 * @param pos - Position
	 * @param pathcost - cost up to the given Position
	 * @return - true if Position should be moved to the closedlist
	 */
	boolean moveToClosedList(IPosition pos, int pathcost);
	
}
