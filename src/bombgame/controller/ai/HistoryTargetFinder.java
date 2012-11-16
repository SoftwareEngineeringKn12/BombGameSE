package bombgame.controller.ai;

import java.util.Random;

import bombgame.controller.ai.ManAI;
import bombgame.controller.ai.ManAI.Node;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;

public final class HistoryTargetFinder implements TargetFinder {

	
	private Random rand;
	
	private Position target;
	
	public HistoryTargetFinder() {
		rand = new Random();
	}
	
	@Override
	public Position searchTarget(ManAI ai) {
		
		ai.countDirections();
		
		//get direction with highest count
		int direction = getMaxIndex(ai.getDirectionCount());
		ai.setDirectionCount(direction, 0);
				
		//find suitable Node as Target
		Position enemy = new Position(ai.getFocusedEnemyX(), ai.getFocusedEnemyY());
		calculateTarget(enemy, direction, ai);
		
		return target;
	}
	
	
	/**
	 * Calculates the target by looking for a suitable Node in the given direction from
	 * the position (x/y)
	 * @param x - start x-coordinate
	 * @param y - start y-coordinate
	 * @param direction - searching direction
	 */
	private void calculateTarget(Position enemy, int direction, ManAI ai) {
		

		switch(direction) {
		
		case Man.UP:
			findTarget(enemy, 0, -1, ai);
			break;
			
		case Man.DOWN:
			findTarget(enemy, 0, 1, ai);
			break;
			
		case Man.RIGHT:
			findTarget(enemy, 1, 0, ai);
			break;
			
		case Man.LEFT:
			findTarget(enemy, -1, 0, ai);
			break;
		default:
			if(sum(ai.getDirectionCount()) <= 0) {
				target = enemy;
			} else {
				chooseRandomTargetDirection(enemy, ai);
			}
		}
		
	}
	
	
	/**
	 * Finds a target from the given Position (x/y) in the direction determined by xfac and yfac.
	 * If no suitable target is found, this method calls calculateDirection() with another 
	 * direction from the last unused coordinate.
	 * @param x - start x-coordinate
	 * @param y - start y-coordinate
	 * @param xfac - factor for the y-direction
	 * @param yfac - factor for the x-direction
	 */
	private void findTarget( Position start, int xfac, int yfac, ManAI ai) {
		GameObject[][] field = ai.getHandler().getField();
		
		int i = 1;
		int x_tmp = start.getX() + xfac * i;
		int y_tmp = start.getY() + yfac * i;
		
		while(y_tmp >= 0 &&  y_tmp < field[0].length &&
				x_tmp < field.length && x_tmp >= 0) {
			
			if(field[x_tmp][y_tmp] instanceof Wall) {
				//if way is blocked by Wall break the loop
				break;
			}
			
			//check if found node has same coordinates
			Node q = ai.getNodes().ceiling(ai.new Node(x_tmp, y_tmp, 0, null));
			if(q != null && q.getX() == x_tmp && q.getY() == y_tmp) {
				target = new Position(x_tmp, y_tmp);
				return;
			}
			i++;
			x_tmp = start.getX() + xfac * i;
			y_tmp = start.getY() + yfac * i;
			
		}
		//no suitable node found (Wall blocked or out of field)
		int direction = getMaxIndex(ai.getDirectionCount());
		ai.setDirectionCount(direction,0);
		calculateTarget(new Position(x_tmp - xfac, y_tmp - yfac), direction, ai);
		
	}
	
	/**
	 * This method calculates a random direction and calls calculateTraget() with the given
	 * x- and y-coordinates and the calculated direction.
	 * @param x - start x-coordinate
	 * @param y - start y-coordiante
	 */
	private void chooseRandomTargetDirection(Position pos, ManAI ai) {
		while(true) {
			int i = rand.nextInt(ManAI.NUMBER_OF_DIRECTIONS);
			if(i != Man.NO_DIR) {
				calculateTarget(pos, i, ai);
				return;
			}
		}
	}
	
	
	
	/**
	 * This method calculates and returns the index with the highest value from the given
	 * int array.
	 * @param ia - examined int array 
	 * @return - index of highest value in the array
	 */
	private int getMaxIndex(int[] ia) {
		int maxval = 0;
		int maxindex = 0;
		for(int i = 0; i < ia.length; i++) {
			if(ia[i] > maxval ) {
				maxval = ia[i];
				maxindex = i;
			}
		}
		return maxindex;
	}
	
	
	/**
	 * Sums up the values of the given int array.
	 * @param ia - examined int array
	 * @return - sum of all values
	 */
	private int sum(int[] ia) {
		int sum = 0;
		for(int i: ia) {
			sum+=i;
		}
		return sum;
	}

}
