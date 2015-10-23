package Ants;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Maze.Coordinate;
import main.VariableManager;

public class AntManager {
	
	private static ArrayList<String> convergenceInformation = new ArrayList<String>();

	public static void releaseMethodI(Coordinate startcoord, Coordinate endcoord) {

		int iterations = 0;
		int winners = 0;

		//Allocating space for all the ants
		Ant[] antarray = new Ant[VariableManager.getAmountOfAnts()];

		//Instantiate the Ants
		for(int i=0;i<antarray.length;i++)
		{
			antarray[i] = new Ant(startcoord, endcoord);
		}

		//Releasing the ants into the maze. Next Ant gets released if the previous Ant reached its destination
		outerForLoop:
			for(int i=0;i<antarray.length;i++)
			{
				while(true)
				{
					iterations++;
					antarray[i].move();													
					//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
					if(antarray[i].getCurrentnode().equals(VariableManager.getMaze().getNode(endcoord)))
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
					if(VariableManager.isLimitIterations()==true && iterations==VariableManager.getMaxIterations())
					{
						convergenceInformation.add("Max stepss reached :(");
						break outerForLoop;
					}
				}
			}



	}

	public static void releaseMethodII(Coordinate startcoord, Coordinate endcoord) {

		int iterations = 0;
		int winners = 0;

		//Allocating space for all the ants
		Ant[] antarray = new Ant[VariableManager.getAmountOfAnts()];

		//Instantiate the Ants
		for(int i=0;i<antarray.length;i++)
		{
			antarray[i] = new Ant(startcoord, endcoord);
		}

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
						if(antarray[i].getCurrentnode().equals(VariableManager.getMaze().getNode(endcoord)))
						{
							System.out.println();
							System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
							convergenceInformation.add("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
							deadAnts[i]=true; //kill the Ant that reached the end destination
							antarray[i].splat();
							winners++;
							//steps = 0;
							if (winners>=VariableManager.getAmountOfWinners()){
								break OuterWhile;
							}
						}
					}
					if(VariableManager.isLimitIterations()==true && iterations==VariableManager.getMaxIterations())
					{
						convergenceInformation.add("Max stepss reached :(");
						break OuterWhile;
					}
				}

			}

	}


	public static ArrayList<Integer> releaseMethodIII(Coordinate startcoord, Coordinate endcoord) {
		int iterations = 0;
		int winners = 0;

		//Allocating space for all the ants
		Ant[] antarray = new Ant[VariableManager.getAmountOfAnts()];

		//Instantiate the Ants
		for(int i=0;i<antarray.length;i++)
		{
			antarray[i] = new Ant(startcoord, endcoord);
		}

		//Releasing all ants at once
		SecondOuterWhile:
			while(true)
			{
				for(int i=0;i<antarray.length;i++){
					iterations++;

					antarray[i].move();													
					//System.out.print("steps: " + steps + " Ant: " + i + " Current node: " + antarray[i].getCurrentnode().getCoordinate().toString() + "\r");
					if(antarray[i].getCurrentnode().equals(VariableManager.getMaze().getNode(endcoord)))
					{
						System.out.println();
						System.out.println("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");
						convergenceInformation.add("steps: " + antarray[i].getDirections().size() + " Ant: " + i +  " reached the destination (winner no. "+winners+")");

						antarray[i].splat();
						winners++;
						//steps = 0;
						if (winners>=VariableManager.getAmountOfWinners()){
							break SecondOuterWhile;
						}

						//Reinstantiating the Ants so they start over again
						for(int h=0;h<antarray.length;h++)
						{
						
							antarray[h] = new Ant(startcoord, endcoord);
						}
						//start over again with the new Ants
						break;

					}
					if(VariableManager.isLimitIterations()==true && iterations==VariableManager.getMaxIterations())
					{
						convergenceInformation.add("Max stepss reached :(");
						break SecondOuterWhile;
					}
				}
			}
		
		return antarray[winners-1].getDirections();
	}

	public static ArrayList<Integer> releaseSuperAnt(Coordinate startcoord, Coordinate endcoord)
	{		
		//Instantiate a SuperAnt
		Ant controlAnt = new SuperAnt(startcoord, endcoord);
		System.out.print("Releasing the Super Ant :D");

		while(true)
		{
			
			((SuperAnt)controlAnt).move();													
			//System.out.print("steps: " + steps + " SuperAnt: " + " Current node: " + controlAnt.getCurrentnode().getCoordinate().toString() + "\r");
			if(controlAnt.getCurrentnode().equals(VariableManager.getMaze().getNode(endcoord)))
			{
				System.out.println();
				System.out.println("SuperAnt reached the destination");
				convergenceInformation.add("steps: " + controlAnt.getDirections().size() + " SuperAnt reached the destination");
				//Update pheromone of path and kill the ant
				controlAnt.splat();
				break;
		
			}
			if(((SuperAnt)controlAnt).isStuck())
			{
				System.out.println("SuperAnt is stuck :(");
				convergenceInformation.add("steps: " + controlAnt.getDirections().size() + " SuperAnt is stuck :(");
				return null;
			}
		}
		return controlAnt.getDirections();
	}

}

