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

		modeSelectLoop:
			while(true)
			{
				System.out.println("Select mode:");
				System.out.println("1) Concurrent");
				System.out.println("2) One by one");
				int choice = sc.nextInt();
				switch (choice){
				case 1:
					concurrentRelease = true;
					break modeSelectLoop;
				case 2:
					concurrentRelease = false;
					break modeSelectLoop;
				default:
					System.out.println("No valid number entered");
					break;
				}
			}


			//close input scanner
			sc.close();

			File inputFileMaze = new File("mazes\\" + mazeDifficulty + " maze.txt");
			Maze maze = Reader.input(inputFileMaze);

			File inputFileCoordinates = new File("coordinates\\" + mazeDifficulty + " coordinates.txt");
			Coordinate[] coordinates = Coordinate.read(inputFileCoordinates);
			startcoord = coordinates[0];
			endcoord = coordinates[1];

			//estimation of the route length.
			Q = Math.abs(startcoord.getX()-endcoord.getX()) + Math.abs(startcoord.getY()-endcoord.getY());


			System.out.println(maze.toString());
			for (Node node : maze.getNodes()){
				System.out.println(node);
			}
			maze.instantiateNodes();
			//		System.out.println(maze.getNodes().size());
			maze.cleanUpNodes(startcoord, endcoord);
			//		System.out.println(maze.getNodes().size());

			System.out.println(maze.GraphtoString());
			PrintWriter writer = null;

			//Write the graph representation of the maze to a txt file
			try {
				writer = new PrintWriter("mazeNodes.txt", "UTF-8");
			} catch (FileNotFoundException e) {} 
			catch (UnsupportedEncodingException e) {}
			writer.println(maze.GraphtoString());
			writer.flush();


			//Allocating space for all the ants
			Ant[] antarray = new Ant[amountOfAnts];

			//Instantiating the Ants
			for(int i=0;i<antarray.length;i++)
			{
				antarray[i] = new Ant(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);
			}

			//Instantiate a controlAnt for use later
			Ant controlAnt = new SuperAnt(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);



			int iteration = 0;
			int winners = 0;

			if(concurrentRelease==false)
			{

				//Releasing the ants into the maze. Next Ant gets released if the previous Ant reached its destination
				outerForLoop:
					for(int i=0;i<antarray.length;i++)
					{
						while(true)
						{
							iteration++;
							antarray[i].move();													
							System.out.print("Iteration: " + iteration + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
							if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
							{
								System.out.println();
								System.out.println("Iteration: " + iteration + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
								convergenceInformation[i] =  "Iteration: " + iteration + " Ant: " + i +  " reached the destination (winner no. "+winners+")";
								winners++;


								antarray[i].splat();

								break;
							}
							if(iteration==maxIterations)
							{
								convergenceInformation[amountOfAnts-1] =  "Max Iterations reached :(";
								break outerForLoop;
							}
						}
					}
			}
			else
			{
				//Releasing the ants one by one into the maze
				boolean deadAnts[] = new boolean[antarray.length];
				OuterWhile:
					while(true)
					{
						for(int i=0;i<antarray.length;i++){
							iteration++;
							if(deadAnts[i]==false)
							{
								antarray[i].move();													
								System.out.print("Iteration: " + iteration + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
								if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
								{
									System.out.println();
									System.out.println("Iteration: " + iteration + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
									convergenceInformation[i] =  "Iteration: " + iteration + " Ant: " + i +  " reached the destination (winner no. "+winners+")";
									deadAnts[i]=true; //kill the Ant that reached the end destination
									antarray[i].splat();
									winners++;
									if (winners>=amountOfWinners){
										break OuterWhile;
									}
								}
							}
							if(iteration==maxIterations)
							{
								convergenceInformation[amountOfAnts-1] =  "Max Iterations reached :(";
								break OuterWhile;
							}
						}

					}
			}

			//Releasing the Super Ant. Super Ant always chooses the 'best' path:
			System.out.print("Releasing the Super Ant :D");
			while(true)
			{
				iteration++;
				((SuperAnt)controlAnt).move();													
				System.out.print("Iteration: " + iteration + " SuperAnt: " + " Current node: " + controlAnt.getCurrentnode().getCoordinate().toString() + "\r");
				if(controlAnt.getCurrentnode().equals(maze.getNode(endcoord)))
				{
					System.out.println();
					System.out.println("SuperAnt reached the destination");
					convergenceInformation[amountOfAnts] = "Iteration: " + iteration + " SuperAnt reached the destination";
					//Update pheromone of path and kill the ant
					controlAnt.splat();

					break;
				}
				if(((SuperAnt)controlAnt).isStuck())
				{
					System.out.println("Becouse SuperAnt is stuck, the path of the last normal Ant will be stored");
					convergenceInformation[amountOfAnts] = "Iteration: " + iteration + " SuperAnt is stuck :(";
					controlAnt = antarray[antarray.length-1];
					break;
				}
			}

			//UNCOMMENT FOLLOWING TO SHOW ALL THE LEAVING PATHS'S PHEROMONE FOR ALL NODE
			//for(int i=0;i<(maze.getNodes().size());i++)
			//{
			//	System.out.println("Node: " + i + " Pheromone of leaving paths: " + maze.getNodes().get(i).getPheromone().toString());
			//}

			try {
				writer = new PrintWriter("mazeNodesVisited.txt", "UTF-8");
			} catch (FileNotFoundException e) {} 
			catch (UnsupportedEncodingException e) {}
			writer.println(maze.pathGraphtoString(controlAnt.getPath()));
			writer.flush();


			try {
				writer = new PrintWriter("directions.txt", "UTF-8");
			} catch (FileNotFoundException e) {} 
			catch (UnsupportedEncodingException e) {}
			writer.println(controlAnt.getDirections().size() + ";");
			writer.println(startcoord.getX() + ", " + startcoord.getY() + ";");
			writer.println(controlAnt.getDirections().toString().replace(", ", ";").replace("[","").replace("]", ";"));
			writer.flush();

			try {
				writer = new PrintWriter("convergenceInformation.txt", "UTF-8");
			} catch (FileNotFoundException e) {} 
			catch (UnsupportedEncodingException e) {}
			writer.println("////Convergence Information:////");
			writer.println();
			for(int i=0;i<convergenceInformation.length;i++)
			{
				writer.println(convergenceInformation[i]);
			}
			writer.println();
			writer.println("////Used Parameters:////");
			writer.println();
			writer.println("concurrentRelease: " + concurrentRelease);
			writer.println("evaporationConstant: " + evaporationConstant);
			writer.println("alpha: " + alpha);
			writer.println("beta: " + beta);
			writer.println("Q: " + Q);
			writer.println("amountOfAnts: " + amountOfAnts);
			writer.println("mazeDifficulty: " + mazeDifficulty);
			writer.println("maxIterations: " +  maxIterations);
			writer.println("amountOfWinners: " + amountOfWinners);
	
			writer.flush();


			System.out.println("done");

	}

}


