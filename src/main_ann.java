import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class main_ann {
static double[][] features = new double[7854][10];
static int[] targets = new int[7854];


	public static void main(String[] args) {
		//amount of hiddenlayers not yet adjustable
		int hiddenlayers_amount = 1;
	    int neuronsperlayer = 5;
	    int incoming_amount = 10;
	    int outgoing_amount = 7;
	    double[] testinputs = new double[10];
	    double[] testoutputs = new double[7];
	    double[] hiddenoutputs = new double[7];
	    double[] desiredoutputs = new double[7];
	    double[] errorvalues = new double[7];
 	    double alpha = 0.5;
 	    double epochs = 300;
 	    double[] outputgradients = new double[7];
 	    double sum_err_val = 0.0;
 	    double[] output_targets = new double[7854];
 	    
 	    //Make a neural network named n1 with the selected parameters.
 		NeuronNetwork n1 = new NeuronNetwork(hiddenlayers_amount, neuronsperlayer, outgoing_amount, incoming_amount);
		
		//read features.txt and put into 2-dim array features
		readFeatures();
		
		//read targets.txt and put into 1-dim array targets
		readTargets();
		
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
		
				//calculate the errorvalues
				//errorvalues = calculate_Errorvalues(testoutputs,desiredoutputs);
		
				//calculate sum of all errors each epoch
				//for(int j=0;j<errorvalues.length;j++)
				//{
				//	sum_err_val=sum_err_val+errorvalues[j]*errorvalues[j]; 
				//}
		
				//begin of backpropagation:
				n1.update(desiredoutputs, alpha, testinputs);
				
				testoutputs = n1.calculate_Outputs(testinputs, hiddenlayers_amount+1);
				
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
