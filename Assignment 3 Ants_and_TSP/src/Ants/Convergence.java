package Ants;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Maze.Coordinate;
import Maze.Maze;
import main.VariableManager;

public class Convergence {

	private static ArrayList<String> convergenceInformation = new ArrayList<String>();




	public static int estimateQ(String mazeDifficulty, Coordinate startcoord, Coordinate endcoord){
		//estimation of the route length.
		if (mazeDifficulty.equals("easy")||mazeDifficulty.equals("medium")){
			return Math.abs(startcoord.getX()-endcoord.getX()) + Math.abs(startcoord.getY()-endcoord.getY());
		}
		else{
			return 100*Math.abs(startcoord.getX()-endcoord.getX()) + Math.abs(startcoord.getY()-endcoord.getY());
		}
	}

	public static void writeMazeGraph(Maze maze)
	{
		PrintWriter writer = null;

		//Write the graph representation of the maze to a txt file
		try {
			writer = new PrintWriter("mazeNodes.txt", "UTF-8");
		} catch (FileNotFoundException e) {} 
		catch (UnsupportedEncodingException e) {}
		writer.println(maze.GraphtoString());
		writer.flush();
	}

	public static void writeConvergenceInformation(int releaseMethod, String fileName)
	{
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {} 
		catch (UnsupportedEncodingException e) {}
		writer.println("////Convergence Information:////");
		writer.println();
		for(int i=0;i<convergenceInformation.size();i++)
		{
			writer.println(convergenceInformation.get(i).toString());
		}
		writer.println();
		writer.println("////Used Parameters:////");
		writer.println();
		writer.println("releaseMethod: " + releaseMethod);
		writer.println("evaporationConstant: " + VariableManager.getEvaporationconstant());
		writer.println("alpha: " + VariableManager.getAlpha());
		writer.println("beta: " + VariableManager.getBeta());
		writer.println("Q: " + VariableManager.getQ());
		writer.println("amountOfAnts: " + VariableManager.getAmountOfAnts());
		writer.println("mazeDifficulty: " + VariableManager.getMazeDifficulty());
		writer.println("maxIterations: " +  VariableManager.getMaxIterations());
		writer.println("amountOfWinners: " + VariableManager.getAmountOfWinners());

		writer.flush();
		writer.close();
	}

}



