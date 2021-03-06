package bombgame.controller.ai.impl;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import bombgame.controller.ai.IManAI;
import bombgame.controller.ai.IPosition;
import bombgame.controller.ai.PathFinder;
import bombgame.controller.ai.TargetFinder;
import bombgame.controller.ai.impl.NodeFinder.Node;
import bombgame.entities.IField;
import bombgame.entities.IMan;
import bombgame.entities.impl.Man;


/**
 * This class manages the behavior of a Man-object. It watches the movement of its enemies. It calculates
 * the shortest path to its target considering Explosions and Bombs in its way.
 * @author Rookfighter
 *
 */
public final class ManAI implements IManAI{
	
	
//################################################################################
//##################################### STATIC ###################################
//################################################################################
	
	/**
	 * difference between two refreshs
	 */
	public static final int REFRESH_RATE = 5;
	
	/**
	 * number of directions a Man can have
	 */
	public static final int NUMBER_OF_DIRECTIONS = 5;
	
	/**
	 * difference between two refocuses
	 */
	public static final int FOCUS_RATE = 15;
	
	/**
	 * if mode == this attribute the AI is in attack mode
	 */
	public static final int ATK_MODE = 0;
	
	/**
	 * if mode == this attribute the AI is in flee mode
	 */
	public static final int FLEE_MODE = 1;
	
	/**
	 * maximum history length
	 */
	public static final int HISTORYLENGTH = 4;
	
	/**
	 * format String
	 */
	protected static final String FORMAT = "] [";
	
//################################################################################
//################################# NON STATIC ###################################
//################################################################################
	
	/**
	 * Man-object controlled by AI
	 */
	private final IMan man;
	
	/**
	 * Man-object that is focused by this AI
	 */
	private IMan focusedEnemy;
	
	/**
	 * Handler wich handles this ManAI-object
	 */
	private final IField field;
	
	
	/**
	 * target Position of the AI
	 */
	private IPosition target;
	
	/**
	 * Path which was calculated by A*
	 */
	private Deque<IPosition> path;
	
	/**
	 * determines if Man-object should place a bomb at the target
	 */
	private boolean placebomb;
	
	/**
	 * turns until next refresh of path
	 */
	private int turns;
	
	/**
	 * history of the focused enemy is stored in this queue
	 */
	private final Queue<Integer> directionHistory;
	
	/**
	 * counts the directions from the history
	 */
	private final int[] directionCount;
	
	
	/**
	 * nodes of the field
	 */
	private final Node[] nodes;
	
	/**
	 * describes the mode of this AI
	 */
	private int mode;
	
	/**
	 * random generator
	 */
	private final Random rand;
	
	/**
	 * determines Algorithm to find the path
	 */
	private PathFinder pathfinder;
	
