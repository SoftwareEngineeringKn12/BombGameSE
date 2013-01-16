package bombgame.controller.ai;


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
	IPosition searchTarget();
	
}
