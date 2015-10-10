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
	private Node currentnode;
	private Node endnode;
	
	
	public Ant(Maze maze, Coordinate start, Coordinate end) {
		this.maze = maze;
		this.currentpos = start;
		this.destpos = end;
		this.currentnode = maze.getNode(start);
		this.endnode = maze.getNode(end);
	}
	
	public void move(Direction dir) {
		
	}
	

}
