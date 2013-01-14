package bombgame.controller.ai.impl;

import bombgame.controller.ai.IPosition;
import bombgame.controller.ai.TargetFinder;
import bombgame.entities.IField;
import bombgame.entities.IWall;

public class FleeTargetFinder implements TargetFinder {

	/**
	 * Flee range.
	 */
	private static final int RANGE = 10;
	
	/**
	 * Ai that wants to flee.
	 */
	private ManAI ai;
	
	/**
	 * Creates FleeTargetFinder for the given AI.
	 * @param ai - ai searching a target
	 */
	public FleeTargetFinder(ManAI ai) {
		this.ai = ai;
	}
	@Override
	public IPosition searchTarget() {
		int xfac = ai.getMan().getX() - ai.getFocusedEnemyX();
		int yfac = ai.getMan().getY() - ai.getFocusedEnemyY();
		if(xfac != 0) {
			xfac = xfac / Math.abs(ai.getMan().getX() - ai.getFocusedEnemyX());
		} else {
			xfac = 1;
		}
		if(yfac != 0) {
			yfac = yfac / Math.abs(ai.getMan().getY() - ai.getFocusedEnemyY());
		} else {
			yfac = 1;
		}
		
		IPosition pos = search(xfac, yfac);
		
		return pos;
	}
	
	/**
	 * Calculates Manhattendistance.
	 * @param sx - start-x
	 * @param sy - start-y
	 * @param gx - goal-x
	 * @param gy - goal-y
	 * @return distance
	 */
	protected int getDist(int sx, int sy, int gx, int gy) {
		return Math.abs(sx - gx) + Math.abs(sy - gy);
	}
	
	/**
	 * Searches Position in the given direction.
	 * @param xfac
	 * @param yfac
	 * @return
	 */
	protected IPosition search(int xfac, int yfac) {
		IField field = ai.getField();
		int x = ai.getMan().getX();
		int y = ai.getMan().getY();

		
		while(getDist( ai.getMan().getX(), ai.getMan().getY(), x, y) < RANGE && isInField(x + xfac,y + yfac,field)) {
			x += xfac;
			y += yfac;
		}
		
		boolean swap = true;
		while(field.getField()[x][y] instanceof IWall) {
			if(swap) {
				x-= xfac;
				swap = false;
			}else {
				y-= yfac;
				swap = true;
			}
		}
		
		return new Position(x,y);
		
		
	}
	
	/**
	 * Checks if Position is still on the field.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param handler - handler that has the field
	 * @return 
	 */
	protected boolean isInField(int x, int y, IField field) {
		return x >= 0 && x < field.getWidth() && y >= 0 && y < field.getHeight();
	}

}
