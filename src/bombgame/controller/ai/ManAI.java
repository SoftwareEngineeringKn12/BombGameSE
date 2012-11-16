package bombgame.controller.ai;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeSet;

import bombgame.controller.GameHandler;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;

public final class ManAI {
	
	/**
	 * difference between two refreshs
	 */
	public static final int REFRESH_RATE = 5;
	
	public static final int NUMBER_OF_DIRECTIONS = 5;
	
	/**
	 * difference between two refocuses
	 */
	public static final int FOCUS_RATE = 15;
	
	
	public static final int ATK_MODE = 0;
	
	public static final int FLEE_MODE = 1;
	
	/**
	 * Man-object controlled by AI
	 */
	private Man man;
	
	/**
	 * Man-object that is focused by this AI
	 */
	private Man focusedEnemy;
	
	/**
	 * Handler wich handles this ManAI-object
	 */
	private GameHandler handler;
	
	
	/**
	 * target Cell of the AI
	 */
	private Position target;
	
	/**
	 * Path which was calculated by A*
	 */
	private Deque<Position> path;
	
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
	private Deque<Integer> directionHistory;
	
	/**
	 * counts the directions from the history
	 */
	private int[] directionCount;
	
	/**
	 * maximum history length
	 */
	public static final int HISTORYLENGTH = 4;
	
	/**
	 * nodes of the field
	 */
	private NavigableSet<Node> nodes;
	
	/**
	 * describes the mode of this AI
	 */
	private int mode;
	
	/**
	 * random generator
	 */
	private Random rand;
	
	private PathFinder pathfinder;
	
	private TargetFinder targetfinder;
	
	
	/**
	 * Creates a ManAI-object controlling the specified Man-object and living in the specified GameHandler-object.
	 * @param man - Man-object controlled by AI
	 * @param handler - GameHandler in which AI acts
	 */
	public ManAI (Man man, GameHandler handler) {
		this.man = man;
		this.handler = handler;
		directionHistory = new LinkedList<Integer>();
		directionCount = new int[NUMBER_OF_DIRECTIONS];
		nodes = new TreeSet<Node>();
		pathfinder = new PathFinderAStar(handler);
		targetfinder = new HistoryTargetFinder();
		findAllNodes();
	}
	
	
	/**
	 * Calculates the next step of the Man-object, including pathfinding, setting the Man-objects direction and
	 * placing bombs.
	 */
	public void calcNextStep() {
		incrementTurns();
		
		if(checkTargetReached()) {
			return;
		}
		
		updateHistory();
		
		if(path.isEmpty()) {
			//System.out.println("path is empty");
			if(mode == ATK_MODE) {
				target = targetfinder.searchTarget(this);
			} else if ( mode == FLEE_MODE) {
				
			}
		}
		
		path = pathfinder.calculatePath(new Position(man.getX(), man.getY()), target);
		
		if(path.isEmpty()) {
			return;
		}
		
		Position pos = path.pop();
		if(pos.getX() != man.getX() || pos.getY() != man.getY()) {
			path.clear();
			return;
		}
		calcManDirection();
		
		
	}
	
	
	/**
	 * Searches through the field given by handler and calls for each coordiante
	 * createNode().
	 */
	private void findAllNodes() {
		for(int i = 0; i < handler.getField().length; i++) {
			for(int j = 0; j < handler.getField()[0].length; j++) {
				createNode(i,j);
			}
		}
	}
	
	/**
	 * Creates a new Node for the given coordinates considering the free directions
	 * from (x/y) and adds it to nodes.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	private void createNode(int x, int y) {
		GameObject field[][] = handler.getField();
		if(field[x][y] instanceof Wall) {
			return;
		}
		
		boolean direction[] = { false, false, false, false };
		int directions = 0;
		
		if(x + 1 < field.length && !(field[x + 1][y] instanceof Wall)) {
			direction[Node.RIGHT] = true;
			directions++;
		}
		if(x - 1 >= 0 && !(field[x - 1][y] instanceof Wall)) {
			direction[Node.LEFT] = true;
			directions++;
		}
		if(y + 1 < field[0].length && !(field[x][y + 1] instanceof Wall)) {
			direction[Node.DOWN] = true;
			directions++;
		}
		if(y - 1 >= field[0].length && !(field[x][y - 1] instanceof Wall)) {
			direction[Node.UP] = true;
			directions++;
		}
		Node q = new Node(x, y, directions, direction);
		addNode(q);
		
	}
	
	
	/**
	 *Adds the given node to nodes, if it has more than 1 free direction and if it has 2 directions, only they
	 *are not opposing.
	 */
	 private void addNode(final Node q) {
	 
		
		if(q.directions <= 1) {
			return;
		} else if(q.directions == 2) {
			if( ( q.direction[Node.UP] && q.direction[Node.DOWN] ) || (q.direction[Node.RIGHT] && q.direction[Node.LEFT]) ) {
				return;
			}
			
		}
		
		nodes.add(q);
	}
	 
