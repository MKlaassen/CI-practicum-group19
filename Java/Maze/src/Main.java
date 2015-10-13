import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;


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
		File inputfile = new File("mazes\\INSANE.txt");
		Maze maze = Reader.input(inputfile);
		System.out.println(maze.toString());
		for (Node node : maze.getNodes()){
			System.out.println(node);
		}
		maze.instantiateNodes();
//		System.out.println(maze.getNodes().size());
		maze.cleanUpNodes(new Coordinate(0,0), new Coordinate(19,9));
//		System.out.println(maze.getNodes().size());

		System.out.println(maze.GraphtoString());
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter("mazeNodes.txt", "UTF-8");
		} catch (FileNotFoundException e) {} 
		catch (UnsupportedEncodingException e) {}
		writer.println(maze.GraphtoString());
		writer.flush();
	}

}

