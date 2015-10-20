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
	private static float evaporationConstant = 0.1f;
	private static float alpha = 1;
	private static float beta = 0.5f;
	private static int Q; //estimate of the length of the route
	private static int amountOfAnts = 25; //amount of ants released in the maze
	private static String mazeDifficulty;

	private static int amountOfWinners = amountOfAnts; //amount of ants that need to reach the end (only used in concurrent release mode)
	private static boolean limitsteps = false;
	private static int maxiterations = 100000; //max amount of stepss
	private static int releaseMethod;
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
				System.out.println("1) One by one");
				System.out.println("2) Concurrent + No resetting of Ants");
				System.out.println("3) Concurrent + Reset all Ants, if one Ant finds destination");
				int choice = sc.nextInt();
				switch (choice){
				case 1:
					releaseMethod = 1;
					break modeSelectLoop;
				case 2:
					releaseMethod = 2;
					break modeSelectLoop;
				case 3:
					releaseMethod = 3;
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
			
			//TEST PURPOSE
			//Q = 100*Q;

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



			int iterations = 0;
			int winners = 0;

			if(releaseMethod==1)
			{

				//Releasing the ants into the maze. Next Ant gets released if the previous Ant reached its destination
				outerForLoop:
					for(int i=0;i<antarray.length;i++)
					{
						while(true)
						{
							iterations++;
							antarray[i].move();													
							//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
							if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
							{
								System.out.println();
								System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
								convergenceInformation[i] =  "steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")";
								winners++;
								antarray[i].splat();
								//reset the steps taken
								//steps=0;
								break;
							}
							if(limitsteps==true && iterations==maxiterations)
							{
								convergenceInformation[amountOfAnts-1] =  "Max stepss reached :(";
								break outerForLoop;
							}
						}
					}
			}
			else if(releaseMethod==2)
			{
				//Releasing all ants at once
				boolean deadAnts[] = new boolean[antarray.length];
				OuterWhile:
					while(true)
					{
						for(int i=0;i<antarray.length;i++){
							iterations++;
							if(deadAnts[i]==false)
							{
								antarray[i].move();													
								//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
								if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
								{
									System.out.println();
									System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
									convergenceInformation[i] =  "steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")";
									deadAnts[i]=true; //kill the Ant that reached the end destination
									antarray[i].splat();
									winners++;
									//steps = 0;
									if (winners>=amountOfWinners){
										break OuterWhile;
									}
								}
							}
							if(limitsteps==true && iterations==maxiterations)
							{
								convergenceInformation[amountOfAnts-1] =  "Max stepss reached :(";
								break OuterWhile;
							}
						}

					}
			}else if(releaseMethod==3)
			{
				//Releasing all ants at once
				boolean deadAnts[] = new boolean[antarray.length];
				SecondOuterWhile:
					while(true)
					{
						for(int i=0;i<antarray.length;i++){
							iterations++;

							antarray[i].move();													
							//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
							if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
							{
								System.out.println();
								System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
								convergenceInformation[i] =  "steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")";

								antarray[i].splat();
								winners++;
								//steps = 0;
								if (winners>=amountOfWinners){
									break SecondOuterWhile;
								}

								//Reinstantiating the Ants so they start over again
								for(int h=0;h<antarray.length;h++)
								{
									antarray[h] = new Ant(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);
								}
								//start over again with the new Ants
								break;

							}
							if(limitsteps==true && iterations==maxiterations)
							{
								convergenceInformation[amountOfAnts-1] =  "Max stepss reached :(";
								break SecondOuterWhile;
							}
						}
					}

				//Releasing the Super Ant. Super Ant always chooses the 'best' path:
				System.out.print("Releasing the Super Ant :D");
				while(true)
				{
					iterations++;
					((SuperAnt)controlAnt).move();													
					//System.out.print("steps: " + steps + " SuperAnt: " + " Current node: " + controlAnt.getCurrentnode().getCoordinate().toString() + "\r");
					if(controlAnt.getCurrentnode().equals(maze.getNode(endcoord)))
					{
						System.out.println();
						System.out.println("SuperAnt reached the destination");
						convergenceInformation[amountOfAnts] = "steps: " + controlAnt.getDirections().size() + " SuperAnt reached the destination";
						//Update pheromone of path and kill the ant
						controlAnt.splat();

						break;
					}
					if(((SuperAnt)controlAnt).isStuck())
					{
						System.out.println("Becouse SuperAnt is stuck, the path of the last normal Ant will be stored");
						convergenceInformation[amountOfAnts] = "steps: " + controlAnt.getDirections().size() + " SuperAnt is stuck :(";
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
				writer.println("releaseMethod: " + releaseMethod);
				writer.println("evaporationConstant: " + evaporationConstant);
				writer.println("alpha: " + alpha);
				writer.println("beta: " + beta);
				writer.println("Q: " + Q);
				writer.println("amountOfAnts: " + amountOfAnts);
				writer.println("mazeDifficulty: " + mazeDifficulty);
				writer.println("maxiterations: " +  maxiterations);
				writer.println("amountOfWinners: " + amountOfWinners);


				writer.flush();


				System.out.println("done");

			}

	}

}
