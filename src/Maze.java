import java.awt.Point;

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

	static Cell[] cellList;

	public static char[][] create(int width, int height) {

		int horizontalLength = (width * 2) + 1;
		int verticalLength = (height * 2) + 1;

		Cell[][] theCells = new Cell[horizontalLength][verticalLength];
		char[][] theMaze = new char[horizontalLength][verticalLength];
		cellList = new Cell[width * height];

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
		}

		// print out the walls/cells, and add the cells to an array.
		int numberOfCells = 0;
		for (int x = 0; x < verticalLength; x++) {

			for (int y = 0; y < horizontalLength; y++) {
				System.out.print(theMaze[y][x]);
				if (x % 2 == 1 && y % 2 == 1) {
					// print the cell to make the maze complete
					System.out.print(theCells[y][x].sign);
					// add the cell to our array of cells (the array will be
					// used for the DisjointSet)
					cellList[numberOfCells] = theCells[y][x];
					// give the cell a number (0 to n-1)
					cellList[numberOfCells].cellNumber = numberOfCells;
					// we need to initialize the parent array(cellList), do this
					// by setting the parent property to the current cellNumber,
					// this is essentially the "makeSet()" function of a
					// disjointSet
					cellList[numberOfCells].parent = numberOfCells;
					DisjointSet.findSet(numberOfCells++);
				}
			}
			System.out.println();
		}

		DisjointSet.union(0, 1);
		DisjointSet.union(1, 2);
		DisjointSet.union(2, 3);
		DisjointSet.union(3, 4);
		DisjointSet.union(0, 5);
		DisjointSet.union(5, 6);
		DisjointSet.findSet(3);

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

				north = new Wall(false);
				west = new Wall(false);
				break;
			case WEST:
				east = new Wall(true);
				south = new Wall(true);
				north = new Wall(true);

				west = new Wall(false);
				break;
			case SOUTH_WEST:
				east = new Wall(true);
				north = new Wall(true);

				south = new Wall(false);
				west = new Wall(false);
				break;
			case SOUTH:
				west = new Wall(true);
				east = new Wall(true);
				north = new Wall(true);

				south = new Wall(false);
				break;
			case SOUTH_EAST:
				north = new Wall(true);
				west = new Wall(true);

				south = new Wall(false);
				east = new Wall(false);
				break;
			case EAST:
				north = new Wall(true);
				south = new Wall(true);
				west = new Wall(true);

				east = new Wall(false);
				break;
			case NORTH_EAST:
				west = new Wall(true);
				south = new Wall(true);

				north = new Wall(false);
				east = new Wall(false);
				break;
			case NORTH:
				west = new Wall(true);
				east = new Wall(true);
				south = new Wall(true);

				north = new Wall(false);
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

		@Override
		public String toString() {
			// show what walls can be destroyed
			return "North:" + this.north.isDestructable + ", East:"
					+ this.east.isDestructable + ", South:"
					+ this.south.isDestructable + ", West:"
					+ this.west.isDestructable;
		}
	}

	private static class Cell {

		char sign = ' ';
		Point position;
		int cellNumber;
		boolean isStart = false;
		boolean isEnd = false;
		int parent; // the parent in the disjointSet
		int rank = 0; // the rank of the current node/tree
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
			return "Cell: " + position.toString() + cellWalls.toString();
		}
	}

	private static class DisjointSet {

		// no makeSet() function because i make the set independent of the
		// DisjointSet class.

		private static Cell findSet(int x) {
			if (cellList[x].parent == x) {
				System.out.println("FindSet: Cell " + cellList[x].cellNumber
						+ "'s parent is: " + cellList[x].parent);
				return cellList[x];
			}

			cellList[x].parent = findSet(cellList[x].parent).cellNumber;
			return findSet(cellList[x].parent);
		}

		private static void union(int x, int y) {

			Cell parentX = findSet(x);
			Cell parentY = findSet(y);

			if (parentX.rank < parentY.rank) {
				cellList[parentX.cellNumber].parent = parentY.cellNumber;
			} else if (parentY.rank < parentX.rank) {
				cellList[parentY.cellNumber].parent = parentX.cellNumber;
			} else {
				cellList[parentY.cellNumber].parent = parentX.cellNumber;
				cellList[parentX.cellNumber].rank++;
			}

			cellList[findSet(y).cellNumber].parent = findSet(x).cellNumber;
			System.out.println("UNION: Cell " + cellList[y].cellNumber
					+ "'s parent is now " + cellList[x].cellNumber);
		}

	}

	public static void main(String[] args) {
		Maze.create(6, 3);

	}
}
