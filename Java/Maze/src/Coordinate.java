/**
 * @author mklaassen
 *
 */
public class Coordinate {

	@Override
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
	
	public boolean equals(Object other){
		if (other instanceof Coordinate){
			Coordinate that = (Coordinate)other;
			return (this.xcor==that.xcor && this.ycor==that.ycor);
		}
		return false;
	}
}
