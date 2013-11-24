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

	// enums for the type of cell or cell wall. This corrsponds to its location
	// in the maze.
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

		// return nothing if there is a zero, because it wouldn't be a valid
		// maze then.
		if (width == 0 || height == 0) {
			return null;
		}
		if (width == 1 && height == 1) {
			char weirdMaze[][] = new char[3][3];

			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (x == 0 || y == 0 || x == 2 || y == 2) {
						weirdMaze[x][y] = '#';
					} else {
						weirdMaze[x][y] = 'e';
					}
				}
			}
			return weirdMaze;
		} else if (width == 1 || height == 1) {// if there was an input of width
												// or
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
							// System.out.print(weirdMaze[x][y]);
						} else if (x % 2 == 1 && y % 2 == 0) {
							weirdMaze[x][y] = ' ';
							// System.out.print(weirdMaze[x][y]);
						} else if (x % 2 == 1 && y % 2 == 1) {
							if (x == 1 && y == 1) {
								weirdMaze[x][y] = 's';
								// System.out.print(weirdMaze[x][y]);
							} else if (y == horizontalLength - 2 && x == 1) {
								weirdMaze[x][y] = 'e';
								// System.out.print(weirdMaze[x][y]);
							} else if (x == 1) {
								weirdMaze[x][y] = ' ';
								// System.out.print(weirdMaze[x][y]);
							}
						}
					}
					// System.out.println();
				}
			}
			return weirdMaze;

		} else {
			// This is really messy because of the way I started the program, I
			// initially started off with the maze's (width and height)
			// variables swapped, so the maze was reversed from what it should
			// of been.
			Random randy = new Random();
			setOfUnionedCells = new HashSet<Cell>();
			Cell[][] theCells = new Cell[horizontalLength][verticalLength];
			char[][] theMaze = new char[horizontalLength][verticalLength];
			cellList = new ArrayList<Cell>();
			heightOfMaze = height;

			// fill up an array representing our cells
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

			// fill up a list of our cells, this is used at the 'parent array'
			// in the disjoing set.
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

			// this is where all of the logic for the maze creation algorithm
			// is.
			int randomSeed = width * height;
			int listTicker = randomSeed - 1;

			// keep going until we've unioned every cell
			while (listTicker > 0) {
				// get a random number so we can get a random cell
				int randomCellWall = randy.nextInt(randomSeed);
				Cell cellsWallToKnockDown = cellList.get(randomCellWall);

				// keep trying to get a random cell if the cell we are grabbing
				// has already been unioned
				while (setOfUnionedCells.contains(cellsWallToKnockDown)) {
					randomCellWall = randy.nextInt(randomSeed);
					cellsWallToKnockDown = cellList.get(randomCellWall);
				}
				listTicker--;
				// cellsWallToKnockDown.isDirty = true;
				setOfUnionedCells.add(cellsWallToKnockDown);

				// for(Wall w:
				// cellsWallToKnockDown.cellWalls.listOfDestructableWalls){
				// if(w.sign )
				// }

				// so we grabbed a cell that isn't in the same set as the rest.
				// So that means we should check each one it's walls and
				// adjacent cells to see if we can union them into the main set.
				do {
					// get a destructable wall from the cells queue of
					// destructable walls.
					Wall w = cellsWallToKnockDown.cellWalls.listOfDestructableWalls
							.removeFirst();
					// get the current cellsNumbers and the cells number that is
					// adjacent to the cell we are talking about (on the other
					// side of the wall)
					int currentCellX = w.adjacentCells.x;
					int otherCellY = w.adjacentCells.y;
					// if the wall we have just grabbed has adjacent cells that
					// are not in the main set then lets remove the wall between
					// them (the current wall).
					if ((w.sign == '#')
							&& (DisjointSet.findSet(currentCellX).parent != DisjointSet
									.findSet(otherCellY).parent)) {

						DisjointSet.union(currentCellX, otherCellY);
						// remove the wall
						w.sign = ' ';
						// because of the way I set up the program we need to
						// not only remove the wall from current cell we are
						// dealing with, we need to remove wall from the
						// 'otherCell' (the adjacent cell) to make things work.
						switch (w.wallDirection) {
						// if we just removed the current cells east wall, we
						// need to remove the otherCells west wall.
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
						// setOfUnionedCells.add(cellList.get(otherCellY));
					}

					// keep grabbing and destroying the walls while the list
					// isn't empty.
				} while (!cellsWallToKnockDown.cellWalls.listOfDestructableWalls
						.isEmpty());

			} // closes outer while()

			// now i need to convert the ArrayList of cells into a 2D array of
			// chars.. I'm not even going to go into the logic to transfer a
			// arraylist into a 2D array of chars...it took a lot of trial and
			// error.
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
			// whoops! i started out wrong and made the initial arrays [rows]
			// and [columns] swapped! That means we have to switch the rows and
			// columns in the current char[][] array to make it work. Because if
			// i were to keep it the way it is everything would be backwards and
			// switch, everything would still be a valid maze, but everything
			// would be backwards and wrong i guess.
			char[][] theRealFinalMaze = new char[verticalLength][horizontalLength];
			// System.out.println("Changing the maze:");
			for (int y = 0; y < (verticalLength); y++) {
				for (int x = 0; x < (horizontalLength); x++) {
					// System.out.print(finalMaze[x][y]);
					theRealFinalMaze[y][x] = finalMaze[x][y];
				}
				// System.out.println();
			}
			// return the REAL correct maze...
			return theRealFinalMaze;
		}
	}

	public static double difficultyRating() {
		// this was hard and annoying
		return 4.1;
	}

	public static double hoursSpent() {
		// put way too much time into this.
		return 15;
	}

	// class to represent a wall
	private static class Wall {

		// will either be a '#' or a ' '
		char sign;
		boolean isDestructable = false;
		// point to represent the adjacent cells.
		// x == currentCell, y == theAdjacentCell
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

	// class to represent the walls around the cell.
	private static class CellWalls {
		// every cell has four walls, BUT not all of them may be necessarily
		// destructable.
		Wall north, south, east, west;
		ArrayDeque<Wall> listOfDestructableWalls;
		ArrayList<Wall> walls;
		Point eastCell, southCell, westCell, northCell;

		CellWalls(Cell_Type type, int currentCellNumber) {
			listOfDestructableWalls = new ArrayDeque<Wall>();
			// logic to find the adjacentCell depending on the type of cell we
			// are dealing with.
			int eastPoint = (currentCellNumber + heightOfMaze);
			int southPoint = (currentCellNumber + 1);
			int westPoint = (currentCellNumber - heightOfMaze);
			int northPoint = (currentCellNumber - 1);
			this.eastCell = new Point(currentCellNumber, eastPoint);
			this.westCell = new Point(currentCellNumber, westPoint);
			this.northCell = new Point(currentCellNumber, northPoint);
			this.southCell = new Point(currentCellNumber, southPoint);

			// this assigns the adjacent cell to the current cell depending on
			// the type of cell we are dealing with and the type of wall.
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
				// wont happen
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

	// class to represent a cell
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

		// gives the cell a specific type depending on the position of the cell
		// in the maze.
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

	// disjoint set class that has all those disjoint set methods
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

	// A method for printing mazes.
	public static void printMaze(char[][] maze) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j]);
			System.out.println();
		}

		System.out.println();
	}

}
