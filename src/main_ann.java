
public class main_ann {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int hiddenlayers_amount = 1;
	    int neuronsperlayer = 5;
	    int incoming_amount = 10;
	    int outgoing_amount = 7;
	    double[] testinputs = new double[10];
	    double[] testoutputs = new double[7];
	    int testdesired;
	    double[] desiredoutputs = new double[7];
	    double[] errorvalues = new double[7];
 	    double[] errorgradient = new double[7];
 	    double alpha = 0.1;
 	    double epochs = 10;
 	    double[] outputgradients = new double[7];
 	    
 		NeuronNetwork n1 = new NeuronNetwork(hiddenlayers_amount, neuronsperlayer, incoming_amount, outgoing_amount);
		
		//features.txt must be imported here, this is for testing purposes
		testinputs[0]=0.57855;
		testinputs[1]=0.82114;
		testinputs[2]=1.2148;
		testinputs[3]=0.72998;
		testinputs[4]=0.34868;
		testinputs[5]=0.99462;
		testinputs[6]=-0.018461;
		testinputs[7]=0.92471;
		testinputs[8]=0.24418;
		testinputs[9]=0.063001;
		
		testdesired = 7;
		desiredoutputs[testdesired-1]=1.0;
		
		
		//run code for each epoch:
		for(int i=0;i<=epochs-1;i++)
		{
		System.out.println("epoch" + (i+1));
		System.out.println();
		//RESERVED
		//functie om iedere keer 10 nieuwe data elementen in te lezen moet hier komen en for loop onder epoch loop om alle data te verwerken
		//RESERVED
			
		//testoutputs resize to 7 because last 3 elements are zero (not connected)
		testoutputs = NeuronNetwork.arrayresize(n1.calculate_Outputs(testinputs),7);
		
		errorvalues = calculate_Errorvalues(testoutputs,desiredoutputs);
		
		//print all outputs
		for(int j=0;j<testoutputs.length;j++)
		{
		System.out.println((j+1) + ". " + testoutputs[j]);
		}
		
		System.out.println();
		
		for(int j=0;j<errorvalues.length;j++)
		{
		System.out.println((j+1) + ". " + errorvalues[j]);
		}
		
		//begin of backpropagation
		
		//update the output weights
		outputgradients = n1.update_Outputweights(testoutputs,errorvalues,alpha);
		
		n1.update_Hiddenweights(testoutputs, alpha, outputgradients, testinputs);
		
		}
		
		
	}
	
	
	//calculates the errorvalues (desiredoutput - realoutput)
	public static double[] calculate_Errorvalues(double[] outputs, double[] desiredoutputs)
	{
		double[] errorv = new double[7];
		for(int i=0;i<=(6);i++)
		{
			errorv[i]=desiredoutputs[i]-outputs[i];
		}
		return errorv;
	}
				

}
