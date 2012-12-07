package bombgame.controller.gamehandler.impl;

import java.util.ArrayList;
import java.util.List;

import bombgame.controller.ai.ManAI;
import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.Man;

public final class GameUpdater {

	
	private GameHandler handler; 
	
	protected GameUpdater(GameHandler handler) {
		this.handler = handler;
	}
	
	protected GameHandler getHandler() {
		return handler;
	}
	
	
	/**
	 * Checks if any Man-object from men wants to place a bomb. If so a Bomb-object is added.
	 */
	protected void placeBombs() {
		for (Man man : handler.getMen()) {
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
		List<Bomb> bs = new ArrayList<Bomb>(handler.getBombs());
		for (Bomb bomb : bs) {
			Explosion expl = bomb.decrementTimer();
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
		List<List<Explosion>> explist = new ArrayList<List<Explosion>>(handler.getExplosionList()); 
		for(List<Explosion> explosion : explist) {
			Explosion exp = explosion.get(0);
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
		List<Man> m = new ArrayList<Man>(handler.getMen());
		for (Man man : m) {
			if(checkHit(man)) {
				handler.removeObject(man);
			} else {
				handler.getGameCalculator().moveMan(man);
			}
		}
	}
	
	/**
	 * Checks if the specified Man-object is hit by an Explosion. Returns true if the man is on a field
	 * which is already use by an Explosion-object, else returns false.
	 * @param man - Man-object to check
	 * @return - returns true if man is hit
	 */
	private boolean checkHit(Man man) {
		return handler.getField()[man.getX()][man.getY()] instanceof Explosion;
	}
	
	
	protected void updateAIs() {
		for(ManAI ai : handler.getAIs()) {
			ai.calcNextStep();
		}
	}
	
}
