package bombgame.controller.ai;

public interface ClosedListSelector {
	
	boolean moveToClosedList(Position pos, int pathcost);
	
}
