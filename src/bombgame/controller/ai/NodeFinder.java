package bombgame.controller.ai;

import java.util.Arrays;

import bombgame.controller.GameHandler;
import bombgame.entities.GameObject;
import bombgame.entities.Wall;


/**
 * 
 * @author Rookfighter
 *
 */
public class NodeFinder {

	private static final int DEF_SIZE = 10;
	
	private static final int INC_FAC = 10;
	
	protected NodeFinder() {
		
	}
	
	/**
	 * Searches through the field given by handler and 
	 * and returns an ordered (by natural order) Array of all found Nodes.
	 */
	public static Node[] findAllNodes(GameHandler handler) {
		
		Node[] nodes = new Node[DEF_SIZE];
		int[] count = {0};
		
		//go through the whole field and create a Node if the given place fulfills the conditions
		for(int i = 0; i < handler.getField().length; i++) {
			for(int j = 0; j < handler.getField()[0].length; j++) {
				nodes = ensureCapacity(count, nodes);
				createNode(i,j, handler, nodes, count);
			}
		}
		
		//copy the nodes to a new array with correct size (less overhead)
		Node[] old = nodes;
		
		nodes = new Node[count[0]];
		System.arraycopy(old, 0, nodes, 0, nodes.length);
		//sort the array by natural order.
		Arrays.sort(nodes);
		return nodes;
	}
	
	/**
	 * Creates a new Node for the given coordinates considering the free directions
	 * from (x/y) and adds it to nodes.
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 */
	protected static void createNode(int x, int y, GameHandler handler, Node[] nodes, int[] count) {
		
		GameObject field[][] = handler.getField();
		
		//if it is a Wall it cannot be a Node
		if(field[x][y] instanceof Wall) {
			return;
		}
		
		boolean direction[] = { false, false, false, false };
		int directions = 0;
		
		//check the directions of the given Position
		directions += checkDirections(x, y, 1, 0, direction, Node.RIGHT, field);
		directions += checkDirections(x, y, -1, 0, direction, Node.LEFT, field);
		directions += checkDirections(x, y, 0, -1, direction, Node.UP, field);
		directions += checkDirections(x, y, 0, 1, direction, Node.DOWN, field);
		
		Node q = new Node(x, y, directions, direction);
		addNode(q, nodes, count);
		
	}
	
	/**
	 * 
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param xfac - x-direction
	 * @param yfac - y-direction
	 * @param direction - directionarray
	 * @param dir - direction number
	 * @param field - field in which the Position is
	 * @return - value is 1 if the examined direction is not blocked else 0
	 */
	protected static int checkDirections(int x, int y, int xfac, int yfac, final boolean[] direction, int dir, final GameObject field[][]) {
		
		int xtmp = x + xfac;
		int ytmp = y + yfac;
		
		boolean xlegal = xtmp < field.length && xtmp >= 0;
		boolean ylegal = ytmp < field[0].length && ytmp >= 0;
		
		if(xlegal && ylegal && !(field[xtmp][ytmp] instanceof Wall) ) {
			direction[dir] = true;
			return 1;
		}
		
		return 0;
	}
	
	/**
	 *Adds the given node to nodes, if it has more than 1 free direction and if it has 2 directions, only they
	 *are not opposing.
	 */
	 protected static void addNode(final Node q, Node[] nodes,int[] count) {
	 
		
		if(q.directions <= 1) {
			return;
		}
		
		boolean updown =  q.direction[Node.UP] && q.direction[Node.DOWN];
		boolean leftright = q.direction[Node.RIGHT] && q.direction[Node.LEFT];
		
		if(q.directions == 2 && (updown || leftright)) {
			return;
		}
		
		nodes[count[0]] = q;
		count[0]++;
	}
	 
	 /**
	  * Ensures the capacity for nodes.
	  * @param count - count of Nodes hold in nodes
	  * @param nodes - array of Nodes
	  * @return - new array of nodes
	  */
	 protected static Node[] ensureCapacity(int[] count, Node[] nodes) {
		 
		 if(count[0] >= nodes.length) {
			 Node[] old = nodes;
			 nodes = new Node[count[0] * INC_FAC];
			 System.arraycopy(old, 0, nodes, 0, old.length);
		 }
		 
		 return nodes;
	 }
	 
	 
	//############################################################################################
	//############################################################################################
		
		/**
		 * This class is used for marking places in the field, which don't lead into a dead-end.
		 * @author Fabian
		 *
		 */
		static class Node implements Comparable<Node> {
			
			protected final int x;
			protected final int y;
			
			protected final int directions;
			
			protected final boolean[] direction;
			
			protected static final int UP = 0;
			protected static final int DOWN = 1;
			protected static final int LEFT = 2;
			protected static final int RIGHT = 3;
			
			protected static final int NODE_DIRECTIONS = 4;
			
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
				if(this.x > q.x) {
					return 1;
				} else if(this.x < q.x){
					return -1;
				} else if( this.y > q.y) {
					return 1;
				} else {
					return -1;
				}
			}
			
		}
	
}
