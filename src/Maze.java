import java.awt.Point;
import java.util.ArrayList;

import javax.xml.ws.Endpoint;

/*
 * James Van Gaasbeck
 * PID: J2686979
 * COP 3503 : Computer Science II
 * Professor Sean Szumlanski
 * Assignment 6 : Maze Creation
 * Due: Sunday, November 17, 11:59pm
 * 
 */
public class Maze {
	public enum Cell_Type {
		NORTH_WEST, WEST, SOUTH_WEST, SOUTH, SOUTH_EAST, EAST, NORTH_EAST, NORTH, CENTER
	}


	public static char[][] create(int width, int height) {

		int horizontalLength = (width * 2) + 1;
		int verticalLength = (height * 2) + 1;

		Cell[][] theCells = new Cell[horizontalLength][verticalLength];
		char[][] theMaze = new char[horizontalLength][verticalLength];

		int cellX = -1;
		for (int x = 0; x < horizontalLength; x++) {
			if (x % 2 == 1) {
				++cellX;
			}
			int cellY = 0;
			for (int y = 0; y < verticalLength; y++) {

				// gets the outer walls of the maze
				if (x == 0 || x == horizontalLength - 1 || y == 0
						|| y == verticalLength - 1) {
					theMaze[x][y] = '#';
					// System.out.println(theMaze[x][x]);
				} else if (x % 2 == 1 && y % 2 == 0) {
					theMaze[x][y] = '#';
				} else if (x % 2 == 0) {
					theMaze[x][y] = '#';
				}
				if (x % 2 == 1 && y % 2 == 1) {
					theCells[x][y] = new Cell(new Point(cellX, cellY++), width,
							height);
					
				}
			}
			// System.out.println();
		}

		for (int x = 0; x < verticalLength; x++) {

			for (int y = 0; y < horizontalLength; y++) {
				System.out.print(theMaze[y][x]);
				//System.out.print(theCells[x][y].sign);
				if (x % 2 == 1 && y % 2 == 1){
					System.out.print(theCells[y][x].sign);
				}
			}

			System.out.println();
		}

		return null;
	}

	public static double difficultyRating() {
		return 3;
	}

	public static double hoursSpent() {
		return 5;
	}

	private static class Wall {

		char sign;
		boolean isDestructable = false;
		Point position;

		Wall(boolean isDestructable) {
			this.sign = '#';
			this.isDestructable = isDestructable;
		}

		@Override
		public String toString() {
			return "" + this.sign;
		}

	}

	private static class CellWalls {
		Wall north, south, east, west;

		CellWalls(Cell_Type type) {
			switch (type) {
			case NORTH_WEST:
				east = new Wall(true);
				south = new Wall(true);
				break;
			case WEST:
				east = new Wall(true);
				south = new Wall(true);
				north = new Wall(true);
				break;
			case SOUTH_WEST:
				east = new Wall(true);
				north = new Wall(true);
				break;
			case SOUTH:
				west = new Wall(true);
				east = new Wall(true);
				south = new Wall(true);
				break;
			case SOUTH_EAST:
				north = new Wall(true);
				west = new Wall(true);
				break;
			case EAST:
				north = new Wall(true);
				south = new Wall(true);
				west = new Wall(true);
				break;
			case NORTH_EAST:
				west = new Wall(true);
				south = new Wall(true);
				break;
			case NORTH:
				west = new Wall(true);
				east = new Wall(true);
				south = new Wall(true);
				break;
			case CENTER:
				east = new Wall(true);
				south = new Wall(true);
				west = new Wall(true);
				north = new Wall(true);
				break;
			default:

				break;
			}
		}

	}

	private static class Cell {

		char sign = ' ';
		Point position;
		boolean isStart = false;
		boolean isEnd = false;
		int parent; // the parent in the disjointSet
		CellWalls cellWalls;
		Cell_Type type;

		Cell(Point position, int width, int height) {
			this.position = position;
			if (this.position.x == 0 && this.position.y == 0) {
				this.isStart = true;
				this.sign = 's';
				this.cellWalls = new CellWalls(Cell_Type.NORTH_EAST);
				this.type = Cell_Type.NORTH_EAST;
			} else if (this.position.x == 0) {
				if (this.position.y == (height - 1)) {
					this.cellWalls = new CellWalls(Cell_Type.SOUTH_WEST);
					this.type = Cell_Type.SOUTH_WEST;
				} else if (this.position.y < (height - 1)
						&& this.position.y > 0) {
					this.cellWalls = new CellWalls(Cell_Type.WEST);
					this.type = Cell_Type.WEST;
				}
			} else if (this.position.x == (width - 1)) {
				if (this.position.y == 0) {
					this.cellWalls = new CellWalls(Cell_Type.NORTH_EAST);
					this.type = Cell_Type.NORTH_EAST;
				} else if (this.position.y == (height - 1)) {
					this.cellWalls = new CellWalls(Cell_Type.SOUTH_EAST);
					this.type = Cell_Type.SOUTH_EAST;
					this.sign = 'e';
					this.isEnd = true;
				} else {
					this.cellWalls = new CellWalls(Cell_Type.EAST);
					this.type = Cell_Type.EAST;
				}
			} else if ((this.position.x > 0 && this.position.x < (width - 1))
					&& this.position.y == 0) {
				this.cellWalls = new CellWalls(Cell_Type.NORTH);
				this.type = Cell_Type.NORTH;
			} else if ((this.position.x > 0 && this.position.x < (width - 1))
					&& this.position.y == (height - 1)) {
				this.cellWalls = new CellWalls(Cell_Type.SOUTH);
				this.type = Cell_Type.SOUTH;
			} else {
				this.cellWalls = new CellWalls(Cell_Type.CENTER);
				this.type = Cell_Type.CENTER;
			}
		}

		@Override
		public String toString() {
			return "Cell: " + position.toString();
		}
	}

	private static class DisjointSet {

	}

	public static void main(String[] args) {
		Maze.create(6, 3);

	}
}
