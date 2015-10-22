package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Ants.Coordinate;
import Ants.Maze;
import Ants.Menu;
import Ants.PathCalculator;
import Ants.Reader;

public class Main {
	
	private static Coordinate startcoord;
	private static Coordinate endcoord;
	private static float evaporationConstant;
	private static float alpha = 1;
	private static float beta = 0.5f;
	private static int Q; //estimate of the length of the route
	private static int amountOfAnts = 100; //amount of ants released in the maze
	private static String mazeDifficulty;

	private static int amountOfWinners = amountOfAnts; //amount of ants that need to reach the end (only used in concurrent release mode)
	private static boolean limitIterations = false;
	private static int maxIterations = 100000; //max amount of iterations
	private static int releaseMethod;
	private static String[] convergenceInformation = new String[amountOfAnts + 1]; //Store information for all ants + SuperAnts for parameter optimization 
	
	private static ArrayList<Coordinate> coordinates;

	public static void main(String[] args) throws FileNotFoundException {
		//Read all product locations for TSP
		try
		{
			File inputFileProducts = new File("products\\tsp products.txt");
			coordinates = Reader.readProducts(inputFileProducts);
		}catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
		}
		
		mazeDifficulty = Menu.chooseDifficulty();
		releaseMethod = Menu.chooseReleaseMethod();
		
		File inputFileMaze = new File("mazes\\" + mazeDifficulty + " maze.txt");
		Maze maze = Reader.input(inputFileMaze);

		//Instantiate Nodes in the maze
		maze.instantiateNodes();
		
		//Cleanup the Nodes in the maze
		maze.cleanUpNodes(coordinates);
		
		for ( int i = 0 ; i < coordinates.size() ; i++ ){
			for ( int j = i + 1 ; j < coordinates.size() ; j++){
				int Q =PathCalculator.estimateQ(mazeDifficulty, coordinates.get(i), coordinates.get(j));
				PathCalculator.releaseAnts(releaseMethod, amountOfAnts, maze, coordinates.get(i), coordinates.get(j), alpha, beta, evaporationConstant, Q, limitIterations, maxIterations, amountOfWinners);
				
			}
		}
		
		
		
		
		
		
	}
	
	
	
	
}
