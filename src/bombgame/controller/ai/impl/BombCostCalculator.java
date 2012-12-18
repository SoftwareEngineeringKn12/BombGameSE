package bombgame.controller.ai.impl;

import bombgame.controller.ai.ExtraCostCalculator;
import bombgame.controller.gamehandler.IGameHandler2D;
import bombgame.entities.IBomb;
import bombgame.entities.impl.Explosion;

/**
 * This class calculates the additional cost if a given Position is threatened by a Bomb.
 * @author Rookfighter
 *
 */
public final class BombCostCalculator implements ExtraCostCalculator{
	
	/**
	 * GameHandler that is observed by this CostCalculator
	 */
	private IGameHandler2D handler;
	
	/**
	 * additional cost if a Bomb blocks the way
	 */
	protected static final int BOMB_COST = 9;
	
	/**
	 * Creates a new BombCostCalculator observing the given GameHandler.
	 * @param handler - GameHandler tah is observed
	 */
	public BombCostCalculator(IGameHandler2D handler) {
		this.handler = handler;
	}
	
	
	/**
	 * Returns an extra cost if the position is in the range of a Bomb, else 0.
	 */
	public int calcExtraCost(Position pos) {
		if(isInBombRange(pos))	{
			return BOMB_COST;
		} else {
			
			return 0;
			
		}
	}
	
	/**
	 * Returns true if the given Position is probably in danger of being hit by an
	 * Explosion.
	 * @param pos - Cell that is to be proofed to be out of danger
	 * @return - true if the Position is threated by a Bomb
	 */
	private boolean isInBombRange(Position pos) {
		for(IBomb b : handler.getBombs()) {
			//simple and ineffective algorythm-> only checks radius
			int dx = Math.abs(pos.getX() - b.getX());
			int dy = Math.abs(pos.getY() - b.getY());
			if(dx <= Explosion.RANGE && dy <= Explosion.RANGE) {
				return true;
			}
		}
		return false;
	}

}
