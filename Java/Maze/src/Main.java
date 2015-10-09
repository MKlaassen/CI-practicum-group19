import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Rick Molenaar
 * @author mklaassen
 *
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
		
	}

}
