package bombgame.controller;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;

import bombgame.entities.Man;
import bombgame.entities.Wall;

public class ManAI {

	private Man man;
	
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
		
		if(handler.getField()[c.x][c.y] instanceof Wall) {
			c.blocked = true;
			addClosedList(c);
			return;
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
	
	
	private class Cell implements Comparable<Cell> {
		private int x;
		private int y;
		
		private Cell prev;
		
		private boolean blocked;
		
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
