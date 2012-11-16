package bombgame.controller;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * Generates a random maze with the depth-first search algorithm. The maze is a
 * 2D array of Cell objects.
 * 
 * 2D Array: maze[x-coord][y-coord]
 * 
 * To display the maze on console: Wall: '#' Path: 'o'
 * 
 * @author jens
 * 
 */
public final class MazeGen {

	/**
	 * Width of the maze field.
	 */
	private int xLength;

	/**
	 * Height of the maze field.
	 */
	private int yLength;

	/**
	 * Removes on random positions walls in the maze to get a non-perfect maze.
	 */
	private int numberOfDeletionsPerLine;

	/**
	 * The maze field.
	 */
	private Cell maze[][];

	/**
	 * Stack to store the path. Used for backtracking -> if there is no path to
	 * got from the actual cell.
	 */
	private Deque<Cell> backtrack = new LinkedList<Cell>();

	/**
	 * Random number generator for random start cell, random path direction and
	 * creating a non-perfect maze.
	 */
	private Random rand = new Random();

	/**
	 * x-length of the maze when standard constructor is used.
	 */
	private static final int STANDARD_X_LENGTH = 60;

	/**
	 * y-length of the maze when standard constructor is used.
	 */
	private static final int STANDARD_Y_LENGTH = 15;

	/**
	 * Multiplied with x-length to calculate number of deletions per line
	 * relative to x-length of the maze.
	 */
	private static final double NUMBER_DEL_FACTOR = 0.4;

	/**
	 * Cell represent the objects in the maze. It can be a wall or path.
	 * 
	 * @author jens
	 * 
	 */
	static class Cell {
		private int x;
		private int y;

		// Cell is a wall at the beginning
		private boolean wall = true;
		private boolean visited = false;

		/**
		 * Constructor to create a new Cell.
		 * 
		 * @param x
		 *            x-coordinate
		 * @param y
		 *            y-coordinate
		 */
		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Returns value of wall. (If the Cell is a wall or path).
		 * 
		 * @return
		 */
		public boolean getWall() {
			return wall;
		}
	}

	/**
	 * Create maze with standard with and height.
	 */
	public MazeGen() {
		this(STANDARD_X_LENGTH, STANDARD_Y_LENGTH);
	}

	/**
	 * Create a maze with given with and height.
	 * 
	 * @param xLength
	 *            width of the maze
	 * @param yLength
	 *            height of the maze
	 */
	public MazeGen(int xLength, int yLength) {
		this.xLength = xLength;
		this.yLength = yLength;
		numberOfDeletionsPerLine = (int) (xLength * NUMBER_DEL_FACTOR);
	}

	private void generate() {
		// 0 = N, 1 = O, 2 = S, 3 = W
		boolean foundWay = false;
		boolean allChecked = false;
		boolean checked[] = new boolean[4];
		Cell startCell, temp; // wayToTemp = null;

		// Random start cell
		temp = maze[rand.nextInt(xLength)][rand.nextInt(yLength)];
		temp.visited = true;
		temp.wall = false;
		backtrack.push(temp);
		startCell = temp;

		// System.out.println(toString());
		// System.out.println("Start: " + temp.x + " " + temp.y);

		do {
			// Reset switches
			foundWay = false;
			allChecked = false;
			checked[0] = false;
			checked[1] = false;
			checked[2] = false;
			checked[3] = false;

			// Loop for one Cell
			// System.out.println("---------------------------------");
			while (!foundWay && !allChecked) {

				if (!temp.equals(temp = walk(temp, checked))) {
					foundWay = true;
				}

				// System.out.println("Temp Cell is at: " + temp.x + " " +
				// temp.y);
				// System.out.println("neighborLookDirection: " +
				// neighborLookDirection);

				/*
				 * switch (neighborLookDirection) { case 0: // S if ((temp.y +
				 * 2) < yLength && !maze[temp.x][temp.y + 2].visited) { //
				 * System.out.println(neighborLookDirection + //
				 * "=> Not visited: " + temp.x + " " + (temp.y + 2)); wayToTemp
				 * = maze[temp.x][temp.y + 1]; temp = maze[temp.x][temp.y + 2];
				 * 
				 * foundWay = true; temp.wall = false; temp.visited = true;
				 * wayToTemp.wall = false; backtrack.push(temp); } checked[0] =
				 * true; break; case 1: // O if ((temp.x + 2) < xLength &&
				 * !maze[temp.x + 2][temp.y].visited) { //
				 * System.out.println(neighborLookDirection + //
				 * "=> Not visited: " + (temp.x + 2) + " " + temp.y); wayToTemp
				 * = maze[temp.x + 1][temp.y]; temp = maze[temp.x + 2][temp.y];
				 * 
				 * foundWay = true; temp.wall = false; temp.visited = true;
				 * wayToTemp.wall = false; backtrack.push(temp); } checked[1] =
				 * true; break; case 2: // N if ((temp.y - 2) >= 0 &&
				 * !maze[temp.x][temp.y - 2].visited) { //
				 * System.out.println(neighborLookDirection + //
				 * "=> Not visited: " + temp.x + " " + (temp.y - 2)); wayToTemp
				 * = maze[temp.x][temp.y - 1]; temp = maze[temp.x][temp.y - 2];
				 * 
				 * foundWay = true; temp.wall = false; temp.visited = true;
				 * wayToTemp.wall = false; backtrack.push(temp); } checked[2] =
				 * true; break; case 3: // W if ((temp.x - 2) >= 0 &&
				 * !maze[temp.x - 2][temp.y].visited) { //
				 * System.out.println(neighborLookDirection + //
				 * "=> Not visited: " + (temp.x - 2) + " " + temp.y); wayToTemp
				 * = maze[temp.x - 1][temp.y]; temp = maze[temp.x - 2][temp.y];
				 * 
				 * foundWay = true; temp.wall = false; temp.visited = true;
				 * wayToTemp.wall = false; backtrack.push(temp); } checked[3] =
				 * true; break; }
				 */

				// System.out.println(checked[0] + " " + checked[1] + " " +
				// checked[2] + " " + checked[3]);

				if (!foundWay) {
					if (checked[0] && checked[1] && checked[2] && checked[3]) {
						// System.out.println("All checked: No way, go back.");
						allChecked = true;
						temp = backtrack.pop();
					}
				}
			}
			// System.out.println(toString());
			/*
			 * try { Thread.sleep(100); } catch (InterruptedException e1) {
			 * e1.printStackTrace(); }
			 */
		} while (!startCell.equals(temp));
	}

