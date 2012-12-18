package bombgame.controller.ai;

import bombgame.controller.ai.impl.Position;


/**
 * Instances of this class are searching a target e.g. for an AI.
 * @author Rookfighter
 *
 */
public interface TargetFinder {

	/**
	 * Searches and returns a target.
	 * @return - target Position
	 */
	Position searchTarget();
	
}
