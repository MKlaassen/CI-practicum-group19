import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.sun.jmx.snmp.internal.SnmpOutgoingRequest;

import java.util.Arrays;

public class main_ann {
static double[][] features = new double[7854][10];
static int[] targets = new int[7854];


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int hiddenlayers_amount = 1;
	    int neuronsperlayer = 5;
	    int incoming_amount = 10;
	    int outgoing_amount = 7;
	    double[] testinputs = new double[10];
	    double[] testoutputs = new double[7];
	    double[] hiddenoutputs = new double[7];
	    int testdesired;
	    double[] desiredoutputs = new double[7];
	    double[] errorvalues = new double[7];
 	    double alpha = 0.1;
 	    double epochs = 2;
 	    double[] outputgradients = new double[7];
 	    //amount of data lines to read from features.txt
 	    
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
		
		//read features.txt and put into 2-dim array features
		readFeatures();
		
		//read targets.txt
		readTargets();
		
		for(int p=0;p<500;p++)
		System.out.println(targets[p]);
		
		//run code for each epoch:
		for(int i=0;i<=epochs-1;i++)
		{
		System.out.println("epoch" + (i+1));
		System.out.println();
		
		//7854
		for(int k=0;k<1;k++)
		{
			
		System.out.println("Target: " + targets[k]);
			//read 10 new values as inputs
			for(int m=0;m<10;m++)
			{
			testinputs[m]=features[k][m];
			}
			
			//put zero's in the desiredoutputs vector
			for(int n=0;n<desiredoutputs.length;n++)
			{	
			desiredoutputs[n]=0.0;
			}
			//only the desiredouput that corrosponds to the target is set to 1
			desiredoutputs[targets[k]-1]=1.0;
					
		hiddenoutputs = n1.calculate_Hiddenoutputs(testinputs);
		testoutputs = n1.calculate_Finaloutputs(hiddenoutputs);
		
		errorvalues = calculate_Errorvalues(testoutputs,desiredoutputs);
		
		//begin of backpropagation
		
		//update the output weights
		outputgradients = n1.update_Outputweights(testoutputs,hiddenoutputs,errorvalues,alpha);
		
		n1.update_Hiddenweights(testoutputs,hiddenoutputs, alpha, outputgradients, testinputs);
		
		}
		
		//print all outputs of epoch i
				for(int j=0;j<testoutputs.length;j++)
				{
				System.out.println((j+1) + ". " + testoutputs[j]);
				}
				
				System.out.println();
				
				//print all errorvalues
				for(int j=0;j<errorvalues.length;j++)
				{
				System.out.println((j+1) + ". " + errorvalues[j]);
				}
		
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
				

	public static void readFeatures(){
		Scanner sc = null;
		try {
			sc = new Scanner(new File("src\\features.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		features[0] = new double[] {1,2,3,4,5,6,7,8,9,0};
		
		for(int i=0; i<7854; i++){
			String line = sc.next();
			String[] values = line.split(",");
			for (int j=0; j<10; j++){
				features[i][j] = Double.valueOf(values[j]);
			}
			}
		
		sc.close();
	}
	
	public static void readTargets(){
		Scanner sc = null;
		 try {
			 sc = new Scanner(new File("src\\targets.txt"));
			 int i=0;
		        while(sc.hasNextInt())
		        {
		        	targets[i]=sc.nextInt();
		            i=i++;
		        }

		    } catch (FileNotFoundException e1) {
		            e1.printStackTrace();
		    }
		
		
		sc.close();
	}
	
}
