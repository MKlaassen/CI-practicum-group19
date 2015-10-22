package Ants;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	
	public static Coordinate[] read(File inputfile) throws FileNotFoundException{
		Coordinate[] coordlist = new Coordinate[2];
		Scanner sc = new Scanner(inputfile);
		
		int x = Integer.valueOf (sc.next().replace (",",""));
		System.out.println("X: " + x);
		int y = Integer.valueOf (sc.next().replace (";",""));;
		System.out.println("Y: " + y);
		coordlist[0] = new Coordinate(x,y);
		x = Integer.valueOf (sc.next().replace (",",""));
		System.out.println("X: " + x);
		y = Integer.valueOf (sc.next().replace (";",""));
		System.out.println("Y: " + y);
		coordlist[1] = new Coordinate(x,y);
		
		sc.close();
		return coordlist;
		
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
