package TSP;

import java.util.ArrayList;

public class GenePool {

	public ArrayList<Chromosome> chromosomes;

	public GenePool(){
		chromosomes = new ArrayList<Chromosome>();
	}

	public void createChromosomes(int amountOfChromosomes)
	{
		for(int i=0;i<amountOfChromosomes;i++)
		{
			chromosomes.add(new Chromosome());
		}
	}
	
	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void addChromosome(Chromosome chromosome)
	{
		chromosomes.add(chromosome);
	}

	public void mutateChromosomes(double P)
	{
		for(int i=0;i<chromosomes.size();i++)
		{
			double randomNum = Math.random();

			if(randomNum<P)
			{
				chromosomes.get(i).mutate();
			}
		}		
	}
	
	public Chromosome getBestChromosome()
	{
		float bestFitness = 0;
		int index = 0;
		bestFitness = chromosomes.get(0).getFitness();
		for(int i=0;i<chromosomes.size();i++)
		{
			if(chromosomes.get(i).getFitness()>bestFitness)
			{
				bestFitness = chromosomes.get(i).getFitness();
				index = i;
			}
		}
		
		return chromosomes.get(index);
	}

	public Chromosome selectChromosome()
	{
		float[] fitnessList = new float[chromosomes.size()];
		double randomNum = Math.random();
		float sumFitness[] = new float[chromosomes.size()];
		float sum = 0;

		for(int i=0;i<chromosomes.size();i++)
		{
			fitnessList[i] = chromosomes.get(i).getFitness();
			//System.out.println("Fitness: " + chromosomes.get(i).getFitness());
		}

		for(int i=0;i<fitnessList.length;i++)
		{
			sum = 0;
			for(int j=0;j<=i;j++)
			{
				sum = sum + fitnessList[j];
			}
			sumFitness[i] = sum;
		}

		for(int i=0;i<sumFitness.length;i++)
		{
			sumFitness[i] = sumFitness[i]/sumFitness[sumFitness.length-1];
		}

		for( int i = 0 ; i < sumFitness.length ; i++)
			if(randomNum<sumFitness[i])
			{
				return chromosomes.get(i);
			}

		return null;
	}

	public Chromosome crossOverChromosomes()
	{
		Chromosome temp1 = selectChromosome();
		Chromosome temp2 = selectChromosome();

		while(temp1.equals(temp2))
		{
			temp2 = selectChromosome();

		}
		return temp1.crossOver(temp2);
	}

}
