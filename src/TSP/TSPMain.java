package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
		VariableManager.setAmountOfAnts(30);
		VariableManager.setMaxIterations(100000);
		VariableManager.setLimitIterations(false);
		VariableManager.setAmountOfWinners(30);

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
		//Maze maze = Reader.input(inputFileMaze);

		//VariableManager.setMaze(maze);

		for ( int i = 0 ; i < coordinates.size() ; i++ ){
			for ( int j = i + 1 ; j < coordinates.size() ; j++){
				Maze maze = Reader.input(inputFileMaze);
				VariableManager.setMaze(maze);
				//Instantiate Nodes in the maze
				maze.instantiateNodes();
				//Cleanup the Nodes in the maze
				maze.cleanUpNodes(coordinates);
				
				System.out.println("Calculating path from: " + i + " to " + j);
				
				int Q = Convergence.estimateQ(mazeDifficulty, coordinates.get(i), coordinates.get(j));
				VariableManager.setQ(Q);
				AntManager.releaseMethodIII(coordinates.get(i), coordinates.get(j));
				ArrayList<Integer> path = AntManager.releaseSuperAnt(coordinates.get(i), coordinates.get(j));
				
				//Write directions of superAnt to file
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("./Maze/directions/" + "directions_" + i + "_" + j + ".txt", "UTF-8");
				} catch (FileNotFoundException e) {} 
				catch (UnsupportedEncodingException e) {}
				writer.println(path.size() + ";");
				writer.println(coordinates.get(i).getX() + ", " + coordinates.get(i).getY() + ";");
				//writer.println(coordinates.get(j).getX() + ", " + coordinates.get(j).getY() + ";");
				writer.println(path.toString().replace(", ", ";").replace("[","").replace("]", ";"));
				writer.flush();
				writer.close();
				break;
			
			}


		}

		System.out.println("done");
	}


}	
