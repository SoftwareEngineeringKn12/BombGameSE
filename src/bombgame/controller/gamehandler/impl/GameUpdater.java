package bombgame.controller.gamehandler.impl;

import java.util.ArrayList;
import java.util.List;
import bombgame.controller.ai.ManAI;
import bombgame.entities.IBomb;
import bombgame.entities.IExplosion;
import bombgame.entities.IMan;

public final class GameUpdater {

	/**
	 * GameHandler which is updated
	 */
	private GameHandler handler; 
	
	/**
	 * Creates a new Updater with the given GameHandler.
	 * @param handler - GameHandler
	 */
	protected GameUpdater(GameHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * Returns the GameHandler use by this GameUpdater.
	 * @return - GameHandler
	 */
	protected GameHandler getHandler() {
		return handler;
	}
	
	
	/**
	 * Checks if any Man-object from men wants to place a bomb. If so a Bomb-object is added.
	 */
	protected void placeBombs() {
		for (IMan man : handler.getMen()) {
			if (man.getPlaceBomb()) {
				handler.addObject(man.placeBomb());
			}
		}
	}
	
	/**
	 * Decrements the timer of the Bomb-object kept in bombs. If they explode the Bomb-object is removed
	 * and an Explosion-object is added.
	 */
	protected void updateBombs() {
		//new ArrayList is needed to remove a bomb during iteration
		List<IBomb> bs = new ArrayList<IBomb>(handler.getBombs());
		for (IBomb bomb : bs) {
			IExplosion expl = bomb.decrementTimer();
			if (expl != null) {
				handler.removeObject(bomb);
				handler.addObject(expl);
			}
		}
	}
	
	/**
	 * Decrements the duration of the explosions until they reach a duration of 0. Then they will be removed.
	 */
	protected void updateExplosion() {
		//new ArrayList is needed to remove a Explosionlist during iteration
		List<List<IExplosion>> explist = new ArrayList<List<IExplosion>>(handler.getExplosionList()); 
		for(List<IExplosion> explosion : explist) {
			IExplosion exp = explosion.get(0);
			exp.decrementTimer();
			
			if(exp.getTimer() <= 0) {
				handler.removeExplosionList(explosion);
			}
		}
	}
	
	/**
	 * Moves all Man-objects kept in men.
	 */
	protected void updateMen() {
		//new ArrayList is needed to remove a man during iteration
		List<IMan> m = new ArrayList<IMan>(handler.getMen());
		for (IMan man : m) {
			if(checkHit(man)) {
				handler.removeObject(man);
			} else {
				handler.getCalculator().moveMan(man);
			}
		}
	}
	
	/**
	 * Checks if the specified Man-object is hit by an Explosion. Returns true if the man is on a field
	 * which is already use by an Explosion-object, else returns false.
	 * @param man - Man-object to check
	 * @return - returns true if man is hit
	 */
	private boolean checkHit(IMan man) {
		return handler.getField()[man.getX()][man.getY()] instanceof IExplosion;
	}
	
	/**
	 * Updates all AIs on the Field.
	 */
	protected void updateAIs() {
		for(ManAI ai : handler.getAIs()) {
			ai.calcNextStep();
		}
	}
	
}
