import java.util.ArrayList;

/** Class that represents a Node containing an amount of pheromone and neighbournodes with their distances to this node. 
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */

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
	
	public void updatePheromone(float evaporationConstant, float releasedPheromone)
	{
		pheromone = (1-evaporationConstant) * pheromone + releasedPheromone;
	}

	public float getPheromone() {
		return pheromone;
	}

	public void setPheromone(float pheromone) {
		this.pheromone = pheromone;
	}
	

	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	@Override
	public String toString() {
		String res= "Node [pheromone=" + pheromone + ", dists=" + dists + ", coordinate="
				+ coordinate+", neighbors=["; 
		for (Node node : neighbors){
			res+=node.getCoordinate()+", ";
		}
		if (this.neighbors.size()!=0)
			res = res.substring(0,res.length()-2);
		res += "]]";
		return res;
	}

	public ArrayList<Integer> getDists() {
		return dists;
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
