package bombgame.controller.ai;

import java.util.Deque;


public interface PathFinder {
	Deque<IPosition> calculatePath(IPosition start, IPosition target);
}
