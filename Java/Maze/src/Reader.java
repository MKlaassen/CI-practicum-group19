import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/** Class that is used to Read the inputfile
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */
public class Reader {
	
	public static Maze input(File inputfile) throws FileNotFoundException {
		
		Scanner sc = new Scanner(inputfile);
		int columns = sc.nextInt();
		int rows = sc.nextInt();
		int[][] input = new int[rows][columns];
		
		for(int i = 0 ; i < rows ; i++) {
			for( int j = 0 ; j < columns ; j++) {
				//System.out.println("At row: " + i + " Column: " + j);
				input[i][j] = sc.nextInt();
			}
		}
		
		Maze maze = new Maze(input, columns, rows);
		
		sc.close();
		return maze; 
	}

}
