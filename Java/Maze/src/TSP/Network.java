package TSP;

import java.util.ArrayList;

import Ants.Ant;
import Ants.Coordinate;
import Ants.Maze;
import Ants.SuperAnt;

public class Network {

	private ArrayList<TSPNode> tspnodes;

	public Network(ArrayList<Coordinate> coordinates)
	{
		tspnodes = new ArrayList<TSPNode>();
		createTSPNodes(coordinates);

	}

	private void createTSPNodes(ArrayList<Coordinate> coordinates)
	{
		for(int i=0;i<coordinates.size();i++)
		{
			tspnodes.add(new TSPNode(coordinates.get(i)));
		}
	}

	public ArrayList<TSPNode> getTspnodes() {
		return tspnodes;
	}




		}
