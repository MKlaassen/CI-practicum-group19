package TSP;

import java.util.ArrayList;

import Ants.Ant;
import Ants.Coordinate;
import Ants.Maze;
import Ants.PathCalculator;
import Ants.SuperAnt;

public class Network {

	//private ArrayList<TSPNode> tspnodes;
	TSPNode[] tspnodes;
	private ArrayList<Coordinate> coordinates;

	public Network(ArrayList<Coordinate> coordinates)
	{
		//tspnodes = new ArrayList<TSPNode>();
		tspnodes = new TSPNode[coordinates.size()];
		createTSPNodes(coordinates);
		this.coordinates = coordinates;

	}

	private void createTSPNodes(ArrayList<Coordinate> coordinates)
	{
		for(int i=0;i<coordinates.size();i++)
		{
			tspnodes[i]=(new TSPNode(coordinates.get(i)));
		}
	}

	public TSPNode[] getTspnodes() {
		return tspnodes;
	}

	public void addAllPaths(int releaseMethod, int amountOfAnts, Maze maze, float alpha, float beta, float evaporationConstant, boolean limitIterations, int maxIterations, int amountOfWinners, String mazeDifficulty){
		for (int i = 0 ; i < coordinates.size() ; i++ ){
			for ( int j = i + 1 ; j < coordinates.size() ; j++){
				System.out.println("Adding " + j + " path to node: " + i);
				
				int Q =PathCalculator.estimateQ(mazeDifficulty, coordinates.get(i), coordinates.get(j));
				PathCalculator.releaseAnts(releaseMethod, amountOfAnts, maze, coordinates.get(i), coordinates.get(j), alpha, beta, evaporationConstant, Q, limitIterations, maxIterations, amountOfWinners,false);
				tspnodes[i].addPaths(PathCalculator.releaseSuperAnt(releaseMethod, amountOfAnts, maze, coordinates.get(i), coordinates.get(j), alpha, beta, evaporationConstant, Q, limitIterations, maxIterations,false));
				//Garbage Collect:
				System.gc();
			}
		}
		
	}
}
