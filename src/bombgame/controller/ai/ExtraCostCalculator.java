package bombgame.controller.ai;

import bombgame.controller.ai.impl.Position;


/**
 * Instances of this class calculate the extra costs for specific Positions.
 * @author Rookfighter
 *
 */
public interface ExtraCostCalculator {
	
	/**
	 * This method returns the extra costs for the given Position.
	 * @param pos - examined Position
	 * @return - extra cost
	 */
	int calcExtraCost(Position pos);
	
}
