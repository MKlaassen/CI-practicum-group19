/**
 * @author mklaassen
 *
 */
public class Coordinate {

	public void setXcor(int xcor) {
		this.xcor = xcor;
	}

	public void setYcor(int ycor) {
		this.ycor = ycor;
	}

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
