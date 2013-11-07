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
	public static final Point START_POINT = new Point(0, 0);

	public static char[][] create(int width, int height) {

		int numberOfHorizontalWalls = width * 2;
		int numberOfVerticalWalls = height * 2;

		Wall[][] theWalls = new Wall[numberOfHorizontalWalls][numberOfVerticalWalls];
		Cell[][] theCells = new Cell[width][height];

		int cellX = -1;
		for (int x = 0; x < numberOfHorizontalWalls; x++) {
			++cellX;
			int cellY = 0;
			for (int y = 0; y < numberOfVerticalWalls; y++) {
				if (x == 0 || y == 0 || x == numberOfHorizontalWalls
						|| y == numberOfVerticalWalls) {
					theWalls[x][y] = new Wall(false, new Point(x, y));
				} else if (x % 2 == 0 && y % 2 == 0) {
					theWalls[x][y] = new Wall(false, new Point(x, y));
				} else if(x % 2 == 1) {
					theWalls[x][y] = new Wall(true, new Point(x, y));
				}
				if (x % 2 == 1 && y % 2 == 1) {
					
					if (x == 0 && y == 0) {
						theCells[cellX][cellY++] = new Cell(new Point(cellX, cellY), true, false);
					} else if (x == width - 1 && y == height - 1) {
						theCells[cellX][cellY++] = new Cell(new Point(cellX, cellY), false, true);
					} else {
						theCells[cellX][cellY++] = new Cell(new Point(cellX, cellY), false, false);
					}
				}
			}
			//++cellX;
		} // closes outer for-loop
		
		for(int x = 0; x < numberOfHorizontalWalls; x++){
			for(int y = 0; y < numberOfVerticalWalls; y++){
				System.out.print(theWalls[x][y].sign);
				System.out.print(theCells[x][y].sign);
			}
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
		boolean isDestructable;
		Point position;

		Wall(boolean isDestructable, Point position) {
			this.sign = '#';
			this.isDestructable = isDestructable;
			this.position = position;
		}

		@Override
		public String toString() {
			return "Wall: " + position.toString();
		}

	}

	private static class Cell {

		char sign;
		Point position;
		boolean isStart;
		boolean isEnd;
		int parent; // the parent in the disjointSet

		Cell(Point position, boolean isStart, boolean isEnd) {
			this.position = position;
			this.isStart = isStart;
			this.isEnd = isEnd;
			if (this.isStart) {
				this.sign = 's';
			} else if (this.isEnd) {
				this.sign = 'e';
			} else {
				this.sign = ' ';
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
