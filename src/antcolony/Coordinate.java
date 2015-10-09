/**
 * 
 */
package antcolony;

/**
 * @author mklaassen
 *
 */
public class Coordinate {

	private int xcor;
	private int ycor;
	
	public Coordinate(int x, int y) {
		xcor = x;
		ycor = y;
	}
	
	public int getX() {
		return xcor;
	}
	
	public int getY() {
		return ycor;
	}
}
