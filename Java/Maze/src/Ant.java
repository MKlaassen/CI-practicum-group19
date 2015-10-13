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
	private float evaporationConstant;


	public Ant(Maze maze, Coordinate start, Coordinate end, float alpha, float beta, float ec) {
		this.maze = maze;
		this.currentpos = start;
		this.destpos = end;
		this.currentnode = maze.getNode(start);
		this.endnode = maze.getNode(end);
		this.alpha = alpha;
		this.beta = beta;
		this.distance = 0;
		this.evaporationConstant = ec;
	}

	public void move(Direction dir) 
	{
		float[] routeProbabilities = calculateRouteProbabilities(alpha,beta);
		float[] sumProbabilities = new float[routeProbabilities.length];

		//generate random number between 0 and 1
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
		
		//sumProbabilities[i] = [0.3,0.6,1]
		for(int i=0;i<routeProbabilities.length;i++)
		{
			if(i==0){
				if (randNum>0 && randNum<sumProbabilities[i]){
					travelToNode(this.currentnode.getNeighbors().get(i));
				}
			}
			else if(randNum>sumProbabilities[i-1] && randNum<sumProbabilities[i])
			{
				//If True -> ANT needs to travel to Neighbor Node I-1
				//HIER MOET VERDER
				travelToNode(this.currentnode.getNeighbors().get(i));
			}
			
		}
		
		
	}
	
	public void travelToNode(Node target){
		int neighInd = -1;
		for (int i = 0; i<this.currentnode.getNeighbors().size(); i++){
			if (this.currentnode.getNeighbors().get(i)==target){
				neighInd = i;
				break;
			}
		}
		this.currentnode.updatePheromone(neighInd, this.evaporationConstant, 
				1000/this.currentnode.getDists().get(neighInd));
		
		this.currentnode = target;
		
		neighInd = -1;
		for (int i = 0; i<this.currentnode.getNeighbors().size(); i++){
			if (this.currentnode.getNeighbors().get(i)==target){
				neighInd = i;
				break;
			}
		}
		this.currentnode.updatePheromone(neighInd, this.evaporationConstant, 
				1000/this.currentnode.getDists().get(neighInd));
		
	}
	
	public void splat(){
		
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
