package Ants;
import java.util.ArrayList;
import java.util.Random;

import Maze.Coordinate;
import Maze.Node;
import main.VariableManager;

/** Class that constructs an Ant.
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Dani�l Brouwer
 * @version 9 October 2015
 */

public class Ant {

	private int distance;
	protected Node currentnode;
	protected ArrayList<Node> path;
	protected ArrayList<Integer> directions;

	public Ant(Coordinate start, Coordinate end) {
		this.path = new ArrayList<Node>();
		this.directions = new ArrayList<Integer>();
		this.currentnode = VariableManager.getMaze().getNode(start);
		this.distance = 0;
		this.path.add(currentnode);
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public void move() 
	{
		float[] routeProbabilities = calculateRouteProbabilities(VariableManager.getAlpha(),VariableManager.getBeta());
		float[] sumProbabilities = new float[routeProbabilities.length];

		//generate random number between 0 and 1
		Random rand = new Random();
		//		float  randNum = (rand.nextInt(10) + 1)/10.0f;
		float randNum = rand.nextFloat();
		//		System.out.println("Randomnum: " + randNum);
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

		for(int i=0;i<routeProbabilities.length;i++)
		{
			if(i==0){
				if (randNum>0 && randNum<sumProbabilities[i]){
					directions.add(getDirection(this.currentnode, this.currentnode.getNeighbors().get(i)));	//Add correct direction

					travelToNode(this.currentnode.getNeighbors().get(i));									//travel to the node

					break;
				}
			}
			else if(randNum>sumProbabilities[i-1] && randNum<sumProbabilities[i])
			{

				travelToNode(this.currentnode.getNeighbors().get(i));										//travel to the node
				break;
			}
		}
		if (randNum>=sumProbabilities[sumProbabilities.length-1]){

			travelToNode(this.currentnode.getNeighbors().get(this.currentnode.getNeighbors().size()-1));
		}


	}

	public ArrayList<Integer> getDirections() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (int i = 0; i<this.path.size()-1; i++){
			Node currentNode = this.path.get(i);
			for (int j = 0; j < currentNode.getNeighbors().size(); j++){
				if (currentNode.getNeighbors().get(j)==this.path.get(i+1)){
					for (int k = 0; k < currentNode.getDists().get(j); k++){
						res.add(getDirection(currentNode, this.path.get(i+1)));
					}
				}
			}
		}
		return res;
	}

	public int getDirection(Node current, Node target)
	{
		Coordinate currentCoord = current.getCoordinate();
		Coordinate targetCoord = target.getCoordinate();

		if(currentCoord.getX()<targetCoord.getX())
			return 0; //East
		else if(currentCoord.getX()>targetCoord.getX())
			return 2; //West
		else if(currentCoord.getY()<targetCoord.getY())
			return 3; //South
		else if(currentCoord.getY()>targetCoord.getY())
			return 1; //North


		return 4; //If this executes the code is broken
	}


	public void travelToNode(Node target){
		int neighInd = -1;
		for (int i = 0; i<this.currentnode.getNeighbors().size(); i++){
			if (this.currentnode.getNeighbors().get(i)==target){
				neighInd = i;
				break;
			}
		}

		Node lastNode = this.currentnode;
		this.currentnode = target;
		this.path.add(currentnode);
		this.distance = distance + lastNode.getDists().get(neighInd);

		//UNCOMMENT THE FOLLOWING CODE IF PATHPHEROMONES NEED TO BE UPDATED FROM BOTH WAYS

		//		neighInd = -1;
		//		for (int i = 0; i<this.currentnode.getNeighbors().size(); i++){
		//						//System.out.println("target:"+target);
		//						//System.out.println("neigh:"+this.currentnode.getNeighbors().get(i));
		//			if (this.currentnode.getNeighbors().get(i)==lastNode){
		//				neighInd = i;
		//				break;
		//			}
		//		}
		//		this.currentnode.updatePheromone(neighInd, this.evaporationConstant, 
		//				1/this.currentnode.getDists().get(neighInd));

	}

	public void splat()
	{
		clearPath();
		for (int i=0;i<this.path.size()-1;i++)
		{
			for (int j = 0; j<this.path.get(i).getNeighbors().size(); j++){

				if (this.path.get(i).getNeighbors().get(j)==this.path.get(i+1)){
					this.path.get(i).updatePheromone(j, 0, 
							(this.path.get(i).getDists().get(j)*(float)VariableManager.getQ())/distance);
				}

			}

		}

		//evaporate all paths with the evaporationconstant
		VariableManager.getMaze().evaporateAllPaths(VariableManager.getEvaporationconstant());
	}


	private void clearPath() {
		ArrayList<Node> seen = new ArrayList<Node>();
		for (int i = 0; i < this.path.size(); i++){
			if (!seen.contains(this.path.get(i))){
				seen.add(this.path.get(i));
			}
			else {
				int firstNodeIndex = seen.indexOf(this.path.get(i));
				for (int j = firstNodeIndex; j < i; j++){
					this.path.remove(firstNodeIndex+1);
				}

				i = firstNodeIndex+1;
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
