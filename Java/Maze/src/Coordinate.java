/** Class that constructs a Coordinate containing a x,y coordinate.
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */
public class Coordinate {

	public Coordinate(int x, int y) {
		xcor = x;
		ycor = y;
	}
	
	public String toString() {
		return "Coordinate [xcor=" + xcor + ", ycor=" + ycor + "]";
	}

	public void setXcor(int xcor) {
		this.xcor = xcor;
	}

	public void setYcor(int ycor) {
		this.ycor = ycor;
	}

	private int xcor;
	private int ycor;
	

	
	public int getX() {
		return xcor;
	}
	
	public int getY() {
		return ycor;
	}
	
	public boolean equals(Object other){
		if (other instanceof Coordinate){
			Coordinate that = (Coordinate)other;
			return (this.xcor==that.xcor && this.ycor==that.ycor);
		}
		return false;
	}
}
