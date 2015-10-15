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
	private static int amountOfAnts = 1000; //amount of ants released in the maze
	private static int amountOfWinners = 10; //amount of ants that need to succesfully reach the end
	private static String mazeDifficulty;


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

		try {
			writer = new PrintWriter("mazeNodes.txt", "UTF-8");
		} catch (FileNotFoundException e) {} 
		catch (UnsupportedEncodingException e) {}
		writer.println(maze.GraphtoString());
		writer.flush();


		//Spawning Ants
		Ant[] antarray = new Ant[amountOfAnts];
		boolean[] deadAnts = new boolean[amountOfAnts];

		//Instantiate a controlAnt for use later
		Ant controlAnt = new Ant(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);

		for(int i=0;i<antarray.length;i++)
		{
			antarray[i] = new Ant(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);
		}

		int iteration = 0;
		Ant winnerAnt;
		int winners = 0;

		//move the Ants
		Outer:
			while(true)
			{
				iteration++;
				for(int i=0;i<antarray.length;i++){
					if(deadAnts[i]==false)
					{
						antarray[i].move();													
						System.out.print("Iteration: " + iteration + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
						if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
						{
							System.out.println();
							System.out.println("Ant: " + i +  " reached the destination (winner no. "+winners+")");
							deadAnts[i]=true; //kill the Ant that reached the end destination
							winners++;
							if (winners>=amountOfWinners){
								winnerAnt = antarray[i];
								break Outer;
							}
						}
					}
				}
				//if(iteration==20)
				//{
				//	break;
				//}
			}

		//UNCOMMENT FOLLOWING TO SHOW ALL THE LEAVING PATHS'S PHEROMONE FOR ALL NODE
		//		for(int i=0;i<(maze.getNodes().size());i++)
		//		{
		//			System.out.println("Node: " + i + " Pheromone of leaving paths: " + maze.getNodes().get(i).getPheromone().toString());
		//		}

		//When pheromone is updated succesfully in the previous loop -> release another ant. Theoretically it should pick the best path in one go
		while(true)
		{
			controlAnt.move();
			if(controlAnt.getCurrentnode().equals(maze.getNode(endcoord)))
			{
				System.out.println("The ControlAnt reached the destination");
				break;
			}
		}


		//System.out.println(controlAnt.getDirections().toString().replace(", ", ";").replace("[","").replace("]", ""));

		//UNCOMMENT FOLLOWING TO SHOW ALL THE LEAVING PATHS'S PHEROMONE FOR ALL NODE
		for(int i=0;i<(maze.getNodes().size());i++)
		{
			System.out.println("Node: " + i + " Pheromone of leaving paths: " + maze.getNodes().get(i).getPheromone().toString());
		}

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

		System.out.println("done");

	}

}