	/**
	 * Calls addHistoryValue() with the current direction of the focused enemy.
	 * If focused enemy is null the method does nothing.
	 */
	private void updateHistory() {
		if(focusedEnemy != null) {
			addHistoryValue(focusedEnemy.getDirection());
		}
	}
		
		
	/**
	 * Adds the given value to directionhistory. If the size of directionHistory
	 * is bigger/equals than HISTORYLENGTH, the oldest entry is removed.
	 * @param value
	 */
	private void addHistoryValue(int value) {
		if(directionHistory.size() >= HISTORYLENGTH) {
			directionHistory.poll();
		}
		
		directionHistory.offer(value);
		
	}
		
	/**
	 * Focuses a new enemy randomly from the men-list from handler.
	 */
	private void focusEnemy() {
		focusedEnemy = null;
		directionHistory.clear();
		int size = handler.getMen().size();
		while(size > 0) {
			int i = rand.nextInt(size);
			Man m = handler.getMen().get(i);
			if( m != this.man) {
				focusedEnemy = m;
				return;
			}
		}
		
	}
	
	/**
	 * Increments turns. If turns is a multiple of REFRESH_RATE path is cleared.
	 * If turn is a multiple of FOCUS_RATE, a new enemy is focused.
	 */
	private void incrementTurns() {
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
	private boolean checkTargetReached() {
		
		if(target != null && man.getX() == target.getX() && man.getY() == target.getY()) {
			target = null;
			man.setPlaceBomb(placebomb);
			man.setDirection(Man.NO_DIR);
			placebomb = false;
			return true;
		}
		return false;
		
	}
	
	
	/**
	 * Sets the direction of the controlled Man-object to the calculated value. The value is calculated
	 * using the next cell from path and the current location of the Man-object. If path is empty, this
	 * method does nothing.
	 */
	private void calcManDirection() {
		
		Position pos = path.peek();
		
		if( pos == null) {
			return;
		}
		
		int dx = pos.getX() - man.getX();
		int dy = pos.getY() - man.getY();
		
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
	
	public NavigableSet<Node> getNodes() {
		return nodes;
	}
	public GameHandler getHandler() {
		return handler;
	}
	
	public int getFocusedEnemyX() {
		return focusedEnemy.getY();
	}
	
	public int getFocusedEnemyY() {
		return focusedEnemy.getX();
	}
	
	public void setDirectionCount( int index, int value) {
		directionCount[index] = value;
	}
	
	public void countDirections() {
		//initialize directionCount with 0
		Arrays.fill(directionCount,  0);
				
		for( Integer i : directionHistory) {
			directionCount[i]++;
		}
	}
	
	public int[] getDirectionCount() {
		return directionCount;
	}
	
	
//############################################################################################
//############################################################################################
	
	/**
	 * This class is used for marking places in the field, which don't lead into a dead-end.
	 * @author Fabian
	 *
	 */
	class Node implements Comparable<Node> {
		
		private final int x;
		private final int y;
		
		private final int directions;
		
		private final boolean[] direction;
		
		private static final int UP = 0;
		private static final int DOWN = 1;
		private static final int LEFT = 2;
		private static final int RIGHT = 3;
		
		private static final int NODE_DIRECTIONS = 4;
		
		/**
		 * Creates a new Node-object with the given coordinates, the given count of directions and
		 * the given array of directions.
		 * @param x - x-coordinate
		 * @param y - y-coordiante
		 * @param directions - amount of free directions
		 * @param direction - holds boolean values wether the direction is free or not
		 */
		public Node(int x, int y, int directions, boolean[] direction) {
			this.x = x;
			this.y = y;
			this.directions = directions;
			if(direction != null) {
				this.direction = new boolean[NODE_DIRECTIONS];
				System.arraycopy(direction, 0, this.direction, 0, NODE_DIRECTIONS);
			} else {
				this.direction = null;
			}
		}
		
		/**
		 * Creates a new Node-object with the given coordinates, the given count of directions and
		 * the given up,down,left,right directions.
		 * @param x - x-coordinate
		 * @param y - y-coordinate
		 * @param directions - amount of free directions
		 * @param up - direction up is free or not
		 * @param down - direction down is free or not
		 * @param left - direction left is free or not
		 * @param right - direction right is free or not
		 */
		public Node(int x, int y, int directions, boolean up, boolean down, boolean left, boolean right) {
			this.x = x;
			this.y = y;
			this.directions = directions;
			this.direction = new boolean[NODE_DIRECTIONS];
			direction[UP] = up;
			direction[DOWN] = down;
			direction[LEFT] = left;
			direction[RIGHT] = right;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		/**
		 * Compares this Node with the given for equality.
		 */
		@Override
		public boolean equals(Object o) {
			if( o instanceof Node) {
				Node n = (Node) o;
				return (this.x == n.x && this.y == n.y);
			} else {
				return false;
			}
		}

		/**
		 * Compares this Node with the given for more than, less than or equals.
		 */
		@Override
		public int compareTo(Node q) {
			if( this.equals(q)) {
				return 0;
			}
			if(this.x + this.y + this.directions > q.x + q.y + q.directions) {
				return 1;
			} else {
				return -1;
			}
		}
		
	}
}
