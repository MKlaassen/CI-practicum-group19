package Ants;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PathCalculator {

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


	public static void releaseAnts(int releaseMethod, int amountOfAnts, Maze maze, Coordinate startcoord, Coordinate endcoord, float alpha, float beta, float evaporationConstant, int Q, boolean limitIterations, int maxIterations, int amountOfWinners)
	{
		int iterations = 0;
		int winners = 0;
		
		//Allocating space for all the ants
		Ant[] antarray = new Ant[amountOfAnts];

		//Instantiate the Ants
		for(int i=0;i<antarray.length;i++)
		{
			antarray[i] = new Ant(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);
		}

		if(releaseMethod==1)
		{

			//Releasing the ants into the maze. Next Ant gets released if the previous Ant reached its destination
			outerForLoop:
				for(int i=0;i<antarray.length;i++)
				{
					while(true)
					{
						iterations++;
						antarray[i].move();													
						//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
						if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
						{
							System.out.println();
							System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
							convergenceInformation.add("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
							winners++;
							antarray[i].splat();
							//reset the steps taken
							//steps=0;
							break;
						}
						if(limitIterations==true && iterations==maxIterations)
						{
							convergenceInformation.add("Max stepss reached :(");
							break outerForLoop;
						}
					}
				}
		}
		else if(releaseMethod==2)
		{
			//Releasing all ants at once
			boolean deadAnts[] = new boolean[antarray.length];
			OuterWhile:
				while(true)
				{
					for(int i=0;i<antarray.length;i++){
						iterations++;
						if(deadAnts[i]==false)
						{
							antarray[i].move();													
							//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
							if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
							{
								System.out.println();
								System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
								convergenceInformation.add("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
								deadAnts[i]=true; //kill the Ant that reached the end destination
								antarray[i].splat();
								winners++;
								//steps = 0;
								if (winners>=amountOfWinners){
									break OuterWhile;
								}
							}
						}
						if(limitIterations==true && iterations==maxIterations)
						{
							convergenceInformation.add("Max stepss reached :(");
							break OuterWhile;
						}
					}

				}
		}else if(releaseMethod==3)
		{
			//Releasing all ants at once
			boolean lastWinner = true;
			SecondOuterWhile:
				while(true)
				{
					if (lastWinner){
						//						lastWinner = false;
						//						for(int i=0;i<(maze.getNodes().size());i++)
						//						{
						//							System.out.println("Node: " + i + " Pheromone of leaving paths: " + maze.getNodes().get(i).getPheromone().toString());
						//						}
					}
					for(int i=0;i<antarray.length;i++){
						iterations++;

						antarray[i].move();													
						//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
						if(antarray[i].getCurrentnode().equals(maze.getNode(endcoord)))
						{
							System.out.println();
							System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
							convergenceInformation.add("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");

							antarray[i].splat();
							winners++;
							//steps = 0;
							if (winners>=amountOfWinners){
								break SecondOuterWhile;
							}

							//Reinstantiating the Ants so they start over again
							for(int h=0;h<antarray.length;h++)
							{
								lastWinner = true;
								antarray[h] = new Ant(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);
							}
							//start over again with the new Ants
							break;

						}
						if(limitIterations==true && iterations==maxIterations)
						{
							convergenceInformation.add("Max stepss reached :(");
							break SecondOuterWhile;
						}
					}
				}
		}
	}

	public static ArrayList<Integer> releaseSuperAnt(int releaseMethod, int amountOfAnts, Maze maze, Coordinate startcoord, Coordinate endcoord, float alpha, float beta, float evaporationConstant, int Q, boolean limitIterations, int maxIterations)
	{		
		//Instantiate a SuperAnt
		Ant controlAnt = new SuperAnt(maze, startcoord, endcoord, alpha, beta, evaporationConstant,Q);
		int iterations = 0;
		System.out.print("Releasing the Super Ant :D");

		while(true)
		{
			iterations++;
			((SuperAnt)controlAnt).move();													
			//System.out.print("steps: " + steps + " SuperAnt: " + " Current node: " + controlAnt.getCurrentnode().getCoordinate().toString() + "\r");
			if(controlAnt.getCurrentnode().equals(maze.getNode(endcoord)))
			{
				System.out.println();
				System.out.println("SuperAnt reached the destination");
				convergenceInformation.add("steps: " + controlAnt.getDirections().size() + " SuperAnt reached the destination");
				//Update pheromone of path and kill the ant
				controlAnt.splat();

			
				//Write directions of superAnt to file
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("directions.txt", "UTF-8");
				} catch (FileNotFoundException e) {} 
				catch (UnsupportedEncodingException e) {}
				writer.println(controlAnt.getDirections().size() + ";");
				writer.println(startcoord.getX() + ", " + startcoord.getY() + ";");
				writer.println(controlAnt.getDirections().toString().replace(", ", ";").replace("[","").replace("]", ";"));
				writer.flush();
				writer.close();
				break;
			}
			if(((SuperAnt)controlAnt).isStuck())
			{
				System.out.println("Because SuperAnt is stuck :(");
				convergenceInformation.add("steps: " + controlAnt.getDirections().size() + " SuperAnt is stuck :(");
				break;
			}
		}
		return controlAnt.getDirections();
	}

	public static void writeConvergenceInformation(int releaseMethod, int amountOfAnts, Maze maze, Coordinate startcoord, Coordinate endcoord, float alpha, float beta, float evaporationConstant, int Q, boolean limitIterations, int maxIterations, int amountOfWinners, String mazeDifficulty, String fileName)
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
		writer.println("evaporationConstant: " + evaporationConstant);
		writer.println("alpha: " + alpha);
		writer.println("beta: " + beta);
		writer.println("Q: " + Q);
		writer.println("amountOfAnts: " + amountOfAnts);
		writer.println("mazeDifficulty: " + mazeDifficulty);
		writer.println("maxIterations: " +  maxIterations);
		writer.println("amountOfWinners: " + amountOfWinners);

		writer.flush();
		writer.close();
	}

}



