/** Class that constructs an Ant.
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */

public class Ant {
	
	private Coordinate startpos;
	private int distance;
	private Maze maze;
	private Coordinate currentpos;
	private Coordinate destpos;
	
	
	
	public Ant(Coordinate start,Coordinate destpos) {
		this.startpos = start;
		this.destpos = destpos;
	}
	
	
	public void move(Direction dir) {
		
	}

}
