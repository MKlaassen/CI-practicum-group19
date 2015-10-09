
public class Node {

	private float pheromone;
	
	public Node(Coordinate coord)
	{
		pheromone = 1;
	}
	
	public void update(float evaporationConstant, float releasedPheromone)
	{
		pheromone = (1-evaporationConstant) * pheromone + releasedPheromone;
	}

	public float getPheromone() {
		return pheromone;
	}

	@Override
	public String toString() {
		return "Node [pheromone=" + pheromone + "]";
	}

}