	/**
	 * determines Algorithm to find the target
	 */
	private TargetFinder targetfinder;
	
	
	/**
	 * Creates a ManAI-object controlling the specified Man-object and living in the specified GameHandler-object.
	 * @param man - Man-object controlled by AI
	 * @param handler - GameHandler in which AI acts
	 */
	public ManAI (final IMan man, final IField field) {
		this.man = man;
		this.field = field;
		this.rand = new Random();
		this.turns = 0;
		this.path = new LinkedList<IPosition>();
		this.directionHistory = new LinkedList<Integer>();
		this.directionCount = new int[NUMBER_OF_DIRECTIONS];
		this.nodes = NodeFinder.findAllNodes(field);
		this.pathfinder = new PathFinderAStar(field, new BombCostCalculator(field), new AISelector(field));
		this.targetfinder = new HistoryTargetFinder(this);
		
	}
	
	
	/**
	 * Calculates the next step of the Man-object, including pathfinding, setting the Man-objects direction and
	 * placing bombs.
	 */
	public void calcNextStep() {
		incrementTurns();
		
		//if target is reached 
		if(checkTargetReached()) {
			//no movement in this round anymore
			return;
		}
		
		//adds next history value if a Man is focused
		updateHistory();
		
		if(path.isEmpty()) {
			if(mode == ATK_MODE) {
				if(focusedEnemy == null) {
					return;
				}
				//set historytargetfinder
				if(targetfinder == null ) {
					targetfinder = new HistoryTargetFinder(this);
				}
				placebomb = true;
			} else if ( mode == FLEE_MODE) {
				if(targetfinder == null ) {
					targetfinder = new FleeTargetFinder(this);
				}
				placebomb = false;
			} else {
				return;
			}
			target = targetfinder.searchTarget();
		}
		
		Position start = new Position(man.getX(), man.getY());
		path = pathfinder.calculatePath(start, target);
		//this only happens if the target is not reachable
		if(path.isEmpty()) {
			return;
		}
		
		path.pop();
		
		calcManDirection();
		
	}
	
	
	
	 
	/**
	 * Calls addHistoryValue() with the current direction of the focused enemy.
	 * If focused enemy is null the method does nothing.
	 */
	protected void updateHistory() {
		if(focusedEnemy != null) {
			addHistoryValue(focusedEnemy.getDirection());
		}
	}
		
		
	/**
	 * Adds the given value to directionhistory. If the size of directionHistory
	 * is bigger/equals than HISTORYLENGTH, the oldest entry is removed.
	 * @param value
	 */
	protected void addHistoryValue(int value) {
		if(directionHistory.size() >= HISTORYLENGTH) {
			directionHistory.poll();
		}
		
		directionHistory.offer(value);
		
	}
		
	/**
	 * Focuses a new enemy randomly from the men-list from handler.
	 */
	protected void focusEnemy() {
		focusedEnemy = null;
		directionHistory.clear();
		int size = field.getMen().size();
		while(size > 1) {
			int i = rand.nextInt(size);
			IMan m = field.getMen().get(i);
			if( m != this.man) {
				focusedEnemy = m;
				return;
			}
		}
		focusedEnemy = man;
	}
	
	/**
	 * Increments turns. If turns is a multiple of REFRESH_RATE path is cleared.
	 * If turn is a multiple of FOCUS_RATE, a new enemy is focused.
	 */
	protected void incrementTurns() {
		if(turns % REFRESH_RATE == 0) {
			path.clear();
		}
		if(turns % FOCUS_RATE == 0) {
			focusEnemy();
		}
		turns++;
		
	}
	
	
	/**
	 * Checks if the coordinates of the target Cell and the ones of the Man-object are the same.
	 * If so, target is set to null and placebomb of the Man-object is set to the AI's placebomb value.
	 * @return - returns true if target was reached
	 */
	protected boolean checkTargetReached() {
		
		if(target != null && man.getX() == target.getX() && man.getY() == target.getY()) {
			target = null;
			targetfinder = null;
			man.setPlaceBomb(placebomb);
			man.setDirection(Man.NO_DIR);
			placebomb = false;
			if(mode == ATK_MODE) {
				mode = FLEE_MODE;
			} else if(mode == FLEE_MODE) {
				mode = ATK_MODE;
			} else {
				mode = ATK_MODE;
			}
			return true;
		}
		return false;
		
	}
	
	
	/**
	 * Sets the direction of the controlled Man-object to the calculated value. The value is calculated
	 * using the next cell from path and the current location of the Man-object. If path is empty, this
	 * method does nothing.
	 */
	protected void calcManDirection() {
		
		//get Position on top of stack
		IPosition pos = path.pop();
		
		int dx = pos.getX() - man.getX();
		int dy = pos.getY() - man.getY();
		
		//decide which direction should be taken
		if(dx == 1) {
			
			man.setDirection(Man.RIGHT);
			
		} else if(dx == -1) {
			
			man.setDirection(Man.LEFT);
			
		} else if(dy == 1) {
			
			man.setDirection(Man.DOWN);
			
		} else if( dy == -1) {
			
			man.setDirection(Man.UP);
			
		} else {
			man.setDirection(Man.NO_DIR);
		} 
	}
	
	
	/**
	 * Returns an array of the Nodes in the GameHandler watched by this AI.
	 * @return - array of Nodes
	 */
	protected Node[] getNodes() {
		return nodes;
	}
	
	
	/**
	 * Returns the GameHandler in which this AI plays.
	 * @return
	 */
	protected IField getField() {
		return field;
	}
	
	
	/**
	 * Returns the x-coordinate of the Man which is currently focused by this AI.
	 * @return - x-coordinate of the focused Man
	 */
	protected int getFocusedEnemyX() {
		return focusedEnemy.getX();
	}
	
	
	/**
	 * Returns the y-coordinate of the Man which is currently focused by this AI.
	 * @return - y-coordinate of the focused Man
	 */
	protected int getFocusedEnemyY() {
		return focusedEnemy.getY();
	}
	
	
	/**
	 * Sets the directionCount on the specified index to the specified value.
	 * @param index - index at which the value is set
	 * @param value - new value 
	 */
	protected void setDirectionCount( int index, int value) {
		directionCount[index] = value;
	}
	
	
	/**
	 * Counts the direction from the direction history.
	 */
	protected void countDirections() {
		//initialize directionCount with 0
		Arrays.fill(directionCount,  0);
		
		
		for( Integer i : directionHistory) {
			directionCount[i]++;
		}
	}
	
	
	/**
	 * Returns the array of the Direction count. The index equates the direction.
	 * @return - array of direction count
	 */
	protected int[] getDirectionCount() {
		return directionCount;
	}
	
	
	/**
	 * Sets the focused Enemy of this AI to the given Man.
	 * @param man - focused enemy
	 */
	protected void setFocusedEnemy(final IMan man) {
		focusedEnemy = man;
	}
	
