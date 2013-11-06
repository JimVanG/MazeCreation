import java.awt.Point;

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

		return null;
	}

	public static double difficultyRating() {
		return 3;
	}

	public static double hoursSpent() {
		return 5;
	}
	


	public static void main(String[] args) {

	}

	
	private static class Wall{
		
		char sign;
		boolean isDestructable;
		Point position;
		
		Wall(boolean isDestructable, Point position){
			this.sign = '#';
			this.isDestructable = isDestructable;
			this.position = position;
		}
		
	}

	private static class Cell{
		
		char sign;
		Point position;
		boolean isStart;
		boolean isEnd;
		int parent; //the parent in the disjointSet 
		
		Cell(Point position, boolean isStart, boolean isEnd){
			this.position = position;
			if(this.position.equals(START_POINT)){
				this.isEnd = false;
				this.isStart = true;
				this.sign = 's';
			}else{
				this.isEnd = false;
				this.isStart = false;
				this.sign = ' ';
			}
		}
	}
	
	private static class DisjointSet{
		
		
	}
	
}


