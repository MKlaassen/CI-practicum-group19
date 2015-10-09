import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * @author mklaassen
 *
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
		
		
		return maze; 
	}

}
