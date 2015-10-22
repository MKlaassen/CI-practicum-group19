package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import Ants.AntManager;
import Ants.Convergence;
import Maze.Coordinate;
import Maze.Maze;
import Maze.Reader;


/** The main class
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Dani�l Brouwer
 * @version 9 October 2015
 */
public class Main {


	private static String mazeDifficulty;
	private static int releaseMethod;


	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException 
	{
			
		VariableManager.setAlpha(1);
		VariableManager.setBeta(0.5f);
		VariableManager.setAmountOfAnts(100);
		VariableManager.setMaxIterations(100000);
		VariableManager.setLimitIterations(false);
		VariableManager.setAmountOfWinners(100);
		
		
		mazeDifficulty = Menu.chooseDifficulty();
			releaseMethod = Menu.chooseReleaseMethod();
			
			File inputFileMaze = new File("./Maze/mazes/" + mazeDifficulty + " maze.txt");
			Maze maze = Reader.input(inputFileMaze);
			
			VariableManager.setMaze(maze);

			//Get coordinates
			File inputFileCoordinates = new File("./Maze/coordinates/" + mazeDifficulty + " coordinates.txt");
			Coordinate[] coords = Coordinate.read(inputFileCoordinates);
	
			ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
			
			Coordinate startcoord = coords[0];
			Coordinate endcoord = coords[1];
			
			coordinates.add(startcoord);
			coordinates.add(endcoord);
			
			//Instantiate Nodes in the maze
			maze.instantiateNodes();
			
			//Cleanup the Nodes in the maze
			maze.cleanUpNodes(coordinates);
			
			//Write maze to file
			Convergence.writeMazeGraph(maze);
			
			//Estimate Q
			int Q = Convergence.estimateQ(mazeDifficulty, startcoord, endcoord);
			VariableManager.setQ(Q);
			
			if (releaseMethod == 1) {
				AntManager.releaseMethodI(startcoord, endcoord);
			}
			
			if (releaseMethod == 2) {
				AntManager.releaseMethodII(startcoord, endcoord);
			}
			
			if (releaseMethod == 3) {
				AntManager.releaseMethodIII(startcoord, endcoord);
			}
			
			ArrayList<Integer> intlist = AntManager.releaseSuperAnt(startcoord, endcoord);
			
			System.out.println(intlist.toString());

			//Write convergenceinformation for the Ants
			String fileName = "convergenceInformation.txt";
			Convergence.writeConvergenceInformation(releaseMethod, fileName);
	}

}
