package bombgame.controller.gamehandler.impl;

import java.util.ArrayList;
import java.util.List;
import bombgame.controller.mazegen.ICell;
import bombgame.controller.mazegen.IMazeGen;
import bombgame.controller.mazegen.impl.MazeGen;
import bombgame.entities.IBomb;
import bombgame.entities.IExplosion;
import bombgame.entities.IField;
import bombgame.entities.IGameObject;
import bombgame.entities.IMan;
import bombgame.entities.IWall;
import bombgame.entities.impl.Explosion;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Man;
import bombgame.entities.impl.Wall;

public final class GameCalculator {
	
	/**
	 * explosion direction up
	 */
	private static final int EXP_UP = 3;
	
	/**
	 * explosion direction down
	 */
	private static final int EXP_DOWN = 2;
	
	/**
	 * explosion direction left
	 */
	private static final int EXP_LEFT = 1;
	
	/**
	 * explosion direction right
	 */
	private static final int EXP_RIGHT = 0;
	
	/**
	 * The GameHandler (Main controller logic).
	 */
	private GameHandler handler;
	
	protected GameCalculator(GameHandler handler) {
		this.handler = handler;
	}

	/**
	 * This method tries to move the specified Man-object to the direction given by man.getDirection(). This is
	 * only possible if the aimed coordinate is not already used by a Wall-object or is out of the range of the field.
	 * @param man - Man-object that should move
	 */
	protected void moveMan( final IMan man) {
		IField field = handler.getField();
		
		switch(man.getDirection()) {
		
		case Man.NO_DIR:
			break;
			
		case Man.UP:
			if( man.getY() != 0 && !(field.getField()[man.getX()][man.getY() - 1] instanceof IWall) ) {
				
				if(field.getField()[man.getX()][man.getY()] == man) {
					//only replace with null if man is the user of the field
					field.getField()[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX(), man.getY() - 1);
				
				if(field.getField()[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field.getField()[man.getX()][man.getY()] = man;
				}
				
			}
			break;
			
		case Man.DOWN:
			if( man.getY() != (field.getHeight() - 1) && !(field.getField()[man.getX()][man.getY() + 1] instanceof IWall) ) {
				
				if(field.getField()[man.getX()][man.getY()] == man) {
					//only replace with null if man is the user of the field
					field.getField()[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX(), man.getY() + 1);
				
				if(field.getField()[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field.getField()[man.getX()][man.getY()] = man;
				}
				
			}
			break;
			
		case Man.LEFT:
			if( man.getX() != 0 && !(field.getField()[man.getX() - 1][man.getY()] instanceof IWall) ) {
				
				if(field.getField()[man.getX()][man.getY()] == man) {
					//only replace with null if man is the user of the field
					field.getField()[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX() - 1, man.getY());
				
				if(field.getField()[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field.getField()[man.getX()][man.getY()] = man;
				}
				
			}
			break;
			
		case Man.RIGHT:
			if( man.getX() != (field.getWidth() - 1) && !(field.getField()[man.getX() + 1][man.getY()] instanceof IWall) ) {
				
				if(field.getField()[man.getX()][man.getY()] == man) {
					//only replace with null if man is the user of the field
					field.getField()[man.getX()][man.getY()] = null;
				}
				man.setPos(man.getX() + 1, man.getY());
				
				if(field.getField()[man.getX()][man.getY()] == null) {
					//if field is already used e.g. by a bomb or explosion
					field.getField()[man.getX()][man.getY()] = man;
				}
				
			}
			break;
		}
	}
	
	/**
	 * Calculates the spread of the specified Explosion-object.
	 * @param explosion - source of explosion
	 * @return - ArrayList of all Explosions included in the spread
	 */
	protected List<IExplosion> calculateExplosion(final IExplosion explosion) {
		
		List<IExplosion> list = new ArrayList<IExplosion>();
		list.add(explosion);
		
		boolean free[] = {true, true, true, true};
		for(int i = 1; i <= Explosion.RANGE; i++) {
				
			//right
			free[EXP_RIGHT] = nextExplosion(explosion.getX() + i, explosion.getY(),free[EXP_RIGHT], list);
			
			//left
			free[EXP_LEFT] = nextExplosion(explosion.getX() - i, explosion.getY(),free[EXP_LEFT], list);
			
			//down
			free[EXP_DOWN] = nextExplosion(explosion.getX(), explosion.getY() + i,free[EXP_DOWN], list);
			
			//up
			free[EXP_UP] = nextExplosion(explosion.getX(), explosion.getY() - i,free[EXP_UP], list);
		}
		
		return list;
		
	}
	
	/**
	 * Creates a new Explosion-object with the specified coordinates, adds that object to the specified ArrayList
	 * and returns true, if the coordinate is not blocked by a Wall-object and free is true.
	 * Else it returns false.
	 * @param x - x-coordinate of the new explosion
	 * @param y - y coordinate of the new explosion
	 * @param free - determines if the explosion is sill spreading
	 * @param list - list to which explosion will be added
	 * @return - returns true if Explosion-object was successfully created
	 */
	protected boolean nextExplosion(int x, int y, boolean free, List<IExplosion> list) {
		IField field = handler.getField();
		boolean tmp = free && x < field.getWidth() && x >= 0 && y < field.getHeight();
		if( tmp && y >= 0 && !(field.getField()[x][y] instanceof IWall)) {
			
			list.add(new Explosion(x, y));
			
			
			if(field.getField()[x][y] instanceof IBomb) {
				IExplosion exp = null;
				IBomb bomb = (IBomb) field.getField()[x][y];
				while(exp == null) {
					exp = bomb.decrementTimer();
				}
				handler.removeObject(bomb);
				handler.addObject(exp);
			}
			
			return true;
			
		} else {
			
			return false;
			
		}
	}
	
	/**
	 * Creates with MazeGen a random generated, non-perfect maze.
	 * This maze is stored in field as the playing field.
	 * If the parameters are both 0, then the FIELDWIDTH and FIELDHEIGHT
	 * are used.
	 * @param width - width of field
	 * @param height - height of field
	 */
	protected void initializeField(final int width, final int height) {
		IMazeGen generator = new MazeGen(width, height);
		IGameObject[][] field = new GameObject[width][height];

		generator.genNonPerfectMaze();
		ICell[][] cellArray = generator.getMaze();
		
		int xLength = cellArray.length;
		int yLength = cellArray[0].length;
		
		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < xLength; j++) {
				if (cellArray[j][i].getWall()) {
					field[j][i] = new Wall(j, i);
				} else {
					field[j][i] = null;
				}
			}
		}
		
		handler.setField(field);
	}
}
