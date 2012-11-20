package bombgame.controller;

import bombgame.controller.MazeGen.Cell;
import junit.framework.TestCase;

public final class MazeGenTest extends TestCase {

	MazeGen maze1; // Standard constructor
	MazeGen maze2;
	MazeGen maze3;
	MazeGen maze4; //!!

	public void setUp() {
		maze1 = new MazeGen(); // Standard constructor

		maze2 = new MazeGen(2, 2);
		maze3 = new MazeGen(3, 3);
	}

	public void testMazeGen() {
		maze1.genMaze();

		// Check x and y length and number deletions per line
		assertEquals(maze1.getMaze().length, MazeGen.STANDARD_X_LENGTH);
		assertEquals(maze1.getMaze()[0].length, MazeGen.STANDARD_Y_LENGTH);
		assertEquals(maze1.getNumberOfDeletionsPerLine(),
				(int) MazeGen.STANDARD_X_LENGTH * MazeGen.NUMBER_DEL_FACTOR);
	}

	public void testGenMaze() {
		maze1.genMaze();
		Cell[][] maze = maze1.getMaze();
		
		//boolean startAtCorner = false;
		//boolean startAtMiddle = false;
		//boolean startAtSide = false;

		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				assertTrue(maze[j][i].getWall());
			}
		}
		
		maze2.genMaze();
		maze = maze2.getMaze();
		/*
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (j == 1 && i == 1) {
					assertFalse(maze[j][i].getWall());
				} else {
					assertTrue(maze[j][i].getWall());
				}
			}
		}*/
	}

	public void testGenNonPerfectMaze() {
		maze1.genNonPerfectMaze();
		//!! TODO
	}

	public void testGetMaze() {
		maze1.genMaze();

		assertNotNull(maze1.getMaze());
	}

	public void testToString() {

	}
}
