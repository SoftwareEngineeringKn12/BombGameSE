package bombgame.controller;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.GameObject;
import bombgame.entities.Man;
import bombgame.entities.Wall;

/**
 * 
 * @author Fabian
 *
 */
public class ManAI {

	/**
	 * Extra-cost for cells threatened by a Bomb-object
	 */
	private static final int B_COST = 9;
	
	/**
	 * difference between two refreshs
	 */
	public static final int REFRESH_RATE = 5;
	
	private static final int NUMBER_OF_DIRECTIONS = 5;
	
	/**
	 * difference between two refocuses
	 */
	public static final int FOCUS_RATE = 15;
	
	
	public static final int ATK_MODE = 0;
	
	public static final int FLEE_MODE = 1;
	
	private static final String FORMAT = "] [";
	
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
	 * List of closed Cells
	 */
	private Set<Cell> closedlist;
	
	/**
	 * List of known cells
	 */
	private PriorityQueue<Cell> openlist;
	
	/**
	 * Maps isclosed statement to field positions
	 */
	private boolean inclosed[][];
	
	/**
	 * target Cell of the AI
	 */
	private Cell target;
	
	/**
	 * Path which was calculated by A*
	 */
	private Deque<Cell> path;
	
	/**
	 * determines if Man-object should place a bomb at the target
	 */
	private boolean placebomb;
	
	/**
	 * turns until next refresh of path
	 */
	private int turns;
	
