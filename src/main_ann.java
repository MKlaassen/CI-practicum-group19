import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class main_ann {
//parameters of the network	
private static int hiddenlayers_amount = 1;
private static int neuronsperlayer = 60;
private static int incoming_amount = 10;
private static int outgoing_amount = 7;
private static double alpha = 0.1;
private static int epochs = 50;	
	
private static double[][] features = new double[7854][10];
private static int[] targets = new int[7854];
private static double[][][] fold = new double[7][(targets.length)/7][10];
private static double[] testinputs = new double[10];
private static double[] testoutputs = new double[7];
private static double[] hiddenoutputs = new double[7];
private static double[] desiredoutputs = new double[7];
private static double[] errorvalues = new double[7];
private static double[] outputgradients = new double[7];
private static double sum_err_val = 0.0;
private static double[] output_targets = new double[7854];

	public static void main(String[] args) {
    
 	    //Make a neural network named n1 with the selected parameters.
 		NeuronNetwork n1 = new NeuronNetwork(hiddenlayers_amount, neuronsperlayer, outgoing_amount, incoming_amount);
		
 		
		//read features.txt and put into 2-dim array features
		readFeatures();
		
		//read targets.txt and put into 1-dim array targets
		readTargets();
		
		//divide features in 7 folds, 1 test fold, 1 validation fold, 5 training folds
		int num = (targets.length)/7;
		for(int f=0;f<7;f++)
		{
			for(int k=0;k<num;k++)
			{
				for(int m=0;m<10;m++)
				{
					fold[f][k][m] = features[k][m];
				}
			}
		}
		
		
		
		
		
		
		
		//run code for each epoch:
		for(int i=0;i<=epochs-1;i++)
		{
			System.out.println("epoch" + (i+1));
			System.out.println();
			//reset sum of errors every epoch
			sum_err_val=0;
		
			//for every input line of features.txt
			for(int k=0;k<7854;k++)
			{
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
					
				//calculate the outputs
				//testoutputs = n1.calculate_Outputs(testinputs, n1.size()-1);
				
				//found outputs in targets.txt format for comparison later, highest value decides the number since use of sigmoid this outputs a integer instead of a double
				//output_targets[k] = index_Highestvalue(testoutputs)+1;
		
				
				//begin of backpropagation:
				n1.update(desiredoutputs, alpha, testinputs);
				
				testoutputs = n1.calculate_Outputs(testinputs, hiddenlayers_amount+1);
				
				//calculate the errorvalues
				errorvalues = calculate_Errorvalues(testoutputs,desiredoutputs);
		
				//calculate sum of all errors each epoch
				for(int j=0;j<errorvalues.length;j++)
				{
					sum_err_val=sum_err_val+errorvalues[j]*errorvalues[j]; 
				}
		
				
				output_targets[k] = index_Highestvalue(testoutputs)+1;
		
			}//at this point 1 line of 10 input values is processed, returning to next line.
		
			//After each epoch:					
			//print all outputs of epoch i
			for(int j=0;j<testoutputs.length;j++)
			{
				System.out.println((j+1) + ". " + testoutputs[j]);
			}
				
			System.out.println();
				
			//print all errorvalues and compute the sum of the errorvalues
			for(int j=0;j<errorvalues.length;j++)
			{
				System.out.println((j+1) + ". " + errorvalues[j]);
			}
				
			//print sum of all errorvalues squared for each epoch
			System.out.println();
			System.out.println("Sum of errorvalues squared:" + (sum_err_val));
			System.out.println();
		
		}	
		//store output_of targets of last epoch into a txt file
		array_to_txtfile(output_targets);
		
		//store parameters into a txt file
		data_to_txtfile();
		
		
	}
	
	public static void run_test_fold(NeuronNetwork n)
	{
		for(int k=0;k<(targets.length)/7;k++)
		{
			//read 10 new values as inputs
			for(int m=0;m<10;m++)
			{
				testinputs[m]=features[k][m];
			}
		
			//put zero's in the desiredoutputs vector
			for(int z=0;z<desiredoutputs.length;z++)
			{	
				desiredoutputs[z]=0.0;
			}
		
			//only the desiredouput that corrosponds to the target is set to 1
			desiredoutputs[targets[k]-1]=1.0;
				
			//begin of backpropagation:
			n.update(desiredoutputs, alpha, testinputs);
			
			testoutputs = n.calculate_Outputs(testinputs, hiddenlayers_amount+1);
			
			//calculate the errorvalues
			errorvalues = calculate_Errorvalues(testoutputs,desiredoutputs);
	
			//calculate sum of all errors
			for(int j=0;j<errorvalues.length;j++)
			{
				sum_err_val=sum_err_val+errorvalues[j]*errorvalues[j]; 
			}
			
			output_targets[k] = index_Highestvalue(testoutputs)+1;
	
		}//at this point 1 line of 10 input values is processed, returning to next line.
	}
	
	public static void array_to_txtfile(double[] data)
	{
		//Prints the data and epoch number to textfile output.txt
		PrintWriter writer;
		try {
			writer = new PrintWriter("src\\output.txt", "UTF-8");
			for(int i=0; i<data.length;i++)
			{
				writer.println((int)data[i]);
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void data_to_txtfile()
	{
		//Prints the data and epoch number to textfile output.txt
		PrintWriter writer;
		try {
			writer = new PrintWriter("src\\parameters.txt", "UTF-8");
				String temp = "hiddenlayers amount: ";
				temp = temp + (int)hiddenlayers_amount;
				writer.println((String)temp);
				temp = "neurons per layer: ";
				temp = temp + (int)neuronsperlayer;
				writer.println((String)temp);
				temp = "incoming amount: ";
				temp = temp + (int)incoming_amount;
				writer.println((String)temp);
				temp = "outgoing amount: ";
				temp = temp + (int)outgoing_amount;
				writer.println((String)temp);
				temp = "alpha: ";
				temp = temp + (double)alpha;
				writer.println((String)temp);
				temp = "epochs: ";
				temp = temp + (int)epochs;
				writer.println((String)temp);
				
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public static int index_Highestvalue(double[] testoutputs)
	{
		//returns indexvalue of highestvalue in testoutputs;
		double min_Value = Double.MIN_VALUE;
		int index =0;
		for(int i=0;i<testoutputs.length;i++)
		{
			if(testoutputs[i]>min_Value)
			{
				min_Value=testoutputs[i];
				index=i;
			}
		}
		return index;
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
		
		for(int i=0; i<7853; i++){
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
		        	targets[i++]=sc.nextInt();
		        }

		    } catch (FileNotFoundException e1) {
		            e1.printStackTrace();
		    }
		
		
		sc.close();
	}
	
}
