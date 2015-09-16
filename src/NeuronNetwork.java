import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Matthijs, Rick, Daniël
 * date: 11-09-2015
 */
public class NeuronNetwork {

	private int hiddenlayers_amount;
	private int neuronsperlayer;
	private int incoming_amount;
	private int outgoing_amount;
	private ArrayList<Layer> layers;

	public NeuronNetwork(int h, int n, int in, int out)
	{
		hiddenlayers_amount = h;
		neuronsperlayer = n;
		incoming_amount = in;
		outgoing_amount = out;

		layers = new ArrayList<>(); 

		//Make the input layer, which has 1 weight per neuron:
		Layer incominglayer = new Layer(incoming_amount, 1);
		layers.add(incominglayer);

		//Weight of input layer are all 1
		for(int i=0;i<incoming_amount;i++)
		{
			layers.get(0).get(i).set_Weight(0,1);
		}

		//Make the hiddenlayers
		for(int i = 1; i <= hiddenlayers_amount; i++)
		{
			layers.add(new Layer(neuronsperlayer, layers.get(i-1).size()) );
		}

		//Make the outputlayer
		OutputLayer outgoinglayer = new OutputLayer(outgoing_amount, layers.get(layers.size()-1).size());
		layers.add(outgoinglayer);
	
	}

	public double[] calculate_Outputs(double[] inputs, int layer)
	{	
		if(layer == 0) return inputs;
		double[] outputs = calculate_Outputs(layers.get(layers.size()-(layer+1)).calculate_Outputs(inputs), layer-1);
		return outputs;

	}
	
	public void update(double alpha, double[] inputs, double[] desiredoutputs)
	{
		layers.get(layers.size()-1).calculate_Gradients(output, input, gradients, alpha);
		for(int i = layers.size()-1 ; i > 0 ; i--) {
			layers.get(i).calculate_Gradients(output, input, gradients, alpha);
		}
	}

//	public void calculate_Weights(double alpha, double[] inputs, double[] desiredoutputs)
//	{
//		double[] gradients = new double[outgoing_amount];
//		double[] outputs = new double[outgoing_amount];
//		double[] errors = new double[outgoing_amount];
//		
//		outputs=calculate_Outputs(inputs,layers.size()-1);
//		
//		for(int i=0;i<outgoing_amount;i++)
//		{
//			errors[i]=desiredoutputs[i]-outputs[i];
//		}
//		
//		for(int i=0;i<outgoing_amount;i++)
//		{
//			gradients[i]=outputs[i]*(1-outputs[i])*errors[i];
//		}
//		
//		layers.get(layers.size()-1).update_Weights(alpha, calculate_Outputs(inputs,layers.size()-2), gradients);
//		
//	}

	public void calculate_Hiddenweights(double alpha, double[] inputs)
	{
		//functie aanroepen!!!
	}
	
	
	

	//calculate output errorgradients and modify output weights
	public double[] update_Outputweights(double[] outputs,double[] hiddenoutputs, double[] errorvalues, double alpha)
	{
		double[] errorgradients = new double[outputs.length];
		double[][] weightcorrections = new double[hiddenoutputs.length][outputs.length];
		double tempweight;
		//[step 3a. from the book]
		//calculate error gradient for the neurons in the output layer 
		for(int k=0;k<outputs.length;k++)
		{
			errorgradients[k]=outputs[k]*(1-outputs[k])*errorvalues[k];
		}

		//calculate the weight corrections
		for(int j=0;j<hiddenoutputs.length;j++)
		{
			for(int k=0;k<outputs.length;k++)
			{
				weightcorrections[j][k]=alpha*hiddenoutputs[j]*errorgradients[k];
			}
		}

		//update the weights at the hidddenlayer
		for(int j=0;j<hiddenoutputs.length;j++)
		{
			for(int k=0;k<outputs.length;k++)
			{

				tempweight = layers.get(hiddenlayers_amount+1).get(k).get_Weight(j);
				layers.get(hiddenlayers_amount+1).get(k).set_Weight(j,tempweight+weightcorrections[j][k]);
			}
		}

		return errorgradients;
	}


	public void update_Hiddenweights(double[] outputs,double[] hiddenoutputs, double alpha, double[] outputgradients, double[] inputs)
	{
		double[] errorgradients = new double[hiddenoutputs.length];
		double[][] weightcorrections = new double[inputs.length][hiddenoutputs.length];

		//[step 3b. from the book]
		//calculate error gradient for the neurons in the hidden layer 
		for(int j=0;j<(hiddenoutputs.length);j++)
		{
			double sum=0.0;
			for(int k=0;k<(outputs.length);k++)
			{
				sum = sum+outputgradients[k]*layers.get(hiddenlayers_amount+1).get(k).get_Weight(j);
			}
			errorgradients[j]=hiddenoutputs[j]*(1-hiddenoutputs[j])*sum;

		}

		//calculate the weight corrections
		for(int i=0;i<inputs.length;i++)
		{
			for(int j=0;j<hiddenoutputs.length;j++)
			{
				weightcorrections[i][j]=alpha*inputs[i]*errorgradients[j];
			}
		}

		//update the weights at the hidden neurons
		for(int i=0;i<inputs.length;i++)
		{
			double tempweight;
			for(int j=0;j<hiddenoutputs.length;j++)
			{
				tempweight = layers.get(hiddenlayers_amount).get(j).get_Weight(i);
				layers.get(hiddenlayers_amount).get(j).set_Weight(i,tempweight+weightcorrections[i][j]);
			}
		}  
	}

	//method for resizing an array
	public static double[] arrayresize(double[] array, int index)
	{
		double[] output = new double[index];
		for(int i=0;i<=index-1;i++)
		{
			output[i]=array[i];
		}
		return output;
	}

}



