package bombgame.controller.ai.impl;

import java.util.Deque;

import junit.framework.TestCase;
import bombgame.controller.ai.impl.AISelector;
import bombgame.controller.ai.impl.BombCostCalculator;
import bombgame.controller.ai.impl.PathFinderAStar;
import bombgame.controller.ai.impl.Position;
import bombgame.controller.gamehandler.impl.GameHandler;
import bombgame.entities.impl.GameObject;
import bombgame.entities.impl.Wall;

public class PathFinderAStarTest extends TestCase {
	
	private GameHandler gh;
	private PathFinderAStar pfas;
	
	public void setUp() {
		gh = new GameHandler(new GameObject[10][10]);
		pfas = new PathFinderAStar(gh, new BombCostCalculator(gh), new AISelector(gh));
	}
	
	public void testCalculatePath() {
		Deque<Position> path = pfas.calculatePath(new Position(0,0), new Position(9,9));
		assertNotNull(path);
		gh.addObject(new Wall(1,0));
		gh.addObject(new Wall(0,1));
		gh.addObject(new Wall(1,1));
		assertEquals(pfas.calculatePath(new Position(0,0), new Position(9,9)).size(),0);
	}
	
	public void testAddOpenList() {
		pfas.addOpenList(null);
		assertEquals(pfas.getOpenlist().size(), 0);
		
		PathFinderAStar pfastmp = new PathFinderAStar(gh, null, new AISelector(gh));
		pfastmp.setTarget( pfastmp.new Cell(9,9,null));
		pfastmp.addOpenList(pfastmp.new Cell(2,2,null));
		assertEquals(pfastmp.getOpenlist().size(), 1);
		
		pfastmp = new PathFinderAStar(gh, new BombCostCalculator(gh), null);
		pfastmp.setTarget(pfastmp.new Cell(9,9,null));
		pfastmp.addOpenList(pfastmp.new Cell(0,1,null));
		assertEquals(pfastmp.getOpenlist().size(), 1);
		
	}
	
	public void testCellEquals() {
		PathFinderAStar.Cell c1 = pfas.new Cell(0,0,null);
		String s = "blas";
		assertFalse(c1.equals(s));
	}
	
	public void testCellToString() {
		
		PathFinderAStar.Cell c1 = pfas.new Cell(0,0,null);
		String s = "C: [0] [0] Cost: 0";
		assertEquals(c1.toString(), s);
		
	}
	
	public void testCellHashCode() {
		PathFinderAStar.Cell c1 = pfas.new Cell(1,0,null);
		assertEquals(c1.hashCode(), 961);
	}

}
