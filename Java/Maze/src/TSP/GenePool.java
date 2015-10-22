package TSP;

import java.util.ArrayList;

public class GenePool {

	public ArrayList<Chromosome> chromosomes;
	
	public GenePool(int amountOfChromosomes,Network network){
		for(int i=0;i<amountOfChromosomes;i++)
		{
			chromosomes.add(new Chromosome(network));
		}
	}
	
}
