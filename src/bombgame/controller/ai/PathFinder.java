package bombgame.controller.ai;

import java.util.Deque;

public interface PathFinder {
	public Deque<Position> calculatePath(Position start, Position target);
}
