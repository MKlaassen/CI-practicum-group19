import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;


/** The main class
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */
public class Main {

	private static Coordinate startcoord = new Coordinate(0,0);
	private static Coordinate endcoord = new Coordinate(19,9);
	//private static Coordinate endcoord = new Coordinate(20,9);
	private static float evaporationConstant = 0.1f;
	private static float alpha = 1;
	private static float beta = 0.5f;

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File inputfile = new File("mazes\\maze.txt");
		Maze maze = Reader.input(inputfile);
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
		Ant[] antarray = new Ant[100];
		for(int i=0;i<antarray.length;i++)
		{
			antarray[i] = new Ant(maze, startcoord, endcoord, alpha, beta, evaporationConstant);
		}
		//move the ant
		int steps = 0;
		Ant winnerAnt;
		int winners = 0;
		Outer:
			while(true)
			{
				steps++;
				for(int i=0;i<antarray.length;i++){
					antarray[i].move();
					
					System.out.print("Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString()+"\r");
					if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
					{
						System.out.println("Ant: " + i +  "reached the destination (winner no. "+winners+")");
						winners++;
						if (winners>=10){
							winnerAnt = antarray[i];
							
							break Outer;
						}
						
					}
					//			if (steps>10){
					//				break;
					//			}
				}
				if (steps%100==0){
					System.out.println("Steps: " + steps);
				}
			}

		System.out.println(winnerAnt.getDirections().toString().replace(", ", ";").replace("[","").replace("]", ""));

		try {
			writer = new PrintWriter("mazeNodesVisited.txt", "UTF-8");
		} catch (FileNotFoundException e) {} 
		catch (UnsupportedEncodingException e) {}
		writer.println(maze.pathGraphtoString(winnerAnt.getPath()));
		writer.flush();



		try {
			writer = new PrintWriter("directions.txt", "UTF-8");
		} catch (FileNotFoundException e) {} 
		catch (UnsupportedEncodingException e) {}
		writer.println(winnerAnt.getDirections().size() + ";");
		writer.println(startcoord.getX() + ", " + startcoord.getY() + ";");
		writer.println(winnerAnt.getDirections().toString().replace(", ", ";").replace("[","").replace("]", ";"));
		writer.flush();

		System.out.println("done");

	}

}


