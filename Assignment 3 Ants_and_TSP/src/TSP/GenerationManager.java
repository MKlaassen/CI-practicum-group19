package TSP;

public class GenerationManager {

	private GenePool activeGeneration;
	private static int generationSum;
	private double mutationChance = 0.1f;
	private int amountOfChromosomes = 100; //Amount of Chromosomes per Generation
	
	public GenerationManager()
	{
		activeGeneration = new GenePool();
		activeGeneration.createChromosomes(amountOfChromosomes);
	
	}
	
	public void generateNewGeneration(){
		GenePool nextGeneration = new GenePool();
		
		//System.out.println("Mutating Chromosomes");
		activeGeneration.mutateChromosomes(mutationChance); //mutate Chromosomes with P=mutationChance
		
		
		Chromosome temp;
		//System.out.println("Cross-overing Chromosomes");
		for(int i=0;i<amountOfChromosomes;i++)
		{
			//System.out.println("Adding cross-overed chromosome " + i + " to the new Generation");
			temp = activeGeneration.crossOverChromosomes();
			
			nextGeneration.addChromosome(temp);
		}	
		
		//System.out.println("Adding new generation");
		activeGeneration = nextGeneration;

		generationSum++;
		System.out.println("Amount of generations: " + generationSum);
		
	}
	
	public GenePool getActiveGeneration() {
		return activeGeneration;
	}

}
