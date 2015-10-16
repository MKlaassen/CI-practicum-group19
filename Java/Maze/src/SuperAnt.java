import java.util.Random;

public class SuperAnt extends Ant{

	public SuperAnt(Maze maze, Coordinate start, Coordinate end, float alpha, float beta, float ec, int Q) {
		super(maze, start, end, alpha, beta, ec, Q);
	}

	public void move() 
	{
		float[] routeProbabilities = calculateRouteProbabilities(alpha,beta);
		float[] sumProbabilities = new float[routeProbabilities.length];
		int indexMax = 0;
		float temp;
		//generate random number between 0 and 1
		Random rand = new Random();
		//		float  randNum = (rand.nextInt(10) + 1)/10.0f;
		float randNum = rand.nextFloat();
		//		System.out.println("Randomnum: " + randNum);
		float sum;

		temp = routeProbabilities[0];
		for(int i=0;i<routeProbabilities.length;i++)
		{
			if(temp<routeProbabilities[i])
			{
				temp = routeProbabilities[i];
				indexMax = i;
			}
		}
		directions.add(getDirection(this.currentnode, this.currentnode.getNeighbors().get(indexMax)));	//Add correct direction
		travelToNode(this.currentnode.getNeighbors().get(indexMax));//travel to the best node
	}
}
