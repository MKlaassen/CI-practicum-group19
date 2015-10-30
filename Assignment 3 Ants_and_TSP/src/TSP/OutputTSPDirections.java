package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import Maze.Coordinate;

public class OutputTSPDirections {

	public static void writeToFile(int[] outputPath, int pathLength, ArrayList<Coordinate> coordinates, Network network){
		
		PrintWriter writer = null;

		//Write the graph representation of the maze to a txt file
		try {
			writer = new PrintWriter("./Maze/" + "directionsTSP.txt", "UTF-8");
		} catch (FileNotFoundException e) {} 
		catch (UnsupportedEncodingException e) {}
		
		
		int sum = 0;
		//read pathlength
		sum = sum + readPathLength("startcoord",Integer.toString(outputPath[0]));
		for(int i=0;i<outputPath.length-1;i++)
		{
			sum = sum + (readPathLength(Integer.toString(outputPath[i]), Integer.toString(outputPath[i+1])));
		}
		
		//Add actions for taking products
		sum = sum + outputPath.length;
		
		//correct pathlength
		pathLength = sum;
		
		//write the length of the path
		writer.println(pathLength + ";");
		
		//write the spawning coords
		Coordinate startcoord = Network.getStartCoord();
		writer.println(startcoord.getX() + ", " + startcoord.getY() + ";");
		
		//write path from spawn point to first product
		writer.println(readPath("startcoord", Integer.toString(outputPath[0])));
		writer.println("take product #" + (outputPath[0]) + ";");
		
		for(int i=0;i<outputPath.length-1;i++)
		{
			writer.println(readPath(Integer.toString(outputPath[i]), Integer.toString(outputPath[i+1])));
			//System.out.println("i= " + i);
			writer.println("take product #" + (outputPath[i+1]) + ";");
		}
		
		writer.flush();
		writer.close();
	}

	public static int readPathLength(String from, String to)
	{
		int length = 0;
		Scanner sc = null;

		try {
			sc = new Scanner(new File("./Maze/directions/" + "directions_" + from + "_" + to + ".txt"));
		} catch (FileNotFoundException e) {System.out.println("File for directions not found!");} 
		String s = sc.next();
		length = Integer.valueOf(s.replace(";", "")).intValue();
	
		sc.close();
		return length;
	}
	

	public static String readPath(String from, String to)
	{
		Scanner sc = null;

		try {
			sc = new Scanner(new File("./Maze/directions/" + "directions_" + from + "_" + to + ".txt"));
		} catch (FileNotFoundException e) {System.out.println("File for directions not found!");} 
		//skip steps
		sc.nextLine();
		//skip coords
		sc.nextLine();

		//Read all directions
		String output = sc.nextLine();
		
		sc.close();
		return output;
	}
}