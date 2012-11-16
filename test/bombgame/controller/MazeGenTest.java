package bombgame.controller;

import junit.framework.TestCase;

public final class MazeGenTest extends TestCase {
	
	MazeGen maze1;
	MazeGen maze2;
	
	public void setUp() {
		maze1 = new MazeGen();
		maze2 = new MazeGen(40, 10);
	}
	
	public void testGenMaze() {
		
	}

	public void testGenNonPerfectMaze() {
		
	}
	
	public void testGetMaze() {
		maze1.genMaze();
		
		assertNotNull(maze1.getMaze());
	}
	
	public void testToString() {
		
	}
}
