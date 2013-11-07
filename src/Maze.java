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

	public static final Point START_POINT = new Point(0, 0);

	public static char[][] create(int width, int height) {

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

	private static class CellWalls {
		Wall north, south, east, west;

		CellWalls(Cell_Type type) {
			switch (type) {
			case NORTH_WEST:

				break;
			case WEST:

				break;
			case SOUTH_WEST:

				break;
			case SOUTH:

				break;
			case SOUTH_EAST:

				break;
			case EAST:

				break;
			case NORTH_EAST:

				break;
			case NORTH:

				break;
			case CENTER:

				break;
			default:

				break;
			}
		}
	}

	private static class Cell {

		char sign;
		Point position;
		boolean isStart = false;
		boolean isEnd = false;
		int parent; // the parent in the disjointSet

		Cell(Point position) {
			this.position = position;
			if (this.position == START_POINT) {
				this.isStart = true;
				this.sign = 's';

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
