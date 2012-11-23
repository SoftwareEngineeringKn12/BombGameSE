package bombgame.controller.ai;

import java.util.Deque;

public interface PathFinder {
	Deque<Position> calculatePath(Position start, Position target);
}
