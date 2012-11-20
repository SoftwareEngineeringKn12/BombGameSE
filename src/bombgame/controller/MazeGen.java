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
	public static final int STANDARD_X_LENGTH = 60;

	/**
	 * y-length of the maze when standard constructor is used.
	 */
	public static final int STANDARD_Y_LENGTH = 15;

	/**
	 * Multiplied with x-length to calculate number of deletions per line
	 * relative to x-length of the maze.
	 */
	public static final double NUMBER_DEL_FACTOR = 0.4;

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
		 * 
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

	/**
	 * Generates the maze. Loops through the cells until all cells are checked.
	 * At each cell generate() chooses a random direction and try´s if there is
	 * a free and non-visited cell.
	 */
	private void generate() {
		// 0 = N, 1 = O, 2 = S, 3 = W
		boolean foundWay = false;
		boolean allChecked = false;
		boolean checked[] = new boolean[4];
		Cell startCell, temp;

		// Random start cell
		temp = maze[rand.nextInt(xLength)][rand.nextInt(yLength)];
		temp.visited = true;
		temp.wall = false;
		backtrack.push(temp);
		startCell = temp;

		do {
			// Reset switches
			foundWay = false;
			allChecked = false;
			checked[0] = false;
			checked[1] = false;
			checked[2] = false;
			checked[3] = false;

			// Loop for one Cell
			while (!foundWay && !allChecked) {

				// Check if theres a way
				if (!temp.equals(temp = tryDirection(temp, checked))) {
					foundWay = true;
				}

				if (!foundWay) {
					if (checked[0] && checked[1] && checked[2] && checked[3]) {
						// System.out.println("All checked: No way, go back.");
						allChecked = true;
						temp = backtrack.pop();
					}
				}
			}
		} while (!startCell.equals(temp));
	}

	/**
	 * Checks if there is a not visited cell at the 2nd next cell. If yes and it
	 * is not the end of the maze field, go to this cell, make it a path (remove
	 * the wall) and remove the wall between it. Than push this cell to the
	 * stack (for backtracking).
	 * 
	 * @param temp
	 *            - Actual cell
	 * @param checked
	 *            - Indicates which direction is already checked
	 * @return Next actual cell if found way, old cell if the path in this
	 *         direction is blocked.
	 */
	private Cell tryDirection(Cell temp, boolean[] checked) {
		int neighborLookDirection;
		Cell wayToTemp = null;
		Cell tryDirection = null;

		// Get random neighbor
		neighborLookDirection = rand.nextInt(4);

		// Could have used normal integers to store the coordinates,
		// but its cooler and easier in that way.
		switch (neighborLookDirection) {
		case 0:
			// S
			tryDirection = new Cell(temp.x, temp.y + 2);
			wayToTemp = new Cell(temp.x, temp.y + 1);
			checked[0] = true;
			break;
		case 1:
			// O
			tryDirection = new Cell(temp.x + 2, temp.y);
			wayToTemp = new Cell(temp.x + 1, temp.y);
			checked[1] = true;
			break;
		case 2:
			// N
			tryDirection = new Cell(temp.x, temp.y - 2);
			wayToTemp = new Cell(temp.x, temp.y - 1);
			checked[2] = true;
			break;
		case 3:
			// W
			tryDirection = new Cell(temp.x - 2, temp.y);
			wayToTemp = new Cell(temp.x - 1, temp.y);
			checked[3] = true;
			break;
		}

		if (isInMazeField(tryDirection)) {
			// If not checked indexing would throw exception
			if (!maze[tryDirection.x][tryDirection.y].visited) {
				maze[wayToTemp.x][wayToTemp.y].wall = false;

				temp = maze[tryDirection.x][tryDirection.y];
				temp.wall = false;
				temp.visited = true;

				backtrack.push(temp);
			}
		}

		return temp;
	}

	/**
	 * Checks if the cell´s coordinates are in the maze field. Is used in
	 * tryDirection() to
	 * 
	 * @param tryDirection
	 * @return
	 */
	private boolean isInMazeField(Cell cell) {
		if (cell.x < xLength && cell.x >= 0 && cell.y < yLength && cell.y >= 0)
			return true;
		return false;
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
	 * For unit tests
	 * 
	 * @return numberOfDeletionsPerLine
	 */
	public int getNumberOfDeletionsPerLine() {
		return numberOfDeletionsPerLine;
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
