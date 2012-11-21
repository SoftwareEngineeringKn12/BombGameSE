package bombgame.controller.ai;

public interface ClosedListSelector {
	
	public boolean moveToClosedList(Position pos, int pathcost);
	
}
