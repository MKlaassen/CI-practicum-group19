import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author mklaassen
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File inputfile = new File("mazes\\maze.txt");
		Maze maze = Reader.input(inputfile);
		System.out.println(maze.toString());
				

	}

}
