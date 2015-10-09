import java.util.ArrayList;

public class Node {

	private float pheromone;
	private ArrayList<Node> neighbors;
	private ArrayList<Integer> dists;
	private Coordinate coordinate;
	
	public Node(Coordinate coord)
	{
		pheromone = 1;
		neighbors = new ArrayList<Node>();
		dists = new ArrayList<Integer>();
		setCoordinate(coord);
	}
	
	public void update(float evaporationConstant, float releasedPheromone)
	{
		pheromone = (1-evaporationConstant) * pheromone + releasedPheromone;
	}

	public float getPheromone() {
		return pheromone;
	}

	public void setPheromone(float pheromone) {
		this.pheromone = pheromone;
	}
	
	
	
	@Override
	public String toString() {
		return "Node [pheromone=" + pheromone + "]";
	}

	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Node> neighbors) {
		this.neighbors = neighbors;
	}
	
	public void addNeighbor(Node node, int dist){
		this.neighbors.add(node);
		this.dists.add(dist);
	}
	
	public void deleteNeighbor(Node node){
		for (int i = 0; i<neighbors.size(); i++){
			if (neighbors.get(i)==node){
				dists.remove(i);
				neighbors.remove(i);
				return;
			}
		}
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

}
