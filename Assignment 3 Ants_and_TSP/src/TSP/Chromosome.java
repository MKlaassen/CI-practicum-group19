package TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Chromosome {

	private ArrayList<Integer> genome = new ArrayList<>();
	private int[] randomNumList;
	private int max;
	private int min;
	private float fitness;
	//private int pathLength;

	//	public static void main(String[] args){
	//		for (int i = 0; i<18; i++){
	//			System.out.println(i+","+Arrays.toString(toBits(i))+","+toInt(toBits(i)));
	//		}
	//	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
		if (Float.floatToIntBits(fitness) != Float.floatToIntBits(other.fitness))
			return false;
		if (genome == null) {
			if (other.genome != null)
				return false;
		} else if (!genome.equals(other.genome))
			return false;
		if (max != other.max)
			return false;
		if (min != other.min)
			return false;
		if (!Arrays.equals(randomNumList, other.randomNumList))
			return false;
		return true;
	}

	public Chromosome()
	{
		Random rand = new Random();
		max = Network.getTspnodes().size();
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


		for(int i=0;i<randomNumList.length;i++)
		{
			genome.add(randomNumList[i]);
		}

		fitness = 1.0f/getPathLength();
	}

	public float getFitness() {
		return fitness;
	}

	private void updateFitness(){
		fitness = 1.0f/getPathLength();
	}

	public int getPathLength(){
//		int sum = 0;
//		
//		for( int i = 0 ; i < genome.size() - 2 ; i++ ) {
//			
//			sum = sum + Network.getTspnodes().get(i).getPathLengths().get(i+1);
//		}
//		
//		return sum;
//		
//	}
		int sum = 0;

		//Add spawnpoint to first product path
		sum = sum + Network.getStartToNodeLengths().get(genome.get(0));
		//System.out.println("Sum: " + sum);

		for(int i=0;i<this.genome.size()-1;i++)
		{
			int startNodeIndex = genome.get(i); 
			int endNodeIndex = genome.get(i+1);

			if(endNodeIndex>startNodeIndex)
			{
				endNodeIndex--;
			}

			//System.out.println(startNodeIndex + "," + endNodeIndex);
			//Add pahts
			sum = sum + Network.getTspnodes().get(startNodeIndex).getPathLengths().get(endNodeIndex);
			//System.out.println("Sum2: " + sum);
		}
		//System.out.println("Sum " + sum);
		return sum;
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

	public static int toInt(boolean[] arr){
		int res = 0;
		for (int i = 0; i<arr.length; i++){
			if(arr[i]){
				res+=Math.pow(2, arr.length-i-1);
			}
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

	//Mutate the chromosome at random place
	public void mutate()
	{
		Random rand = new Random();
		int randomNum1 = rand.nextInt((max - min)) + min;
		int randomNum2 = rand.nextInt((max - min)) + min;

		int temp = genome.get(randomNum1);
		genome.set(randomNum1, genome.get(randomNum2));
		genome.set(randomNum2, temp);
		
		//recalulate fitness and pathlength
		fitness = 1.0f/getPathLength();
	}

	//CrossOver between 2 chromosomes
	public Chromosome crossOver(Chromosome chromosomeOther)
	{
		ArrayList<Integer> newGenome = new ArrayList<>();
		Chromosome parent1 = this;
		Chromosome parent2 = chromosomeOther;

		for(int i=0;i<this.getGenome().size();i++)
		{
			double randomNum2 = Math.random();
			int temp;

			//System.out.println("RandomNum: " + randomNum2);

			if(randomNum2>0.5f)
				temp = parent1.getGenome().get(i);
			else
				temp = parent2.getGenome().get(i);


			if(!newGenome.contains(temp))
			{
				newGenome.add(temp);
			}
			else
			{
				if(randomNum2>0.5f)
					temp = parent2.getGenome().get(i);
				else
					temp = parent1.getGenome().get(i);
				if(!newGenome.contains(temp))
				{
					newGenome.add(temp);
				}else
				{
					newGenome.add(18);
				}
			}
		}

		Chromosome returnChromosome = new Chromosome();
		returnChromosome.setGenome(newGenome);
		returnChromosome = replace(returnChromosome);
		returnChromosome.updateFitness();
		//for(int i=0;i<newGenome.size();i++)
		//{
		//	System.out.print(newGenome.get(i) + ",");
		//
		//}
		//System.out.println(" ");
		return returnChromosome;

	}

	public Chromosome replace(Chromosome chromosome)
	{

		ArrayList<Integer> intlist = new ArrayList<>();
		Chromosome retc = chromosome;

		for(int i=0;i<retc.getGenome().size();i++)
		{
			if(!retc.getGenome().contains((i))){
				intlist.add(i);

			}
		}

		if (intlist.isEmpty()) return chromosome;


		//System.out.println(intlist.toString());
		for(int i = 0 ; i < retc.getGenome().size(); i++) {
			if((retc.getGenome().get(i)) == 18) {

				retc.getGenome().remove(i);
				retc.getGenome().add(i, (intlist.remove(intlist.size()-1)));
			}
		}

		//for(int i=0;i<retc.getGenome().size();i++)
		//{
		//	System.out.print(retc.getGenome().get(i) + ",");
		//}
		return retc;
	}


	public ArrayList<Integer> getGenome() {
		return genome;
	}

	public void setGenome(ArrayList<Integer> genome) {
		this.genome = genome;
	}

}