	/**
	 * Checks if there is a not visited cell at the 2nd next cell. If yes and it
	 * is not the end of the maze field, go to this cell, make it a path (remove
	 * the wall) and remove the wall between it. Than push this cell to the
	 * stack (for backtracking).
	 * 
	 * @param neighborLookDirection
	 *            - Direction to look for path
	 * @param temp
	 *            - Actual and next cell (if found path)
	 * @param checked
	 *            - Indicates which direction is already checked
	 * @return Next actual cell if found way, old cell if the path in this
	 *         direction is blocked.
	 */
	private Cell walk(Cell temp, boolean[] checked) {
		int neighborLookDirection;
		Cell wayToTemp = null;

		// Get random neighbor
		neighborLookDirection = rand.nextInt(4);

		switch (neighborLookDirection) {
		case 0:
			// S
			if ((temp.y + 2) < yLength && !maze[temp.x][temp.y + 2].visited) {
				// System.out.println(neighborLookDirection +
				// "=> Not visited: " + temp.x + " " + (temp.y + 2));
				wayToTemp = maze[temp.x][temp.y + 1];
				temp = maze[temp.x][temp.y + 2];

				temp.wall = false;
				temp.visited = true;
				wayToTemp.wall = false;
				backtrack.push(temp);
			}
			checked[0] = true;
			break;
		case 1:
			// O
			if ((temp.x + 2) < xLength && !maze[temp.x + 2][temp.y].visited) {
				// System.out.println(neighborLookDirection +
				// "=> Not visited: " + (temp.x + 2) + " " + temp.y);
				wayToTemp = maze[temp.x + 1][temp.y];
				temp = maze[temp.x + 2][temp.y];

				temp.wall = false;
				temp.visited = true;
				wayToTemp.wall = false;
				backtrack.push(temp);
			}
			checked[1] = true;
			break;
		case 2:
			// N
			if ((temp.y - 2) >= 0 && !maze[temp.x][temp.y - 2].visited) {
				// System.out.println(neighborLookDirection +
				// "=> Not visited: " + temp.x + " " + (temp.y - 2));
				wayToTemp = maze[temp.x][temp.y - 1];
				temp = maze[temp.x][temp.y - 2];

				temp.wall = false;
				temp.visited = true;
				wayToTemp.wall = false;
				backtrack.push(temp);
			}
			checked[2] = true;
			break;
		case 3:
			// W
			if ((temp.x - 2) >= 0 && !maze[temp.x - 2][temp.y].visited) {
				// System.out.println(neighborLookDirection +
				// "=> Not visited: " + (temp.x - 2) + " " + temp.y);
				wayToTemp = maze[temp.x - 1][temp.y];
				temp = maze[temp.x - 2][temp.y];

				temp.wall = false;
				temp.visited = true;
				wayToTemp.wall = false;
				backtrack.push(temp);
			}
			checked[3] = true;
			break;
		}

		return temp;
	}

	/**
	 * Initialize the maze with cells, which are all walls at the beginning.
	 */
	private void initMaze() {
		maze = new Cell[xLength][yLength];

		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < xLength; j++) {
				maze[j][i] = new Cell(j, i);
			}
		}
	}

	/**
	 * Generate a perfect maze. This maze has exactly one way from one point to
	 * another.
	 */
	public void genMaze() {
		initMaze();
		generate();
	}

	/**
	 * Generate a non perfect maze. This maze has multiple ways from one point
	 * to another.
	 */
	public void genNonPerfectMaze() {
		initMaze();
		generate();

		// Set random cells in the maze to wall.
		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < numberOfDeletionsPerLine; j++) {
				int r = rand.nextInt(xLength);
				maze[r][i].wall = false;
			}
		}
	}

	/**
	 * Returns the maze. (Can be perfect or non-perfect).
	 * 
	 * @return The maze (2D Cell array)
	 */
	public Cell[][] getMaze() {
		return maze;
	}

	/**
	 * Returns the maze as a String to display on console.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < xLength; j++) {
				if (maze[j][i].wall) {
					str.append("#");
				} else {
					str.append("o");
				}
			}
			str.append("\n");
		}
		return str.toString();
	}

}
