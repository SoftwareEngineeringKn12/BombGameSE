package bombgame.controller.ai;

import java.util.Deque;

import bombgame.controller.ai.impl.Position;


public interface PathFinder {
	Deque<Position> calculatePath(Position start, Position target);
}
