package TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Chromosome {

	private ArrayList<boolean[]> genome = new ArrayList<boolean[]>();
	private int[] randomNumList;
	private int max;
	private int min;

	public Chromosome(Network network)
	{
		TSPNode[] tspnodes = network.getTspnodes();
		
		Random rand = new Random();
		max = tspnodes.length;
		min = 0;
		int randomNum = rand.nextInt((max - min)) + min;
		randomNumList = new int[max];
		//fill with -1s
		for(int i=0;i<randomNumList.length;i++)
		{
			randomNumList[i] = -1;
		}
		
		for(int i=0;i<max;i++)
		{
			if(!contains(randomNum))
			{
				randomNumList[i] = randomNum;
			}
			else
			{
				i--;
				randomNum = rand.nextInt((max - min)) + min;
			}	
		}
		
		System.out.println(randomNumList.toString());
		
		for(int i=0;i<randomNumList.length;i++)
		{
			genome.add(toBits(randomNumList[i],5));
		}
		
	}

	public static boolean[] toBits(int n){
		int power = 0;
		while (Math.pow(2, power+1)<=n){
			power++;
		}
		int maxPower = power;
		boolean[] res = new boolean[power+1];
		while (power>=0){
			if (Math.pow(2, power)<=n){
				res[maxPower-power] = true;
				n-=Math.pow(2, power);
			}
			else{
				res[maxPower-power] = false;
			}

			power--;
		}
		return res;

	}
	
	public static boolean[] toBits(int n, int length){
		  boolean[] binary = toBits(n);
		  boolean[] res = new boolean[length];
		  for (int i = 0; i<binary.length; i++){
		   res[i+(length-binary.length)] = binary[i];
		  }
		  return res;
		 }

	private boolean contains(int number)
	{
		for(int i=0;i<randomNumList.length;i++)
		{
			if(randomNumList[i]==number)
				return true;
		}
		return false;
	}
	
	public void mutate()
	{
		Random rand = new Random();
		int randomNum1 = rand.nextInt((max - min)) + min;
		int randomNum2 = rand.nextInt((max - min)) + min;
		
		boolean[] temp = genome.get(randomNum1);
		genome.set(randomNum1, genome.get(randomNum2));
		genome.set(randomNum2, temp);
		
	}

}
