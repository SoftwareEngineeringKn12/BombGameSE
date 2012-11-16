package bombgame.controller.ai;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

import bombgame.controller.GameHandler;
import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.Wall;

public final class PathFinderAStar implements PathFinder {

	/**
	 * Extra-cost for cells threatened by a Bomb-object
	 */
	private static final int B_COST = 9;
	
	private Cell target;
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
	
	private GameHandler handler;
	
	public PathFinderAStar(GameHandler handler) {
		this.handler = handler;
	}
	
	
	@Override
	public Deque<Position> calculatePath(Position start, Position target) {
		
		Deque<Position> path = new LinkedList<Position>();
		
		clearLists();		
		
		this.target = new Cell(target.getX(), target.getY(), null);
		
		addOpenList(new Cell(start.getX(), start.getY(), null));
		
		//search path
		while(!openlist.isEmpty()) {
			
			Cell c = openlist.poll();
			//System.out.println("-> Popped: " + c);
			addClosedList(c);
			
			if(inclosed[target.getX()][target.getY()]) {
				//System.out.println("=> found Target " + c);
				break;
			}
			
			addNeighbours(c);
			
		}
		
		//put path in path deque
		pushOnPath(path);
		return path;
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
	 * Adds the specified Cell-object to the closedlist.
	 * @param c - Cell-object that will bei added to the closedlist
	 */
	private void addClosedList(Cell c) {

		inclosed[c.x][c.y] = true;
		closedlist.add(c);
		
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
	 * Pushes the path referenced by target.prev onto path.
	 */
	private void pushOnPath(Deque<Position> path) {
		path.clear();
		
		if(target.prev != null) {
			//System.out.println("-----------------------");
			for(Cell q = target; q != null; q = q.prev) {
				//System.out.println("Pushing " + q);
				path.push(new Position(q.x, q.y));
			}
		}
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
	
	
//############################################################################################
//############################################################################################
	
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
			sb.append("[").append(x).append("] [").append(y).append("] ");
			sb.append("Cost: ").append(cost);
			return sb.toString();
		}
		
	}

}