	/**
	 * random generator
	 */
	private Random rand;
	
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
	 * Creates a ManAI-object controlling the specified Man-object and living in the specified GameHandler-object.
	 * @param man - Man-object controlled by AI
	 * @param handler - GameHandler in which AI acts
	 */
	public ManAI (Man man, GameHandler handler) {
		this.man = man;
		this.handler = handler;
		openlist = new PriorityQueue<Cell>();
		closedlist = new TreeSet<Cell>();
		inclosed = new boolean[handler.getField().length] [handler.getField()[0].length];
		path = new LinkedList<Cell>();
		rand = new Random();
		directionHistory = new LinkedList<Integer>();
		directionCount = new int[NUMBER_OF_DIRECTIONS];
		nodes = new TreeSet<Node>();
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
				searchTargetAttack();
			} else if ( mode == FLEE_MODE) {
				
			}
		}
		
		calculatePathToTarget();
		
		if(path.isEmpty()) {
			return;
		}
		
		Cell c = path.pop();
		if(c.x != man.getX() || c.y != man.getY()) {
			path.clear();
			return;
		}
		calcManDirection();
		
		
	}
	
	
	/**
	 * Calculates the shortest and most harmless way to the Cell given in target with A*.
	 * If target is null the Method does nothing.
	 */
	private void calculatePathToTarget() {
		if(target == null) {
			return;
		}
		
		clearLists();		
		
		addOpenList(new Cell(man.getX(), man.getY(), null));
		
		//search path
		while(!openlist.isEmpty()) {
			
			Cell c = openlist.poll();
			//System.out.println("-> Popped: " + c);
			addClosedList(c);
			
			if(inclosed[target.x][target.y]) {
				//System.out.println("=> found Target " + c);
				target = c;
				break;
			}
			
			addNeighbours(c);
			
		}
		
		//put path in path deque
		pushOnPath();
		
		
	}
	
	
	/**
	 * Adds all neighbours of the specified Cell-object to the openlist.
	 * @param c - Cell-object whose neighbours are examined 
	 */
	private void addNeighbours(Cell c) {
		
		if(c.x + 1 < inclosed.length && !(inclosed[c.x+1][c.y])) {
			
			addOpenList(new Cell(c.x+1, c.y, c));
			
		}
		
		if(c.x - 1 >= 0 && !(inclosed[c.x-1][c.y])) {
			
			addOpenList(new Cell(c.x-1, c.y, c));
			
		}
		
		if(c.y + 1 < inclosed[0].length && !(inclosed[c.x][c.y+1])) {
			
			addOpenList(new Cell(c.x, c.y+1,c));
			
		}
		
		if(c.y - 1 >= 0 && !(inclosed[c.x][c.y-1]) ) {
			
			addOpenList(new Cell(c.x, c.y-1, c));
			
		}
	}
	
	
	/**
	 * Adds the specified Cell-object to the closedlist.
	 * @param c - Cell-object that will bei added to the closedlist
	 */
	private void addClosedList(Cell c) {
		if(c == null) {
			return;
		}
		inclosed[c.x][c.y] = true;
		closedlist.add(c);
		
	}
		
	
	/**
	 * Adds the specified Cell-Object to the openlist.
	 * If the given Cell points to a Wall-object, the Cell will be immediately moved to the closedlist.
	 * If the given Cell points to an Explosion and the pathcost up to this Cell is lower than the timer
	 * of the Explosion-object, the given Cell will be immediately moved to the closedlist.
	 * If the given Cell is in the Range of a Bomb, its overall cost will be increased by B_COST.
	 * If the given Cell is equals to one in the openlist and has lower overall cost than the one already
	 * in the list, the cost and its predecessor will be overwritten.
	 * @param c - Cell-object that will be added to the openlist.
	 */
	private void addOpenList(Cell c) {
		if( c == null ){
			return;
		}
		
		
		c.calcCosts();
		
		//if is blocking the way immediately put to closed list
		if(handler.getField()[c.x][c.y] instanceof Wall) {
			//System.out.println(c + " is blocking");
			addClosedList(c);
			return;
		}
		
		//if an explosion is in the way
		if(handler.getField()[c.x][c.y] instanceof Explosion) {
			Explosion exp = (Explosion) handler.getField()[c.x][c.y];
			if(exp.getTimer() >= c.pathcost) {
				//System.out.println(c + " is Explosion in way");
				addClosedList(c);
				return;
			}
			
		}
		
		//if is in danger of bomb increase cost
		if(isInBombRange(c)) {
			c.cost += B_COST;
		}
		
		
		
		for(Cell tmp : openlist) {
			if(tmp.equals(c)) {
				//System.out.println(c + " already in openlist");
				if(c.cost < tmp.cost) {
					tmp.prev = c.prev;
					tmp.calcCosts();
				}
				return;
			}
		}
		
		//not found
		//System.out.println(c + " added to openlist");
		openlist.add(c);
		
	}
	
	
	/**
	 * This method searches the best cell as target to place a bomb to kill the focusedEnemy. It
	 * uses the directionHistory of the focused Man-object to calculate which node it will cross next.
	 */
	private void searchTargetAttack() {
		
		//initialize directionCount with 0
		Arrays.fill(directionCount,  0);
		
		for( Integer i : directionHistory) {
			directionCount[i]++;
		}
		
		//get direction with highest count
		int direction = getMaxIndex(directionCount);
		directionCount[direction] = 0;
		
		//find suitable Node as Target
		calculateTarget(focusedEnemy.getX(), focusedEnemy.getY(), direction);
		
	}
	
	
	/**
	 * Calculates the target by looking for a suitable Node in the given direction from
	 * the position (x/y)
	 * @param x - start x-coordinate
	 * @param y - start y-coordinate
	 * @param direction - searching direction
	 */
	private void calculateTarget(int x, int y, int direction) {
		
		target = null;

		switch(direction) {
		
		case Man.UP:
			findTarget(x, y, 0, -1);
			break;
			
		case Man.DOWN:
			findTarget(x, y, 0, 1);
			break;
			
		case Man.RIGHT:
			findTarget(x, y, 1, 0);
			break;
			
		case Man.LEFT:
			findTarget(x, y, -1, 0);
			break;
		default:
			if(sum(directionCount) <= 0) {
				target = new Cell(x, y, null);
				placebomb = true;
				return;
			}
			chooseRandomTargetDirection(x,y);
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
	private void findTarget( int x, int y, int xfac, int yfac) {
		GameObject[][] field = handler.getField();
		
		int i = 1;
		int x_tmp = x + xfac * i;
		int y_tmp = y + yfac * i;
		
		while(y_tmp >= 0 &&  y_tmp < field[0].length &&
				x_tmp < field.length && x_tmp >= 0) {
			
			if(field[x_tmp][y_tmp] instanceof Wall) {
				//if way is blocked by Wall break the loop
				break;
			}
			
			//check if found node has same coordinates
			Node q = nodes.ceiling(new Node(x_tmp, y_tmp, 0, null));
			if(q != null && q.x == x_tmp && q.y == y_tmp) {
				target = new Cell(x_tmp, y_tmp, null);
				placebomb = true;
				return;
			}
			i++;
			x_tmp = x + xfac * i;
			y_tmp = y + yfac * i;
			
		}
		//no suitable node found (Wall blocked or out of field)
		int direction = getMaxIndex(directionCount);
		directionCount[direction] = 0;
		calculateTarget(x_tmp - xfac, y_tmp - yfac, direction);
		
	}
	
	
	/**
	 * This method calculates a random direction and calls calculateTraget() with the given
	 * x- and y-coordinates and the calculated direction.
	 * @param x - start x-coordinate
	 * @param y - start y-coordiante
	 */
	private void chooseRandomTargetDirection(int x, int y) {
		while(true) {
			int i = rand.nextInt(NUMBER_OF_DIRECTIONS);
			if(i != Man.NO_DIR) {
				calculateTarget(x, y, i);
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
	
	/**
	 * Returns true if the given Cell is probably in danger of being hit by an
	 * Explosion.
	 * @param c - Cell that is to be proofed to be out of danger
	 * @return - 
	 */
	private boolean isInBombRange(Cell c) {
		for(Bomb b : handler.getBombs()) {
			//simple and ineffective algorythm!!!
			int dx = Math.abs(c.x - b.getX());
			int dy = Math.abs(c.y - b.getY());
			if(dx <= Explosion.RANGE && dy <= Explosion.RANGE) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Clears the lists openlist, closedlist and inclosed.
	 */
	private void clearLists() {
		openlist.clear();
		closedlist.clear();
		for( int i = 0; i < inclosed.length; i++) {
			for( int j = 0; j < inclosed[0].length; j++) {
				inclosed[i][j] = false;
			}
		}
	}
	
	
	/**
	 * Pushes the path referenced by target.prev onto path.
	 */
	private void pushOnPath() {
		path.clear();
		
		if(target.prev != null) {
			//System.out.println("-----------------------");
			for(Cell q = target; q != null; q = q.prev) {
				//System.out.println("Pushing " + q);
				path.push(q);
			}
		}
	}
	
	
	/**
	 * Sets the direction of the controlled Man-object to the calculated value. The value is calculated
	 * using the next cell from path and the current location of the Man-object. If path is empty, this
	 * method does nothing.
	 */
	private void calcManDirection() {
		
		Cell c = path.peek();
		
		if( c == null) {
			return;
		}
		
		int dx = c.x - man.getX();
		int dy = c.y - man.getY();
		
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
	 * Checks if the coordinates of the target Cell and the ones of the Man-object are the same.
	 * If so, target is set to null and placebomb of the Man-object is set to the AI's placebomb value.
	 * @return - returns true if target was reached
	 */
	private boolean checkTargetReached() {
		
		if(target != null && man.getX() == target.x && man.getY() == target.y) {
			target = null;
			man.setPlaceBomb(placebomb);
			man.setDirection(Man.NO_DIR);
			placebomb = false;
			return true;
		}
		return false;
		
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
	 * Returns the current target Cell of the AI.
	 * @return - target of the AI
	 */
	public Cell getTarget() {
		return target;
	}
	
	
	/**
	 * Sets the target Cell of the AI to the specified Cell.
	 * @param c - Cell-object that should be the new target.
	 */
	public void setTarget(final Cell c) {
		target = c;
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
			sb.append("[").append(target.x).append(FORMAT).append(target.y).append("]");
		}
		sb.append(" F: ");
		if(focusedEnemy == null) {
			sb.append("NULL");
		} else {
			sb.append("[").append(focusedEnemy.getX()).append(FORMAT).append(focusedEnemy.getY()).append("]");
		}
		sb.append(" Turn: ").append(turns);
		return sb.toString();
	}
	
//##############################################################
//####################### Nested Classes #######################
//##############################################################
	
	/**
	 * This class is used for A*. It provides a Reference on a Cell-object to connect Cells
	 * like a linkedList to recreate the shortest way calculated by A*.
	 * @author Fabian
	 *
	 */
	class Cell implements Comparable<Cell> {
		private int x;
		private int y;
		
		/**
		 * Reference to previous Cell
		 */
		private Cell prev;
		
		/**
		 * Costs of the path to get up to this Cell
		 */
		private int pathcost;
		
		/**
		 * Costs of the path to get from this Cell to the target
		 */
		private int heucost;
		
		/**
		 * allover costs
		 */
		private int cost;
		
		
		/**
		 * Creates a Cell-object with the specified x- and y-coordinates and reference
		 * to the specified Cell. All costs are initialized with 0.
		 * @param x - x-coordinate
		 * @param y - y-coordinate
		 * @param prev - previous Cell-object
		 */
		public Cell(int x, int y, Cell prev) {
			this.x = x;
			this.y = y;
			this.prev = prev;
		}
		
		
		/**
		 * Calculates pathcosts as pathcosts of the previous Cell-object + 1 and the heuristic pathcost 
		 * as the logically needed steps to get to the target.
		 * The allover costs are the sum of pathcost and heucost.
		 */
		private void calcCosts() {
			if(prev != null) {
				pathcost = prev.pathcost + 1;
			} else {
				pathcost = 0;
			}
			heucost = Math.abs(this.x - target.x) + Math.abs(this.y - target.y);
			cost = heucost + pathcost;
			
		}
		
		
		/**
		 * Two Cells are equal if they have the same x- and y-coordinates.
		 */
		@Override
		public boolean equals(Object o) {
			if(o instanceof Cell) {
				Cell c = (Cell) o;
				return c.x == this.x && c.y == this.y;
			}
			return false;
		}

		
		/**
		 * A Cell is bigger (>) than another if the allover costs are higher than the allover
		 * costs of the other one.
		 */
		@Override
		public int compareTo(Cell c) {
			if(this.equals(c)) {
				return 0;
			}
			if(this.cost < c.cost ) {
				return -1;
			} else {
				return 1;
			}
		}
		
		
		/**
		 * Returns the String-form of a Cell-object.
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("C: ");
			sb.append("[").append(x).append(FORMAT).append(y).append("] ");
			sb.append("Cost: ").append(cost);
			return sb.toString();
		}
		
	}
	
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
