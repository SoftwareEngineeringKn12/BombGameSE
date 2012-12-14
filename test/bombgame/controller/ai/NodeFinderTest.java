package bombgame.controller.ai;

import bombgame.controller.GameHandler;
import bombgame.controller.ai.NodeFinder.Node;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Wall;
import junit.framework.TestCase;

public class NodeFinderTest extends TestCase{

	private GameHandler gh;
	
	public void setUp() {
		gh = new GameHandler(new GameObject[10][10]);
		new NodeFinder();
	}
	
	public void testFindAllNodes() {
		assertEquals(10 * 10, NodeFinder.findAllNodes(gh).length);
	}
	
	public void testCreateNode() {
		gh.addObject(new Wall(0,0));
		Node[] nodes = new Node[1];
		int[] count =  new int[1];
		NodeFinder.createNode(0, 0, gh, nodes, count);
		assertEquals(count[0], 0);
	}
	
	public void testCheckDirections() {
		gh.addObject(new Wall(0,0));
		assertEquals(0, NodeFinder.checkDirections(0, 0, 0, 0, null, 0, gh.getField()));
		assertEquals(0, NodeFinder.checkDirections(1, 0, -1, 0, null, 0, gh.getField()));
		assertEquals(0, NodeFinder.checkDirections(0, 1, 0, -1, null, 0, gh.getField()));
	}
	
	public void testAddNode() {
		Node[] nodes = new Node[1];
		int[] count =  new int[1];
		Node q = new Node(0,0,1, null);
		NodeFinder.addNode(q,nodes,count);
		assertEquals(count[0],0);
		
		q = new Node(0,0,2,false, false, true, true);
		NodeFinder.addNode(q,nodes,count);
		assertEquals(count[0],0);
		
		q = new Node(0,0,2,true,true, false, false);
		NodeFinder.addNode(q,nodes,count);
		assertEquals(count[0],0);
		
	}
	
	public void testNodeGetX() {
		Node q = new Node(1,0,0,null);
		assertEquals(q.getX(),1);
	}
	
	public void testNodeGetY() {
		Node q = new Node(0,1,0,null);
		assertEquals(q.getY(),1);
		
	}
	
	public void testNodeEquals() {
		Node q1 = new Node(0,1,0,null);
		Node q2 = new Node(1,0,0,null);
		assertFalse(q2.equals(q1));
		String s = "bla";
		assertFalse(q2.equals(s));
		
		Node q3 = new Node(0,1,1,new boolean[4]);
		assertTrue(q3.equals(q1));
	}
	
	public void testNodeCompareTo() {
		Node q1 = new Node(0,1,0,null);
		Node q2 = new Node(0,1,2,new boolean[4]);
		assertEquals(0, q1.compareTo(q2));
		q2 = new Node(1,0,0,null);
		assertEquals(-1, q1.compareTo(q2));
		q2 = new Node(0,2,0,null);
		assertEquals(-1, q1.compareTo(q2));
	}
}