	/**
	 * Returns the currently focused Man.
	 * @return - focused enemy
	 */
	public IMan getFocusedEnemy() {
		return focusedEnemy;
	}
	
	/**
	 * Returns the current target Position.
	 * @return - target Position
	 */
	protected IPosition getTarget() {
		return target;
	}
	
	/**
	 * Sets the amount of turns to the given value.
	 * @param turns - new amount of turns
	 */
	protected void setTurns(int turns) {
		this.turns = turns;
	}
	
	/**
	 * Sets the target Position to the given Position.
	 * @param target - new target Position
	 */
	protected void setTarget( final Position target) {
		this.target = target;
	}
	
	/**
	 * Returns true if the ai wants to place a bomb at the target.
	 * @return - wants to place a bomb
	 */
	protected boolean getPlaceBomb() {
		return placebomb;
	}
	
	/**
	 * Sets the targetfinder to the given one.
	 * @param tf - targetfinder
	 */
	protected void setTargetFinder(final TargetFinder tf) {
		targetfinder = tf;
	}
	
	/**
	 * Returns the Man controlled by this AI.
	 * @return - Man controlled by AI
	 */
	public IMan getMan() {
		return man;
	}
	
	/**
	 * Returns a Deque of the current path.
	 * @return - current path
	 */
	protected Deque<IPosition> getPath() {
		return path;
	}
	
	/**
	 * Sets the mode of this AI to the given value.
	 * @param mode - new mode
	 */
	protected void setMode(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Returns the String-form of a ManAI-object.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("-> AI: ");
		sb.append("[").append(man.getX()).append(FORMAT).append(man.getY()).append("]");
		sb.append(" T: ");
		if(target == null) {
			sb.append("NULL");
		} else {
			sb.append("[").append(target.getX()).append(FORMAT).append(target.getY()).append("]");
		}
		sb.append(" F: ");
		if(focusedEnemy == null) {
			sb.append("NULL");
		} else {
			sb.append("[").append(focusedEnemy.getX()).append(FORMAT).append(focusedEnemy.getY()).append("]");
		}
		sb.append(" Turn: ").append(turns);
		sb.append(" Direction: ").append(man.getDirection());
		return sb.toString();
	}

}
