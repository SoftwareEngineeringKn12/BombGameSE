package bombgame.controller;

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

	private int xLength;
	private int yLength;
	private int numberOfDeletionsPerLine;
	private Cell maze[][];
	private LinkedList<Cell> backtrack = new LinkedList<Cell>();
	private Random rand = new Random();

	static class Cell {
		public int x;
		public int y;

		// Cell is a wall at the beginning
		public boolean wall = true;
		public boolean visited = false;

		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public MazeGen() {
		this(60, 15);
	}

	public MazeGen(int xLength, int yLength) {
		this.xLength = xLength;
		this.yLength = yLength;
		numberOfDeletionsPerLine = (int) (xLength * 0.4);
	}

	private void generate() {
		// 0 = N, 1 = O, 2 = S, 3 = W
		int neighborLookDirection;
		boolean foundWay = false;
		boolean allChecked = false;
		boolean checked[] = new boolean[4];
		Cell startCell, temp, wayToTemp = null;

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

				// Get random neighbor
				neighborLookDirection = rand.nextInt(4);

				// System.out.println("Temp Cell is at: " + temp.x + " " +
				// temp.y);
				// System.out.println("neighborLookDirection: " +
				// neighborLookDirection);

				switch (neighborLookDirection) {
				case 0: // S
					if ((temp.y + 2) < yLength
							&& !maze[temp.x][temp.y + 2].visited) {
						// System.out.println(neighborLookDirection +
						// "=> Not visited: " + temp.x + " " + (temp.y + 2));
						wayToTemp = maze[temp.x][temp.y + 1];
						temp = maze[temp.x][temp.y + 2];

						foundWay = true;
						temp.wall = false;
						temp.visited = true;
						wayToTemp.wall = false;
						backtrack.push(temp);
					}
					checked[0] = true;
					break;
				case 1: // O
					if ((temp.x + 2) < xLength
							&& !maze[temp.x + 2][temp.y].visited) {
						// System.out.println(neighborLookDirection +
						// "=> Not visited: " + (temp.x + 2) + " " + temp.y);
						wayToTemp = maze[temp.x + 1][temp.y];
						temp = maze[temp.x + 2][temp.y];

						foundWay = true;
						temp.wall = false;
						temp.visited = true;
						wayToTemp.wall = false;
						backtrack.push(temp);
					}
					checked[1] = true;
					break;
				case 2: // N
					if ((temp.y - 2) >= 0 && !maze[temp.x][temp.y - 2].visited) {
						// System.out.println(neighborLookDirection +
						// "=> Not visited: " + temp.x + " " + (temp.y - 2));
						wayToTemp = maze[temp.x][temp.y - 1];
						temp = maze[temp.x][temp.y - 2];

						foundWay = true;
						temp.wall = false;
						temp.visited = true;
						wayToTemp.wall = false;
						backtrack.push(temp);
					}
					checked[2] = true;
					break;
				case 3: // W
					if ((temp.x - 2) >= 0 && !maze[temp.x - 2][temp.y].visited) {
						// System.out.println(neighborLookDirection +
						// "=> Not visited: " + (temp.x - 2) + " " + temp.y);
						wayToTemp = maze[temp.x - 1][temp.y];
						temp = maze[temp.x - 2][temp.y];

						foundWay = true;
						temp.wall = false;
						temp.visited = true;
						wayToTemp.wall = false;
						backtrack.push(temp);
					}
					checked[3] = true;
					break;
				}

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
		} while (startCell != temp);
	}

	private void initMaze() {
		maze = new Cell[xLength][yLength];
		// Init array
		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < xLength; j++) {
				maze[j][i] = new Cell(j, i);
			}
		}
	}

	public void genMaze() {
		initMaze();
		generate();
	}

	public void genNonPerfectMaze() {
		initMaze();
		generate();
		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < numberOfDeletionsPerLine; j++) {
				int r = rand.nextInt(xLength);
				maze[r][i].wall = false;
			}
		}
	}

	public Cell[][] getMaze() {
		return maze;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < yLength; i++) {
			for (int j = 0; j < xLength; j++) {
				if (maze[j][i].wall)
					str.append("#");
				else
					str.append("o");
			}
			str.append("\n");
		}
		return str.toString();
	}
}
