import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/** The main class
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */
public class Main {
	private static Coordinate startcoord;
	private static Coordinate endcoord;
	private static float evaporationConstant = 0.5f;
	private static float alpha = 1;
	private static float beta = 0.5f;
	private static int Q; //estimate of the length of the route
	private static int amountOfAnts = 25; //amount of ants released in the maze
	private static String mazeDifficulty;

	private static int amountOfWinners = 25; //amount of ants that need to reach the end (only used in concurrent release mode)
	private static int maxIterations = 100000; //max amount of iterations
	private static boolean concurrentRelease;
	private static String[] convergenceInformation = new String[amountOfAnts + 1]; //Store information for all ants + SuperAnts for parameter optimization 


	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{

		Scanner sc = new Scanner(System.in);
		mainMenuLoop:
			while(true){

				System.out.println("Select maze difficulty:");
				System.out.println("1) Easy");
				System.out.println("2) Medium");
				System.out.println("3) Hard");
				System.out.println("4) INSANE");
				int choice = sc.nextInt();
				switch (choice){
				case 1:
					mazeDifficulty = "easy";
					break mainMenuLoop;
				case 2:
					mazeDifficulty = "medium";
					break mainMenuLoop;
				case 3:
					mazeDifficulty = "hard";
					break mainMenuLoop;
				case 4:
					mazeDifficulty = "insane";
					break mainMenuLoop;
				default:
					System.out.println("No valid number entered");
					break;
				}
			}

			//close input scanner
			sc.close();

			//Read maze and create maze
			File inputFileMaze = new File("mazes\\" + mazeDifficulty + " maze.txt");
			Maze maze = Reader.input(inputFileMaze);

			//Read all product locations for TSP
			File inputFileProducts = new File("products\\tsp products.txt");
			ArrayList<Coordinate> coordinates = Reader.readProducts(inputFileProducts);
			
			//System.out.println(coordinates.toString());

			//Instantiate all Nodes in the maze
			maze.instantiateNodes();

			//Turn maze into a graph representation
			maze.cleanUpNodes(coordinates);

			//New printwriter
			PrintWriter writer = null;

			//Write the graph representation of the maze to a txt file
			try {
				writer = new PrintWriter("mazeNodes.txt", "UTF-8");
			} catch (FileNotFoundException e) {} 
			catch (UnsupportedEncodingException e) {}
			writer.println(maze.GraphtoString());
			writer.flush();

			//done executing
			System.out.println("done");

	}

}


