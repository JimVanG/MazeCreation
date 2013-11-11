import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

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

	static ArrayList<Cell> cellList;

	public static char[][] create(int width, int height) {

		Random randy = new Random();

		int horizontalLength = (width * 2) + 1;
		int verticalLength = (height * 2) + 1;

		Cell[][] theCells = new Cell[horizontalLength][verticalLength];
		char[][] theMaze = new char[horizontalLength][verticalLength];
		cellList = new ArrayList<Cell>();

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
					// top, bottom walls
					theMaze[x][y] = '#';
				} else if (x % 2 == 0) {
					// left, right walls
					theMaze[x][y] = '#';

				}
				if (x % 2 == 1 && y % 2 == 1) {
					// theMaze[x][y] = ' ';
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
					cellList.add(theCells[y][x]);
					// give the cell a number (0 to n-1)
					cellList.get(numberOfCells).cellNumber = numberOfCells;
					// we need to initialize the parent array(cellList), do this
					// by setting the parent property to the current cellNumber,
					// this is essentially the "makeSet()" function of a
					// disjointSet
					cellList.get(numberOfCells).parent = numberOfCells++;
					// DisjointSet.findSet(numberOfCells++);
				}
			}
			System.out.println();
		}
		int randomSeed = width * height;

		do {
			int randomCellWall = randy.nextInt(randomSeed);
			Cell cellsWallToKnockDown = cellList.remove(randomCellWall);
			switch (cellsWallToKnockDown.type) {
			case CENTER:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case NORTH:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case NORTH_EAST:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case EAST:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case SOUTH_EAST:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case SOUTH:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case SOUTH_WEST:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case WEST:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			case NORTH_WEST:
				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					int rightX = w.adjacentCells.x;
					int leftY = w.adjacentCells.y;
					if (DisjointSet.findSet(leftY).parent != DisjointSet
							.findSet(rightX).parent) {
						DisjointSet.union(leftY, rightX);
					}
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());
				break;
			default:
				// shouldn't happen
				break;
			}

		} while (!cellList.isEmpty());

		// DisjointSet.union(0, 1);
		// DisjointSet.union(1, 2);
		// DisjointSet.union(2, 3);
		// DisjointSet.union(3, 4);
		// DisjointSet.union(0, 5);
		// DisjointSet.union(5, 6);
		// DisjointSet.findSet(3);

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
		// point to represent the adjacent cells, (left, right) or (top, bottom)
		Point adjacentCells;
		Cell_Type wallDirection;

		Wall(boolean isDestructable, Point adjacentCells,
				Cell_Type wallDirection) {
			this.sign = '#';
			this.isDestructable = isDestructable;
			this.adjacentCells = adjacentCells;
			this.wallDirection = wallDirection;
		}

		Wall(boolean isDestructable) {
			this.sign = '#';
			this.isDestructable = isDestructable;
			this.adjacentCells = adjacentCells;
		}

		@Override
		public String toString() {
			return "" + this.sign;
		}

	}

	private static class CellWalls {
		Wall north, south, east, west;
		ArrayDeque<Wall> listOfDestructableWalls;
		ArrayList<Wall> walls;

		CellWalls(Cell_Type type, Point currentCellPoint) {
			listOfDestructableWalls = new ArrayDeque<Wall>();

			switch (type) {
			case NORTH_WEST:
				east = new Wall(true, new Point(currentCellPoint.x + 1,
						currentCellPoint.y), Cell_Type.EAST);
				south = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y + 1), Cell_Type.SOUTH);

				north = new Wall(false);
				west = new Wall(false);

				listOfDestructableWalls.add(east);
				listOfDestructableWalls.add(south);
				break;
			case WEST:
				east = new Wall(true, new Point(currentCellPoint.x + 1,
						currentCellPoint.y), Cell_Type.EAST);
				south = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y + 1), Cell_Type.SOUTH);
				north = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y - 1), Cell_Type.NORTH);

				west = new Wall(false);

				listOfDestructableWalls.add(east);
				listOfDestructableWalls.add(south);
				listOfDestructableWalls.add(north);
				break;
			case SOUTH_WEST:
				east = new Wall(true, new Point(currentCellPoint.x + 1,
						currentCellPoint.y), Cell_Type.EAST);
				north = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y - 1), Cell_Type.NORTH);

				south = new Wall(false);
				west = new Wall(false);

				listOfDestructableWalls.add(north);
				listOfDestructableWalls.add(east);
				break;
			case SOUTH:
				west = new Wall(true, new Point(currentCellPoint.x - 1,
						currentCellPoint.y), Cell_Type.WEST);
				east = new Wall(true, new Point(currentCellPoint.x + 1,
						currentCellPoint.y), Cell_Type.EAST);
				north = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y - 1), Cell_Type.NORTH);

				south = new Wall(false);

				listOfDestructableWalls.add(north);
				listOfDestructableWalls.add(east);
				listOfDestructableWalls.add(west);

				break;
			case SOUTH_EAST:
				north = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y - 1), Cell_Type.NORTH);
				west = new Wall(true, new Point(currentCellPoint.x - 1,
						currentCellPoint.y), Cell_Type.WEST);

				south = new Wall(false);
				east = new Wall(false);

				listOfDestructableWalls.add(west);
				listOfDestructableWalls.add(north);
				break;
			case EAST:
				north = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y - 1), Cell_Type.NORTH);
				south = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y + 1), Cell_Type.SOUTH);
				west = new Wall(true, new Point(currentCellPoint.x - 1,
						currentCellPoint.y), Cell_Type.WEST);

				east = new Wall(false);

				listOfDestructableWalls.add(west);
				listOfDestructableWalls.add(north);
				listOfDestructableWalls.add(south);
				break;
			case NORTH_EAST:
				south = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y + 1), Cell_Type.SOUTH);
				west = new Wall(true, new Point(currentCellPoint.x - 1,
						currentCellPoint.y), Cell_Type.WEST);

				north = new Wall(false);
				east = new Wall(false);

				listOfDestructableWalls.add(south);
				listOfDestructableWalls.add(west);
				break;
			case NORTH:
				south = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y + 1), Cell_Type.SOUTH);
				west = new Wall(true, new Point(currentCellPoint.x - 1,
						currentCellPoint.y), Cell_Type.WEST);
				east = new Wall(true, new Point(currentCellPoint.x + 1,
						currentCellPoint.y), Cell_Type.EAST);

				north = new Wall(false);

				listOfDestructableWalls.add(south);
				listOfDestructableWalls.add(west);
				listOfDestructableWalls.add(east);
				break;
			case CENTER:
				south = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y + 1), Cell_Type.SOUTH);
				west = new Wall(true, new Point(currentCellPoint.x - 1,
						currentCellPoint.y), Cell_Type.WEST);
				east = new Wall(true, new Point(currentCellPoint.x + 1,
						currentCellPoint.y), Cell_Type.EAST);
				north = new Wall(true, new Point(currentCellPoint.x,
						currentCellPoint.y - 1), Cell_Type.NORTH);

				listOfDestructableWalls.add(south);
				listOfDestructableWalls.add(west);
				listOfDestructableWalls.add(east);
				listOfDestructableWalls.add(north);
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
				this.cellWalls = new CellWalls(Cell_Type.NORTH_WEST,
						this.position);
				this.type = Cell_Type.NORTH_WEST;
			} else if (this.position.x == 0) {
				if (this.position.y == (height - 1)) {
					this.cellWalls = new CellWalls(Cell_Type.SOUTH_WEST,
							this.position);
					this.type = Cell_Type.SOUTH_WEST;
				} else if (this.position.y < (height - 1)
						&& this.position.y > 0) {
					this.cellWalls = new CellWalls(Cell_Type.WEST,
							this.position);
					this.type = Cell_Type.WEST;
				}
			} else if (this.position.x == (width - 1)) {
				if (this.position.y == 0) {
					this.cellWalls = new CellWalls(Cell_Type.NORTH_EAST,
							this.position);
					this.type = Cell_Type.NORTH_EAST;
				} else if (this.position.y == (height - 1)) {
					this.cellWalls = new CellWalls(Cell_Type.SOUTH_EAST,
							this.position);
					this.type = Cell_Type.SOUTH_EAST;
					this.sign = 'e';
					this.isEnd = true;
				} else {
					this.cellWalls = new CellWalls(Cell_Type.EAST,
							this.position);
					this.type = Cell_Type.EAST;
				}
			} else if ((this.position.x > 0 && this.position.x < (width - 1))
					&& this.position.y == 0) {
				this.cellWalls = new CellWalls(Cell_Type.NORTH, this.position);
				this.type = Cell_Type.NORTH;
			} else if ((this.position.x > 0 && this.position.x < (width - 1))
					&& this.position.y == (height - 1)) {
				this.cellWalls = new CellWalls(Cell_Type.SOUTH, this.position);
				this.type = Cell_Type.SOUTH;
			} else {
				this.cellWalls = new CellWalls(Cell_Type.CENTER, this.position);
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
			if (cellList.get(x).parent == x) {
				// System.out.println("FindSet: Cell " + cellList[x].cellNumber
				// + "'s parent is: " + cellList[x].parent);
				return cellList.get(x);
			}

			cellList.get(x).parent = findSet(cellList.get(x).parent).cellNumber;
			return findSet(cellList.get(x).parent);
		}

		private static void union(int x, int y) {

			Cell parentX = findSet(x);
			Cell parentY = findSet(y);

			if (parentX.rank < parentY.rank) {
				cellList.get(parentX.cellNumber).parent = parentY.cellNumber;
			} else if (parentY.rank < parentX.rank) {
				cellList.get(parentY.cellNumber).parent = parentX.cellNumber;
			} else {
				cellList.get(parentY.cellNumber).parent = parentX.cellNumber;
				cellList.get(parentX.cellNumber).rank++;
			}

			cellList.get(findSet(y).cellNumber).parent = findSet(x).cellNumber;
			// System.out.println("UNION: Cell " + cellList[y].cellNumber
			// + "'s parent is now " + cellList[x].cellNumber);
		}

	}

	public static void main(String[] args) {
		Maze.create(6, 3);

	}
}
