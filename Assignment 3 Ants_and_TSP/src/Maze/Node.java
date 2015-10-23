package Maze;
import java.util.ArrayList;

/** Class that represents a Node containing an amount of pheromone and neighbournodes with their distances to this node. 
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Dani�l Brouwer
 * @version 9 October 2015
 */

public class Node {

	private ArrayList<Node> neighbors;
	private ArrayList<Integer> dists;
	private ArrayList<Float> pheromone;
	private Coordinate coordinate;

	public Node(Coordinate coord)
	{
		neighbors = new ArrayList<Node>();
		dists = new ArrayList<Integer>();
		pheromone = new ArrayList<Float>();
		setCoordinate(coord);
	}


	/** Update the path pheromone
	 * @param neighbor_index index for selecting one of the paths from this Node to a neighbor 'neighbor_index'
	 * @param evaporationConstant the evaporationconstant
	 * @param releasedPheromone the amount of pheromone released by the ant
	 */
	public void updatePheromone(int neighbor_index, float evaporationConstant, float releasedPheromone)
	{
		float new_pheromone = (1-evaporationConstant) * pheromone.get(neighbor_index) + releasedPheromone;
		pheromone.set(neighbor_index,new_pheromone);	

	}

	public ArrayList<Float> getPheromone() {
		return pheromone;
	}


	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	@Override
	public String toString() {
		String res= "Node [dists=" + dists + ", coordinate="
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
		//		System.out.println("Adding neighbor "+node.id+" to node " +this.id );
		this.neighbors.add(node);
		this.dists.add(dist);
		this.pheromone.add(1.0f);
		//		System.out.println(pheromone.size());
	}

	public void deleteNeighbor(Node node){
		for (int i = 0; i<neighbors.size(); i++){
			if (neighbors.get(i)==node){
				dists.remove(i);
				neighbors.remove(i);
				pheromone.remove(i);
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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		return true;
	}


}
