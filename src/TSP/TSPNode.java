package TSP;

import java.util.ArrayList;
import Maze.Coordinate;

public class TSPNode {

	private ArrayList<Integer> pathlengths;
	private Coordinate coord;
	
	public TSPNode(Coordinate coord){
		pathlengths = new ArrayList<>();
		this.coord = coord;
	}

	public void addPathLength(int pathLength) {
		pathlengths.add(pathLength);
	}

	public ArrayList<Integer> getPathLengths() {
		return pathlengths;
	}
	

	public Coordinate getCoord() {
		return coord;
	}


	
}
