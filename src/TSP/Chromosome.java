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

		fitness = 1.0f/pathLength();
	}

	public float getFitness() {
		return fitness;
	}


	public int pathLength(){
		int sum = 0;

		for(int i=0;i<this.genome.size()-1;i++)
		{
			int startNodeIndex = genome.get(i); 
			int endNodeIndex = genome.get(i+1);

			if(endNodeIndex>startNodeIndex)
			{
				endNodeIndex--;
			}

			//System.out.println(startNodeIndex + "," + endNodeIndex);
			sum = sum + Network.getTspnodes().get(startNodeIndex).getPathLengths().get(endNodeIndex);
		}
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
	}

	//CrossOver between 2 chromosomes
	public Chromosome crossOver(Chromosome chromosomeOther)
	{
		ArrayList<Integer> newGenome = new ArrayList<>();
		Chromosome parent1 = this;
		Chromosome parent2 = chromosomeOther;
		
		boolean whichparent;

		for(int i=0;i<this.getGenome().size();i++)
		{
			Random rand = new Random();
			int randomNum1;
			double randomNum2 = Math.random();
			int temp;

			if(randomNum2>0.5){
				temp = parent1.getGenome().get(i);
				whichparent = true;
			}else {
				temp = parent2.getGenome().get(i);
				whichparent = false;
			}

			if(!newGenome.contains(temp))
			{

				newGenome.add(temp);
			}
			else
			{
				if( whichparent && !newGenome.contains(parent2.getGenome().get(i))) {
					newGenome.add(parent2.getGenome().get(i));
				}
				else if( !whichparent && !newGenome.contains(parent1.getGenome().get(i))){
					newGenome.add(parent1.getGenome().get(i));
				}
				else{
					newGenome.add(18);
				}
			}

		}

		Chromosome returnChromosome = new Chromosome();
		returnChromosome.setGenome(newGenome);

		for(int i=0;i<newGenome.size();i++)
		{
			System.out.print(newGenome.get(i) + ",");
		
		}
		System.out.println(" ");
		return replace(returnChromosome);

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
		

		System.out.println(intlist.toString());
		for(int i = 0 ; i < retc.getGenome().size(); i++) {
			if((retc.getGenome().get(i)) == 18) {
				
				retc.getGenome().remove(i);
				retc.getGenome().add(i, (intlist.remove(intlist.size()-1)));
			}
		}

		for(int i=0;i<retc.getGenome().size();i++)
		{
			System.out.print(retc.getGenome().get(i) + ",");
		}
		return retc;
	}


	public ArrayList<Integer> getGenome() {
		return genome;
	}

	public void setGenome(ArrayList<Integer> genome) {
		this.genome = genome;
	}

}
