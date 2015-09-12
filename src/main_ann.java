
public class main_ann {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int hiddenlayers_amount = 1;
	    int neuronsperlayer = 5;
	    int incoming_amount = 10;
	    int outgoing_amount = 7;
	    double[] testinputs = new double[10];
	    double[] testoutputs = new double[7];
	    
		NeuronNetwork n1 = new NeuronNetwork(hiddenlayers_amount, neuronsperlayer, incoming_amount, outgoing_amount);
		
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
		
		testoutputs = n1.calculate_Outputs(testinputs);
		
		for(int i=0;i<testoutputs.length;i++)
		System.out.println(testoutputs[i]);
	}

}
