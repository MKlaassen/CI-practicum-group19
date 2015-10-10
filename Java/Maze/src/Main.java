import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/** The main class
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File inputfile = new File("mazes\\maze.txt");
		Maze maze = Reader.input(inputfile);
		System.out.println(maze.toString());
		for (Node node : maze.getNodes()){
			System.out.println(node);
		}
		maze.instantiateNodes();
		System.out.println(maze.getNodes().size());
		maze.cleanUpNodes(new Coordinate(0,0), new Coordinate(19,9));
		//		for (Node node : maze.getNodes()){
		//			System.out.println(node);
		//		}
		System.out.println(maze.getNodes().size());

		System.out.println(maze.GraphtoString());

		ArrayList<Node> neighbornodes = maze.getNode(new Coordinate(3,0)).getNeighbors();
		for (Node node : neighbornodes){
			System.out.println(node.getCoordinate().toString());
		}
	}

}

