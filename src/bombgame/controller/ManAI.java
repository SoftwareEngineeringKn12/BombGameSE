package bombgame.controller;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;

import bombgame.entities.Bomb;
import bombgame.entities.Explosion;
import bombgame.entities.Man;
import bombgame.entities.Wall;

public class ManAI {

	private Man man;
	
	private static final int B_COST = 6;
	
	private GameHandler handler;
	
	private TreeSet<Cell> closedlist;
	
	private PriorityQueue<Cell> openlist;
	
	private boolean inclosed[][];
	
	private Cell target;
	
	private Deque<Cell> path;
	
	public ManAI (Man man, GameHandler handler) {
		this.man = man;
		this.handler = handler;
		openlist = new PriorityQueue<Cell>();
		closedlist = new TreeSet<Cell>();
		inclosed = new boolean[handler.getField()[0].length] [handler.getField().length];
		path = new LinkedList<Cell>();
	}
	
	
	public void calcNextStep() {
		if(path.isEmpty()) {
			//calculatePathTo(x, y)
		}
		
		Cell c = path.pop();
		
		if(c.x == man.getX() && c.y == man.getY()) {
			c = path.pop();
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
	private void calculatePathTo(int x, int y) {
		openlist.clear();
		closedlist.clear();
		target = new Cell(x, y, null);
		
		
		openlist.add(new Cell(man.getX(), man.getY(), null));
		
		//search path
		while(!openlist.isEmpty()) {
			
			Cell c = openlist.poll();
			addClosedList(c);
			
			if(inclosed[target.x][target.y]) {
				break;
			}
			
			addNeighbours(c);
			
		}
		
		//put path in path deque
		path.clear();
		
		if(inclosed[target.x][target.y]) {
			for(Cell q = target; q != null; q = q.prev) {
				path.push(q);
			}
		}
		
		
	}
	
	/**
	 * Adds all neighbour of the specified Cell-object to the openlist.
	 * @param c - Cell-object whose neighbours are examined 
	 */
	private void addNeighbours(Cell c) {
		
		if(c.x + 1 < inclosed[0].length && !(inclosed[c.x+1][c.y])) {
			
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
	 * @param c - Cell-object that will be added to the openlist.
	 */
	private void addOpenList(Cell c) {
		if( c == null ){
			return;
		}
		
		
		c.calcCosts();
		
		//if is blocking the way immediatly put to closed list
		if(handler.getField()[c.x][c.y] instanceof Wall) {
			addClosedList(c);
			return;
		}
		
		//if an explosion is in the way
		if(handler.getField()[c.x][c.y] instanceof Explosion) {
			Explosion exp = (Explosion) handler.getField()[c.x][c.y];
			if(exp.getTimer() >= c.pathcost) {
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
				if(c.cost < tmp.cost) {
					tmp.prev = c.prev;
					tmp.calcCosts();
				}
				return;
			}
		}
		
		//not found
		openlist.add(c);
		
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
			if(dx <= Explosion.RANGE || dy <= Explosion.RANGE) {
				return true;
			}
		}
		return false;
	}
	
	
	private class Cell implements Comparable<Cell> {
		private int x;
		private int y;
		
		private Cell prev;
		
		private int pathcost;
		private int heucost;
		private int cost;
		
		private Cell(int x, int y, Cell prev) {
			this.x = x;
			this.y = y;
			this.prev = prev;
		}
		
		private void calcCosts() {
			pathcost = prev.pathcost + 1;
			heucost = Math.abs(this.x - target.x) + Math.abs(this.y - target.y);
			cost = heucost + pathcost;
			
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Cell) {
				Cell c = (Cell) o;
				return c.x == this.x && c.y == this.y;
			}
			return false;
		}

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
		
	}
}
