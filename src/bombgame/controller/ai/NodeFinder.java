package bombgame.controller.ai;

import java.util.Arrays;

import bombgame.controller.GameHandler;
import bombgame.entities.GameObject;
import bombgame.entities.Wall;

public class NodeFinder {

	
	
	/**
	 * Searches through the field given by handler and 
	 * and returns an ordered (by natural order) Array of all found Nodes.
	 */
	public static Node[] findAllNodes(GameHandler handler) {
		Node[] nodes = new Node[10];
		int[] count = {0};
		
		for(int i = 0; i < handler.getField().length; i++) {
			for(int j = 0; j < handler.getField()[0].length; j++) {
				createNode(i,j, handler, nodes, count);
			}
		}
		Node[] old = nodes;
		nodes = new Node[count[0]];
		System.arraycopy(old, 0, nodes, 0, count[0]);
		Arrays.sort(nodes);
		return nodes;
	}
	
	/**
	 * Creates a new Node for the given coordinates considering the free directions
	 * from (x/y) and adds it to nodes.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	private static void createNode(int x, int y, GameHandler handler, Node[] nodes, int[] count) {
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
		addNode(q, nodes, count);
		
	}
	
	
	/**
	 *Adds the given node to nodes, if it has more than 1 free direction and if it has 2 directions, only they
	 *are not opposing.
	 */
	 private static void addNode(final Node q, Node[] nodes,int[] count) {
	 
		
		if(q.directions <= 1) {
			return;
		} else if(q.directions == 2) {
			if( ( q.direction[Node.UP] && q.direction[Node.DOWN] ) || (q.direction[Node.RIGHT] && q.direction[Node.LEFT]) ) {
				return;
			}
			
		}
		
		nodes[count[0]] = q;
		count[0]++;
	}
	 
	 
	//############################################################################################
	//############################################################################################
		
		/**
		 * This class is used for marking places in the field, which don't lead into a dead-end.
		 * @author Fabian
		 *
		 */
		static class Node implements Comparable<Node> {
			
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
