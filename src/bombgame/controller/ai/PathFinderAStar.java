package bombgame.controller.ai;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import bombgame.controller.GameHandler;

/**
 * This class calculates the path between two Positions with the A*-Algorythm.
 * See: calculatePath().
 * @author Rookfighter
 *
 */
public final class PathFinderAStar implements PathFinder {
	
	/**
	 * ExtraCostCalculator which the Algorythm considers
	 */
	protected final ExtraCostCalculator extraCostCalc;
	
	/**
	 * ClosedListSelector which the Algorythm considers
	 */
	protected final ClosedListSelector selector;
	
	/**
	 * target cell
	 */
	protected Cell target;
	
	/**
	 * List of closed Cells
	 */
	protected final Set<Cell> closedlist;
	
	/**
	 * List of known cells
	 */
	protected final PriorityQueue<Cell> openlist;
	
	/**
	 * Maps isclosed statement to field positions
	 */
	protected final boolean inclosed[][];
	
	
	/**
	 * Creates a PathFinder searching in the given GameHandler considering the given ExtraCostCalculator and
	 * the given ClosedListSelector.
	 * @param handler - GameHandler in which is searched
	 * @param extraCostCalc- ExtraCostCalculator which will be considered
	 * @param selector - ClosedListSelector which will be considered 
	 */
	public PathFinderAStar(final GameHandler handler, final ExtraCostCalculator extraCostCalc, final ClosedListSelector selector) {
		this.openlist = new PriorityQueue<Cell>();
		this.closedlist = new TreeSet<Cell>();
		this.extraCostCalc = extraCostCalc;
		this.selector = selector;
		this.inclosed = new boolean[handler.getField().length][handler.getField()[0].length];
	}
	
	
	/**
	 * Calculates the Path from start to target with the A* Algorythm.
	 */
	@Override
	public Deque<Position> calculatePath(final Position start, final Position target) {
		
		Deque<Position> path = new LinkedList<Position>();
		
		clearLists();		
		
		this.target = new Cell(target.getX(), target.getY(), null);
		
		addOpenList(new Cell(start.getX(), start.getY(), null));
		
		//search path
		while(!openlist.isEmpty()) {
			
			//get the first element in the queue (with lowest cost)
			Cell c = openlist.poll();
			addClosedList(c);
			//if this Cell was the target it is now in the closedlist
			if(inclosed[target.getX()][target.getY()]) {
				this.target = c;
				break;
			}
			
			//now examine neighbours (up, down, left, right)
			addNeighbours(c);
			
		}
		
		//put found path into the pathdeque
		pushOnPath(path);
		return path;
	}
	
	/**
	 * Adds the specified Cell-Object to the openlist considering the extraCostCalculator and
	 * the ClosedListSelector.
	 * @param c - Cell-object that will be added to the openlist.
	 */
	protected void addOpenList(final Cell c) {
		if( c == null ){
			return;
		}

		c.calcCosts();
		
		Position pos = new Position (c.x, c.y);
		
		//if selector returns true add to closed list
		if(selector != null && selector.moveToClosedList(pos, c.pathcost)) {
			addClosedList(c);
			return;
		}
		
		//increase cost calculated by ExtraCostCalculator
		if(extraCostCalc != null) {
			c.cost += extraCostCalc.calcExtraCost(pos);
		}
		
		
		// check if cell is already in openlist, if so update prev and costs
		for(Cell tmp : openlist) {
			if(tmp.equals(c)) {
				if(c.cost < tmp.cost) {
					tmp.prev = c.prev;
					tmp.calcCosts();
					tmp.cost = c.cost;
				}
				return;
			}
		}
		
		//not found
		openlist.add(c);
		
	}
	
	/**
	 * Adds the specified Cell-object to the closedlist.
	 * @param c - Cell-object that will bei added to the closedlist
	 */
	protected void addClosedList(final Cell c) {

		inclosed[c.x][c.y] = true;
		closedlist.add(c);
		
	}
	
	/**
	 * Adds all neighbours of the specified Cell-object to the openlist.
	 * @param c - Cell-object whose neighbours are examined 
	 */
	protected void addNeighbours(final Cell c) {
		
		//right
		addNeighbour(c, 1, 0);
		//left
		addNeighbour(c, -1, 0);
		//up
		addNeighbour(c, 0, -1);
		//down
		addNeighbour(c, 0, 1);
		
	}
	
	/**
	 * Adds a new Cell to the openlist if it is not in the closedlist and has a legal
	 * Position
	 * @param c - Cell that wants a neighbour
	 * @param xfac - x-direction
	 * @param yfac - y-direction
	 */
	protected void addNeighbour(final Cell c, int xfac, int yfac) {
		int xtmp = c.x + xfac;
		int ytmp = c.y + yfac;
		boolean xlegal = xtmp < inclosed.length && xtmp >= 0;
		boolean ylegal = ytmp < inclosed[0].length && ytmp >= 0;
		
		if(xlegal && ylegal && !(inclosed[xtmp][ytmp])) {
			addOpenList(new Cell(xtmp, ytmp, c));
		}
	}
	
	/**
	 * Pushes the path referenced by target.prev onto path.
	 */
	protected void pushOnPath(final Deque<Position> path) {
		
		path.clear();
		if(target.prev != null) {
			//iterate way backwards from target to start
			for(Cell q = target; q != null; q = q.prev) {
				Position pos = new Position(q.x, q.y);
				path.push(pos);
			}
		}
	}
	
	
	/**
	 * Clears the lists openlist, closedlist and inclosed.
	 */
	protected void clearLists() {
		openlist.clear();
		closedlist.clear();
		
		//nothing is in the closedlist
		for(int i = 0; i < inclosed.length; i++) {
			Arrays.fill(inclosed[i], false);
		}
	}
	
	
//############################################################################################
//############################################################################################
	
	/**
	 * This class is used for A*. It provides a Reference on a Cell-object to connect Cells
	 * like a linkedList to recreate the shortest way calculated by A*.
	 * @author Fabian
	 *
	 */
	class Cell implements Comparable<Cell> {
		
		/**
		 * x-coordinate of the Cell
		 */
		protected final int x;
		
		/**
		 * y-coordinate of the Cell
		 */
		protected final int y;
		
		/**
		 * Reference to previous Cell
		 */
		protected Cell prev;
		
		/**
		 * Costs of the path to get up to this Cell
		 */
		protected int pathcost;
		
		/**
		 * Costs of the path to get from this Cell to the target
		 */
		protected int heucost;
		
		/**
		 * allover costs
		 */
		protected int cost;
		
		
		/**
		 * Creates a Cell-object with the specified x- and y-coordinates and reference
		 * to the specified Cell. All costs are initialized with 0.
		 * @param x - x-coordinate
		 * @param y - y-coordinate
		 * @param prev - previous Cell-object
		 */
		public Cell(int x, int y, final Cell prev) {
			this.x = x;
			this.y = y;
			this.prev = prev;
		}
		
		
		/**
		 * Calculates pathcosts as pathcosts of the previous Cell-object + 1 and the heuristic pathcost 
		 * as the logically needed steps to get to the target.
		 * The allover costs are the sum of pathcost and heucost.
		 */
		protected void calcCosts() {
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
		public boolean equals(final Object o) {
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
		public int compareTo(final Cell c) {
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
			sb.append("[").append(x).append("] [").append(y).append("] ");
			sb.append("Cost: ").append(cost);
			return sb.toString();
		}
		
	}

}
