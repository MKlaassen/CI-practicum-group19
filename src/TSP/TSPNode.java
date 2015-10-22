package TSP;

import java.util.ArrayList;
import Maze.Coordinate;

public class TSPNode {

	private ArrayList<ArrayList<Integer>> paths;
	private Coordinate coord;
	
	public TSPNode(Coordinate coord){
		paths = new ArrayList<>();
		this.coord = coord;
	}

	public void addPaths(ArrayList<Integer> path) {
		paths.add(path);
	}

	public ArrayList<ArrayList<Integer>> getPaths() {
		return paths;
	}

	public Coordinate getCoord() {
		return coord;
	}

	
}
