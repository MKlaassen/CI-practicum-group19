package Ants;
import java.util.Random;

public class SuperAnt extends Ant{

	private Node previousNode;
	private boolean stuck = false;

	public boolean isStuck() {
		return stuck;
	}

	public SuperAnt(Maze maze, Coordinate start, Coordinate end, float alpha, float beta, float ec, int Q) {
		super(maze, start, end, alpha, beta, ec, Q);
	}

	public void move() 
	{
		float[] routeProbabilities = calculateRouteProbabilities(alpha,beta);
		float[] sumProbabilities = new float[routeProbabilities.length];
		int indexMax = 0;
		//generate random number between 0 and 1
		Random rand = new Random();
		//		float  randNum = (rand.nextInt(10) + 1)/10.0f;
		float randNum = rand.nextFloat();
		//		System.out.println("Randomnum: " + randNum);
		float sum;


		indexMax = indexOfHighest(routeProbabilities);

		//If the best route, is the route back -> dont let the Ant go there
		while(true)
		{
			if(!path.contains(this.currentnode.getNeighbors().get(indexMax)))
			{
				for(int h=0; h<this.currentnode.getDists().get(indexMax);h++)
				{
					directions.add(getDirection(this.currentnode, this.currentnode.getNeighbors().get(indexMax)));	//Add correct direction
				}	
				travelToNode(this.currentnode.getNeighbors().get(indexMax));//travel to the best node
				break;
			}else
			{
				routeProbabilities[indexMax] = 0; //Remove route indexMax from the list so it wont be chosen
				indexMax = indexOfHighest(routeProbabilities); //search for the new best route
				for(int r=0;r<routeProbabilities.length;r++)
				{
					stuck=true;
					if(routeProbabilities[r]!=0)
						stuck=false;
				}
				
				if(stuck)
				{
					System.out.println();
					System.out.println("SuperAnt: Sorry Master, but I am stuck :(");
					break;
				}
			}
		}

	}

	public int indexOfHighest(float[] routeProbabilities)
	{

		float temp = routeProbabilities[0];
		int indexMax = 0;

		for(int i=0;i<routeProbabilities.length;i++)
		{
			if(temp<routeProbabilities[i])
			{
				temp = routeProbabilities[i];
				indexMax = i;
			}
		}
		return indexMax;

	}
}