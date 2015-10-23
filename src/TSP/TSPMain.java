package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Ants.AntManager;
import Ants.Convergence;
import Maze.Coordinate;
import Maze.Maze;
import main.Menu;
import main.VariableManager;
import Maze.Reader;

public class TSPMain {


	private static String mazeDifficulty;
	private static boolean recalculatePaths = false;
	private static int amountOfGenerations = 100;
	private static boolean recalculateStartPaths = false;

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

		//Get Start coordinat
		File inputFileCoordinates = new File("./Maze/coordinates/" + mazeDifficulty + " coordinates.txt");
		Coordinate[] startingcoords = Coordinate.read(inputFileCoordinates);
		Coordinate startcoord = startingcoords[0];

		//Construct a TSP Network
		Network network = new Network(coordinates,startcoord);

		//Inputfile for maze
		File inputFileMaze = new File("./Maze/mazes/" + mazeDifficulty + " maze.txt");

		if(recalculateStartPaths){
			for ( int j = 0 ; j < coordinates.size() ; j++ ){
				Maze maze = Reader.input(inputFileMaze);
				VariableManager.setMaze(maze);
				//Instantiate Nodes in the maze
				maze.instantiateNodes();
				//Cleanup the Nodes in the maze
				maze.cleanUpNodes(coordinates);

				System.out.println("Calculating path from: " + "startcoord" + " to " + j);
				int Q = Convergence.estimateQ(mazeDifficulty, startcoord, coordinates.get(j));
				VariableManager.setQ(Q);
				AntManager.releaseMethodIII(startcoord, coordinates.get(j));
				ArrayList<Integer> path = AntManager.releaseSuperAnt(startcoord, coordinates.get(j));

				//Write directions of superAnt to file
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("./Maze/directions/" + "directions_" + "startcoord" + "_" + j + ".txt", "UTF-8");
				} catch (FileNotFoundException e) {} 
				catch (UnsupportedEncodingException e) {}
				writer.println(path.size() + ";");
				writer.println(startcoord.getX() + ", " + startcoord.getY() + ";");
				//writer.println(coordinates.get(j).getX() + ", " + coordinates.get(j).getY() + ";");
				writer.println(path.toString().replace(", ", ";").replace("[","").replace("]", ";"));
				writer.flush();
				writer.close();
			}
		}

		if(recalculatePaths){
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
					ArrayList<Integer> backupPath = AntManager.releaseMethodIII(coordinates.get(i), coordinates.get(j));
					ArrayList<Integer> path = AntManager.releaseSuperAnt(coordinates.get(i), coordinates.get(j));

					//If superAnt got stuck -> save path of last ant
					if(path==null)
					{
						System.out.println("Because SuperAnt is stuck, the path of the last Ant will be saved");
						path = backupPath;
					}
					
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

					
					//Write 'other way round' paths
					try{
						writer = new PrintWriter("./Maze/directions/" + "directions_" + j + "_" + i + ".txt", "UTF-8");
					} catch (FileNotFoundException e) {} 
					catch (UnsupportedEncodingException e) {}
					writer.println(path.size() + ";");
					writer.println(coordinates.get(j).getX() + ", " + coordinates.get(j).getY() + ";");
					writer.println(invertPath(path).toString().replace(", ", ";").replace("[","").replace("]", ";"));
					writer.flush();
				}
			}
		}

		//Add paths to network
		network.readPaths();

		//Add startToNodes paths to network
		network.readStartNodePaths();

		//Construct a GenerationManager
		GenerationManager genman = new GenerationManager();

		//for(int i=0;i<genman.getActiveGeneration().getChromosomes().size();i++)
		//{
		//	System.out.println(genman.getActiveGeneration().getChromosomes().get(i).getGenome().toString());
		//}

		//Generate a few generations
		int pathLength = 0;
		for(int i=0;i<amountOfGenerations;i++)
		{
			genman.generateNewGeneration();
			pathLength = genman.getActiveGeneration().getBestChromosome().getPathLength();
			System.out.println("Shortest Path: " + pathLength);

			//System.out.println("Chromosome list:");
			//for(int h=0;h<genman.getActiveGeneration().getChromosomes().size();h++)
			//{
			//	System.out.println(genman.getActiveGeneration().getChromosomes().get(h).getGenome().toString());
			//}
		}

		//Show the path
		int[] outputPath = new int[genman.getActiveGeneration().getBestChromosome().getGenome().size()];

		for(int i=0; i<genman.getActiveGeneration().getBestChromosome().getGenome().size();i++)
		{
			outputPath[i]=genman.getActiveGeneration().getBestChromosome().getGenome().get(i);
		}
		pathLength = genman.getActiveGeneration().getBestChromosome().getPathLength();

		System.out.println("Path: " + Arrays.toString(outputPath));

		//Write path to a file
		OutputTSPDirections.writeToFile(outputPath, pathLength, coordinates, network);

		System.out.println("done");
	}


	private static ArrayList<Integer> invertPath(ArrayList<Integer> path)
	{
		ArrayList<Integer> returnlist = new ArrayList<Integer>();
		returnlist = reverse(path);
		//reverse order of returnlist
		
		for(int i=0;i<returnlist.size();i++)
		{
			//flip north/south
			if(returnlist.get(i)==1)
				returnlist.set(i, 3);
			//flip west/east
			else if(returnlist.get(i)==2)
				returnlist.set(i,0);
			//flip south/north
			else if(returnlist.get(i)==3)
				returnlist.set(i,1);
			//flip east/west
			else if(returnlist.get(i)==0)
				returnlist.set(i,2);
		}
		return returnlist;
	}
	
	private static ArrayList<Integer> reverse(ArrayList<Integer> list) {
	    if(list.size() > 1) {                   
	        int value = list.remove(0);
	        reverse(list);
	        list.add(value);
	    }
	    return list;
	}

}	
