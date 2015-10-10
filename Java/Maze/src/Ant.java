import java.util.ArrayList;
import java.util.Random;

/** Class that constructs an Ant.
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */

public class Ant {

	private Coordinate startpos;
	private int distance;
	private Maze maze;
	private Coordinate currentpos;
	private Coordinate destpos;
	private Node currentnode;
	private Node endnode;
	private float alpha;
	private float beta;


	public Ant(Maze maze, Coordinate start, Coordinate end, float alpha, float beta) {
		this.maze = maze;
		this.currentpos = start;
		this.destpos = end;
		this.currentnode = maze.getNode(start);
		this.endnode = maze.getNode(end);
		this.alpha = alpha;
		this.beta = beta;
		this.distance = 0;
	}

	public void move(Direction dir) 
	{
		float[] routeProbabilities = calculateRouteProbabilities(alpha,beta);
		float[] sumProbabilities = new float[routeProbabilities.length];

		//generate random number between 0.1 and 1
		Random rand = new Random();
		int  randNum = (rand.nextInt(10) + 1)/10;
		float sum;
		
		for(int i=0;i<routeProbabilities.length;i++)
		{
			sum = 0;
			for(int j=0;j<i;j++)
			{
				sum = sum + routeProbabilities[i];
			}
			sumProbabilities[i] = sum;
		}
		
		for(int i=0;i<routeProbabilities.length;i++)
		{
			if(randNum>sumProbabilities[i] && randNum<sumProbabilities[i+1])
			{
				//If True -> ANT needs to travel to Neighbor Node I
				//HIER MOET VERDER
			}
			
		}
		
		
	}
	
	public float[] calculateRouteProbabilities(float alpha, float beta)
	{
		//list of reachableNodes from current position
		ArrayList<Node> reachableNodes = this.currentnode.getNeighbors();
		ArrayList<Integer> distanceNodes = this.currentnode.getDists();
		ArrayList<Float> pheromoneNodes = this.currentnode.getPheromone();
		float[] LengthPheromoneProduct = new float[reachableNodes.size()];
		float[] routeProbabilities = new float[reachableNodes.size()];
		float SumOfLengthPheromoneProduct = 0;

		for(int i=0;i<reachableNodes.size();i++)
		{
			LengthPheromoneProduct[i] = (float) (Math.pow(pheromoneNodes.get(i),alpha)*Math.pow((1/distanceNodes.get(i)),beta));
			SumOfLengthPheromoneProduct = SumOfLengthPheromoneProduct + LengthPheromoneProduct[i];
		}
		for(int i=0;i<reachableNodes.size();i++)
		{
			routeProbabilities[i] = LengthPheromoneProduct[i]/SumOfLengthPheromoneProduct;
		}	
		return routeProbabilities;
	}
}
