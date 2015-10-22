package Ants;
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


	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{
			mazeDifficulty = Menu.chooseDifficulty();
			releaseMethod = Menu.chooseReleaseMethod();
			
			File inputFileMaze = new File("mazes\\" + mazeDifficulty + " maze.txt");
			Maze maze = Reader.input(inputFileMaze);

			File inputFileCoordinates = new File("coordinates\\" + mazeDifficulty + " coordinates.txt");
			Coordinate[] coordinates = Coordinate.read(inputFileCoordinates);
	
			Coordinate startcoord = coordinates[0];
			Coordinate endcoord = coordinates[1];
			
			//Instantiate Nodes in the maze
			maze.instantiateNodes();
			
			//Cleanup the Nodes in the maze
			maze.cleanUpNodes(startcoord, endcoord);
			
			//Estimate Q
			Q = PathCalculator.estimateQ(mazeDifficulty, startcoord, endcoord);
			
			//Write maze to file
			PathCalculator.writeMazeGraph(maze);
			
			//Release all ants
			String[] convInfoPart1 = PathCalculator.releaseAnts(releaseMethod, amountOfAnts, maze, startcoord, endcoord, alpha, beta, evaporationConstant, Q, limitIterations, maxIterations, amountOfWinners);
			
			//Release superAnt
			String[] convInfoPart2 = PathCalculator.releaseSuperAnt(releaseMethod, amountOfAnts, maze, startcoord, endcoord, alpha, beta, evaporationConstant, Q, limitIterations, maxIterations);

			//Write convergenceinformation for the Ants
			String fileName = "convergenceInformationAnts.txt";
			PathCalculator.writeConvergenceInformation(releaseMethod, amountOfAnts, maze, startcoord, endcoord, alpha, beta, evaporationConstant, Q, limitIterations, maxIterations, amountOfWinners, convergenceInformation, mazeDifficulty, fileName);
			
			//Write convergenceinformation for the SuperAnt
			fileName = "convergenceInformationSuperAnt.txt";
			PathCalculator.writeConvergenceInformation(releaseMethod, amountOfAnts, maze, startcoord, endcoord, alpha, beta, evaporationConstant, Q, limitIterations, maxIterations, amountOfWinners, convergenceInformation, mazeDifficulty, fileName);
			
			
	}

}
