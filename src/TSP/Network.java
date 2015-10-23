package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import Maze.Coordinate;

public class Network {

	private static ArrayList<TSPNode> tspnodes;

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

	public static ArrayList<TSPNode> getTspnodes() {
		return tspnodes;
	}

	public void readPaths(){
		Scanner sc = null;
		for ( int i = 0 ; i < tspnodes.size(); i++ ){
			for ( int j = i + 1 ; j < tspnodes.size(); j++){
				//System.out.println("At i = " + i + " j = " + j);
				try {
					sc = new Scanner(new File("./Maze/directions/" + "directions_" + i + "_" + j + ".txt"));
				} catch (FileNotFoundException e) {System.out.println("File for directions not found!");} 
				String s = sc.next();
				int pathlength = Integer.valueOf(s.replace(";", ""));
				tspnodes.get(i).addPathLength(pathlength);
				//System.out.println("Added: " + pathlength + " to Node " + i );
				//System.out.println("Pathlength size of node: " + i + " is " + tspnodes.get(i).getPathLengths().size());
				tspnodes.get(j).addPathLength(pathlength);
				//System.out.println("Added: " + pathlength + " to Node " + j );

			}
		}
		sc.close();
	}

}
