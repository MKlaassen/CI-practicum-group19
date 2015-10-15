import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

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

	public void move() 
	{
		float[] routeProbabilities = calculateRouteProbabilities(alpha,beta);
		float[] sumProbabilities = new float[routeProbabilities.length];

		//generate random number between 0 and 1
		Random rand = new Random();
		float  randNum = (rand.nextInt(10) + 1)/10.0f;
		System.out.println("Randomnum: " + randNum);
		float sum;
		
		for(int i=0;i<routeProbabilities.length;i++)
		{
			sum = 0;
			for(int j=0;j<=i;j++)
			{
				sum = sum + routeProbabilities[j];
			}
			sumProbabilities[i] = sum;
		}
		System.out.println(Arrays.toString(sumProbabilities));
		//sumProbabilities[i] = [0.3,0.6,1]
		for(int i=0;i<routeProbabilities.length;i++)
		{
			if(i==0){
				if (randNum>0 && randNum<sumProbabilities[i]){
					travelToNode(this.currentnode.getNeighbors().get(i));
					break;
				}
			}
			else if(randNum>sumProbabilities[i-1] && randNum<sumProbabilities[i])
			{
				travelToNode(this.currentnode.getNeighbors().get(i));
				break;
			}
		}
		if (randNum>=sumProbabilities[sumProbabilities.length-1]){
			travelToNode(this.currentnode.getNeighbors().get(this.currentnode.getNeighbors().size()-1));
		}
		
		
	}
	
	public void travelToNode(Node target){
		System.out.println("Travelling to node: "+target.getCoordinate());
		int neighInd = -1;
		for (int i = 0; i<this.currentnode.getNeighbors().size(); i++){
			if (this.currentnode.getNeighbors().get(i)==target){
				neighInd = i;
				break;
			}
		}
		this.currentnode.updatePheromone(neighInd, this.evaporationConstant, 
				1000/this.currentnode.getDists().get(neighInd));
		
		Node lastNode = this.currentnode;
		this.currentnode = target;
		
		neighInd = -1;
		for (int i = 0; i<this.currentnode.getNeighbors().size(); i++){
//			System.out.println("target:"+target);
//			System.out.println("neigh:"+this.currentnode.getNeighbors().get(i));
			if (this.currentnode.getNeighbors().get(i)==lastNode){
				neighInd = i;
				break;
			}
		}
		this.currentnode.updatePheromone(neighInd, this.evaporationConstant, 
				1/this.currentnode.getDists().get(neighInd));
		
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
			LengthPheromoneProduct[i] = (float) (Math.pow(pheromoneNodes.get(i),alpha)*Math.pow((1.0f/distanceNodes.get(i)),beta));
			SumOfLengthPheromoneProduct = SumOfLengthPheromoneProduct + LengthPheromoneProduct[i];
		}
		for(int i=0;i<reachableNodes.size();i++)
		{
			routeProbabilities[i] = LengthPheromoneProduct[i]/SumOfLengthPheromoneProduct;
		}	
		return routeProbabilities;
	}

	public Node getCurrentnode() {
		return currentnode;
	}
	
}
