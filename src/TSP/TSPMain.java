package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Ants.AntManager;
import Ants.Convergence;
import Maze.Coordinate;
import Maze.Maze;
import main.Menu;
import main.VariableManager;
import Maze.Reader;

public class TSPMain {


	private static String mazeDifficulty;


	private static ArrayList<Coordinate> coordinates;

	public static void main(String[] args) throws FileNotFoundException {

		VariableManager.setAlpha(1);
		VariableManager.setBeta(0.5f);
		VariableManager.setAmountOfAnts(100);
		VariableManager.setMaxIterations(100000);
		VariableManager.setLimitIterations(false);
		VariableManager.setAmountOfWinners(100);

		//Read all product locations for TSP
		try
		{
			File inputFileProducts = new File("./Maze/tsp products.txt");
			coordinates = Reader.readProducts(inputFileProducts);
		}catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
		}

		mazeDifficulty = Menu.chooseDifficulty();


		File inputFileMaze = new File("./Maze/mazes/" + mazeDifficulty + " maze.txt");
		Maze maze = Reader.input(inputFileMaze);

		VariableManager.setMaze(maze);

		//Instantiate Nodes in the maze
		maze.instantiateNodes();

		//Cleanup the Nodes in the maze
		maze.cleanUpNodes(coordinates);

		for ( int i = 0 ; i < coordinates.size() ; i++ ){
			for ( int j = i + 1 ; j < coordinates.size() ; j++){
				int Q =Convergence.estimateQ(mazeDifficulty, coordinates.get(i), coordinates.get(j));
				VariableManager.setQ(Q);
				AntManager.releaseMethodIII(coordinates.get(i), coordinates.get(j));

			}


		}


	}


}	
