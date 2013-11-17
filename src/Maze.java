import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
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
	public static int heightOfMaze;
	public static int horizontalLength;
	public static int verticalLength;
	public static HashSet<Cell> setOfUnionedCells;
	public static HashSet<Point> setOfWallsKnockedDown;

	public static char[][] create(int width, int height) {
		horizontalLength = (width * 2) + 1;
		verticalLength = (height * 2) + 1;

		if (width == 0 || height == 0) {
			return null;
		}

		if (width == 1 || height == 1) {// if there was an input of width or
										// height equal to one.
			char weirdMaze[][] = null;
			if (width == 1 && height != 1) {
				weirdMaze = new char[verticalLength][3];
				for (int x = 0; x < verticalLength; x++) {
					for (int y = 0; y < 3; y++) {
						if (x == 0 || y == 0 || y == (2)
								|| x == (verticalLength - 1)) {
							weirdMaze[x][y] = '#';
						} else if (x % 2 == 0 && y % 2 == 1) {
							weirdMaze[x][y] = ' ';
						} else if (x % 2 == 1 && y % 2 == 1) {
							if (x == 1 && y == 1) {
								weirdMaze[x][y] = 's';
							} else if (x == verticalLength - 2 && y == 1) {
								weirdMaze[x][y] = 'e';
							} else {
								weirdMaze[x][y] = ' ';
							}
						}
					}
				}

			} else if (width != 1 && height == 1) {
				weirdMaze = new char[3][horizontalLength];
				for (int x = 0; x < 3; x++) {
					for (int y = 0; y < horizontalLength; y++) {
						if (x == 0 || y == 0 || y == (horizontalLength - 1)
								|| x == (2)) {
							weirdMaze[x][y] = '#';
							//System.out.print(weirdMaze[x][y]);
						} else if (x % 2 == 1 && y % 2 == 0) {
							weirdMaze[x][y] = ' ';
							//System.out.print(weirdMaze[x][y]);
						} else if (x % 2 == 1 && y % 2 == 1) {
							if (x == 1 && y == 1) {
								weirdMaze[x][y] = 's';
								//System.out.print(weirdMaze[x][y]);
							} else if (y == horizontalLength - 2 && x == 1) {
								weirdMaze[x][y] = 'e';
								//System.out.print(weirdMaze[x][y]);
							} else if (x == 1) {
								weirdMaze[x][y] = ' ';
								//System.out.print(weirdMaze[x][y]);
							}
						}
					}
					//System.out.println();
				}
			}
			return weirdMaze;

		} else {
			Random randy = new Random();
			setOfUnionedCells = new HashSet<Cell>();
			Cell[][] theCells = new Cell[horizontalLength][verticalLength];
			char[][] theMaze = new char[horizontalLength][verticalLength];
			cellList = new ArrayList<Cell>();
			heightOfMaze = height;

			int cellX = -1;
			int numberOfCells = 0;
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
						theCells[x][y] = new Cell(new Point(cellX, cellY++),
								width, height, numberOfCells++);
					}
				}
			}

			numberOfCells = 0;
			for (int x = 0; x < horizontalLength; x++) {
				for (int y = 0; y < verticalLength; y++) {
					if (x % 2 == 1 && y % 2 == 1) {
						// add the cell to our array of cells (the array will be
						// used for the DisjointSet)
						cellList.add(theCells[x][y]);
						cellList.get(numberOfCells).parent = numberOfCells++;
					}
				}
			}

			int randomSeed = width * height;
			int listTicker = randomSeed - 1;

			while (listTicker > 0) {
				int randomCellWall = randy.nextInt(randomSeed);
				Cell cellsWallToKnockDown = cellList.get(randomCellWall);

				while (setOfUnionedCells.contains(cellsWallToKnockDown)) {
					randomCellWall = randy.nextInt(randomSeed);
					cellsWallToKnockDown = cellList.get(randomCellWall);
				}
				// randomSeed--;
				listTicker--;
				// cellsWallToKnockDown.isDirty = true;
				setOfUnionedCells.add(cellsWallToKnockDown);

				// for(Wall w:
				// cellsWallToKnockDown.cellWalls.listOfDestructableWalls){
				// if(w.sign )
				// }

				do {
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();


					int currentCellX = w.adjacentCells.x;
					int otherCellY = w.adjacentCells.y;
					if ((w.sign == '#')
							&& (DisjointSet.findSet(currentCellX).parent != DisjointSet
									.findSet(otherCellY).parent)) {
						DisjointSet.union(currentCellX, otherCellY);
						w.sign = ' ';
						switch (w.wallDirection) {
						case EAST:
							cellList.get(otherCellY).cellWalls.west.sign = ' ';
							break;
						case SOUTH:
							cellList.get(otherCellY).cellWalls.north.sign = ' ';
							break;
						case WEST:
							cellList.get(otherCellY).cellWalls.east.sign = ' ';
							break;
						case NORTH:
							cellList.get(otherCellY).cellWalls.south.sign = ' ';
							break;
						default:
							// shouldn't happen
							break;
						}
						// break;
					}

				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());

			} // closes outer while()

			int bottomWall = -1;
			int rightWall = 0;
			int cells = 0;
			// trying to convert to a char[][] array...
			char[][] finalMaze = new char[horizontalLength][verticalLength];
			for (int x = 0; x < (horizontalLength); x++) {
				bottomWall = cells;
				for (int y = 0; y < (verticalLength); y++) {
					if (x == 0 || y == 0 || x == horizontalLength - 1
							|| y == verticalLength - 1) {
						finalMaze[x][y] = '#';
					} else if (x % 2 == 1) {
						if (y % 2 == 1) {
							finalMaze[x][y] = cellList.get(cells++).sign;
						} else {
							finalMaze[x][y] = cellList.get(bottomWall++).cellWalls.south.sign;
						}
					} else if (x % 2 == 0) {
						if (y == 0) {
							rightWall = cells - 1;
						}
						if (y % 2 == 1) {
							finalMaze[x][y] = cellList.get(rightWall++).cellWalls.east.sign;
						} else {
							finalMaze[x][y] = '#';
						}
					}
				}
			}

			char[][] theRealFinalMaze = new char[verticalLength][horizontalLength];
			// System.out.println("Changing the maze:");
			for (int y = 0; y < (verticalLength); y++) {
				for (int x = 0; x < (horizontalLength); x++) {
					// System.out.print(finalMaze[x][y]);
					theRealFinalMaze[y][x] = finalMaze[x][y];

				}
				// System.out.println();
			}

			return theRealFinalMaze;
		}
	}

	public static double difficultyRating() {
		return 4.1;
	}

	public static double hoursSpent() {
		return 15;
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
			return "" + this.wallDirection;
		}

	}

	private static class CellWalls {
		Wall north, south, east, west;
		ArrayDeque<Wall> listOfDestructableWalls;
		ArrayList<Wall> walls;
		Point eastCell, southCell, westCell, northCell;

		CellWalls(Cell_Type type, int currentCellNumber) {
			listOfDestructableWalls = new ArrayDeque<Wall>();
			int eastPoint = (currentCellNumber + heightOfMaze);
			int southPoint = (currentCellNumber + 1);
			int westPoint = (currentCellNumber - heightOfMaze);
			int northPoint = (currentCellNumber - 1);
			this.eastCell = new Point(currentCellNumber, eastPoint);
			this.westCell = new Point(currentCellNumber, westPoint);
			this.northCell = new Point(currentCellNumber, northPoint);
			this.southCell = new Point(currentCellNumber, southPoint);

			// need to fix this. The adjacent cells should be: West=
			// cellNumber--, North=cellNumber-width? East=cellNumber++,
			// South=cellNumber+width?
			switch (type) {
			case NORTH_WEST:
				east = new Wall(true, this.eastCell, Cell_Type.EAST);
				south = new Wall(true, this.southCell, Cell_Type.SOUTH);

				north = new Wall(false);
				west = new Wall(false);

				listOfDestructableWalls.add(east);
				listOfDestructableWalls.add(south);
				break;
			case WEST:
				east = new Wall(true, this.eastCell, Cell_Type.EAST);
				south = new Wall(true, this.southCell, Cell_Type.SOUTH);
				north = new Wall(true, this.northCell, Cell_Type.NORTH);

				west = new Wall(false);

				listOfDestructableWalls.add(east);
				listOfDestructableWalls.add(south);
				listOfDestructableWalls.add(north);
				break;
			case SOUTH_WEST:
				east = new Wall(true, this.eastCell, Cell_Type.EAST);
				north = new Wall(true, this.northCell, Cell_Type.NORTH);

				south = new Wall(false);
				west = new Wall(false);

				listOfDestructableWalls.add(north);
				listOfDestructableWalls.add(east);
				break;
			case SOUTH:
				west = new Wall(true, this.westCell, Cell_Type.WEST);
				east = new Wall(true, this.eastCell, Cell_Type.EAST);
				north = new Wall(true, this.northCell, Cell_Type.NORTH);

				south = new Wall(false);

				listOfDestructableWalls.add(north);
				listOfDestructableWalls.add(east);
				listOfDestructableWalls.add(west);

				break;
			case SOUTH_EAST:
				north = new Wall(true, this.northCell, Cell_Type.NORTH);
				west = new Wall(true, this.westCell, Cell_Type.WEST);

				south = new Wall(false);
				east = new Wall(false);

				listOfDestructableWalls.add(west);
				listOfDestructableWalls.add(north);
				break;
			case EAST:
				north = new Wall(true, this.northCell, Cell_Type.NORTH);
				west = new Wall(true, this.westCell, Cell_Type.WEST);
				south = new Wall(true, this.southCell, Cell_Type.SOUTH);

				east = new Wall(false);

				listOfDestructableWalls.add(west);
				listOfDestructableWalls.add(north);
				listOfDestructableWalls.add(south);
				break;
			case NORTH_EAST:
				west = new Wall(true, this.westCell, Cell_Type.WEST);
				south = new Wall(true, this.southCell, Cell_Type.SOUTH);

				north = new Wall(false);
				east = new Wall(false);

				listOfDestructableWalls.add(south);
				listOfDestructableWalls.add(west);
				break;
			case NORTH:
				west = new Wall(true, this.westCell, Cell_Type.WEST);
				south = new Wall(true, this.southCell, Cell_Type.SOUTH);
				east = new Wall(true, this.eastCell, Cell_Type.EAST);

				north = new Wall(false);

				listOfDestructableWalls.add(south);
				listOfDestructableWalls.add(west);
				listOfDestructableWalls.add(east);
				break;
			case CENTER:
				west = new Wall(true, this.westCell, Cell_Type.WEST);
				south = new Wall(true, this.southCell, Cell_Type.SOUTH);
				east = new Wall(true, this.eastCell, Cell_Type.EAST);
				north = new Wall(true, this.northCell, Cell_Type.NORTH);

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
		boolean isDirty = false;
		int parent; // the parent in the disjointSet
		int rank = 0; // the rank of the current node/tree
		CellWalls cellWalls;
		Cell_Type type;

		Cell(Point position, int width, int height, int cellNumber) {
			this.position = position;
			this.cellNumber = cellNumber;
			if (this.position.x == 0 && this.position.y == 0) {
				this.isStart = true;
				this.sign = 's';
				this.cellWalls = new CellWalls(Cell_Type.NORTH_WEST,
						this.cellNumber);
				this.type = Cell_Type.NORTH_WEST;
			} else if (this.position.x == 0) {
				if (this.position.y == (height - 1)) {
					this.cellWalls = new CellWalls(Cell_Type.SOUTH_WEST,
							this.cellNumber);
					this.type = Cell_Type.SOUTH_WEST;
				} else if (this.position.y < (height - 1)
						&& this.position.y > 0) {
					this.cellWalls = new CellWalls(Cell_Type.WEST,
							this.cellNumber);
					this.type = Cell_Type.WEST;
				}
			} else if (this.position.x == (width - 1)) {
				if (this.position.y == 0) {
					this.cellWalls = new CellWalls(Cell_Type.NORTH_EAST,
							this.cellNumber);
					this.type = Cell_Type.NORTH_EAST;
				} else if (this.position.y == (height - 1)) {
					this.cellWalls = new CellWalls(Cell_Type.SOUTH_EAST,
							this.cellNumber);
					this.type = Cell_Type.SOUTH_EAST;
					this.sign = 'e';
					this.isEnd = true;
				} else {
					this.cellWalls = new CellWalls(Cell_Type.EAST,
							this.cellNumber);
					this.type = Cell_Type.EAST;
				}
			} else if ((this.position.x > 0 && this.position.x < (width - 1))
					&& this.position.y == 0) {
				this.cellWalls = new CellWalls(Cell_Type.NORTH, this.cellNumber);
				this.type = Cell_Type.NORTH;
			} else if ((this.position.x > 0 && this.position.x < (width - 1))
					&& this.position.y == (height - 1)) {
				this.cellWalls = new CellWalls(Cell_Type.SOUTH, this.cellNumber);
				this.type = Cell_Type.SOUTH;
			} else {
				this.cellWalls = new CellWalls(Cell_Type.CENTER,
						this.cellNumber);
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

		long startTime = System.nanoTime();
		char[][] done = Maze.create(6,3);

		// System.out.println(done.length);
		// System.out.println(done[0].length);

		if (done != null) {
			System.out.println("Returned Maze:");
			for (int x = 0; x < done.length; x++) {
				for (int y = 0; y < done[0].length; y++) {
					System.out.print(done[x][y]);
				}
				System.out.println();
			}
			long endTime = System.nanoTime();
			System.out.println("Took " + (endTime - startTime) + " ns");

		}
	}
}
