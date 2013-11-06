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
	public static final Point END_POINT = null;
	
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

	
	class Wall{
		
		char sign;
		boolean isDestructable;
		Point position;
		
		Wall(boolean isDestructable, Point position){
			this.sign = '#';
			this.isDestructable = isDestructable;
			this.position = position;
		}
		
	}

	class Cell{
		
		char sign;
		Point position;
		boolean isStart;
		boolean isEnd;
		
		Cell(Point position, boolean isStart, boolean isEnd){
			this.position = position;
			if(this.position.equals(START_POINT)){
				this.isEnd = false;
				this.isStart = true;
				this.sign = 's';
			}else if(this.position.equals(END_POINT)){
				this.isEnd = true;
				this.isStart = false;
				this.sign = 'e';
			}else{
				this.isEnd = false;
				this.isStart = false;
				this.sign = ' ';
			}
		}
	}
	
}


