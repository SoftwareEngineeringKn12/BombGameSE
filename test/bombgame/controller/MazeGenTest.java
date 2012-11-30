package bombgame.controller;

import java.util.LinkedList;
import java.util.List;
import bombgame.controller.MazeGen.Cell;
import junit.framework.TestCase;

public final class MazeGenTest extends TestCase {

	MazeGen maze1; // Standard constructor
	MazeGen maze2;
	MazeGen maze3;

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
			(int) (MazeGen.STANDARD_X_LENGTH * MazeGen.NUMBER_DEL_FACTOR));
	}

	public void testGenMaze() {
		maze2.genMaze();
		Cell[][] maze = maze2.getMaze();
		
		int wallCounter = 0;
		
		boolean startAtCorner = true;
		boolean startAtMiddle = true;
		boolean startAtSideA = true;
		boolean startAtSideB = true;
		
		boolean allChecked = false;
		
		// 2x2 field
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (!maze[j][i].getWall()) {
					wallCounter++;
				}
			}
		}
		
		assertEquals(wallCounter, 1);
		
		maze3.genMaze();
		maze = maze3.getMaze();
		
		//System.out.println(maze3);
		
		// 3x3 field
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (j == 1 && i == 1) {
					if (maze[j][i].getWall()) {
						startAtMiddle = false;
					}
				} else if (!maze[j][i].getWall()) {
					startAtMiddle = false;
				}
			}
		}
		
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if ((j == 1 && i == 0) || (j == 1 && i == 1) || (j == 1 && i == 2)) {
					if (maze[j][i].getWall()) {
						startAtSideA = false;
					}
				} else if (!maze[j][i].getWall()) {
					startAtSideA = false;
				}
			}
		}
		
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if ((j == 0 && i == 1) || (j == 1 && i == 1) || (j == 2 && i == 1)) {
					if (maze[j][i].getWall()) {
						startAtSideB = false;
					}
				} else if (!maze[j][i].getWall()) {
					startAtSideB = false;
				}
			}
		}

		startAtCorner = cornerTest(maze);
		
		//System.out.println(startAtCorner);
		//System.out.println(startAtMiddle);
		//System.out.println(startAtSideA);
		//System.out.println(startAtSideB);
		
		if (startAtCorner ^ startAtMiddle ^ startAtSideA ^ startAtSideB) {
			allChecked = true;
		}

		assertTrue(allChecked);
	}
	
	private boolean cornerTest(Cell[][] maze) {
		List<Cell> list = new LinkedList<Cell>();
		
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				list.add(maze[j][i]);
			}
		}
		
		if (!maze[1][1].getWall()) {
			return false;
		}
		
		list.remove(maze[1][1]);
		
		if (maze[0][1].getWall()) {
			list.remove(maze[0][1]);
		} else if (maze[1][0].getWall()) {
			list.remove(maze[1][0]);
		} else if (maze[2][1].getWall()) {
			list.remove(maze[2][1]);
		} else if (maze[1][2].getWall()) {
			list.remove(maze[1][2]);
		} else {
			return false;
		}
		
		for (Cell c : list) {
			if (c.getWall()) {
				return false;
			}
		}
		
		return true;
	}

	public void testGenNonPerfectMaze() {
		int amountOfWallsPerLine = 0;
		boolean checked = true;
		maze3.genNonPerfectMaze();
		Cell[][] maze = maze3.getMaze();
		
		// Per x line -1 random wall
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (maze[j][i].getWall()) {
					amountOfWallsPerLine++;
				}
			}
			
			if (amountOfWallsPerLine == 3) {
				checked = false;
			}
			
			amountOfWallsPerLine = 0;
		}
		
		assertTrue(checked);
	}

	public void testGetMaze() {
		maze1.genMaze();

		assertNotNull(maze1.getMaze());
	}

	public void testToString() {
		maze2.genMaze();
		Cell[][] maze = maze2.getMaze();
		StringBuilder str = new StringBuilder();
		
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (maze[j][i].getWall()) {
					str.append(MazeGen.WALL);
				} else {
					str.append(MazeGen.PATH);
				}
			}
			str.append("\n");
		}
		
		assertEquals(str.toString(), maze2.toString());
	}
	
}
