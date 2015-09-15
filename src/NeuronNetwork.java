import java.util.ArrayList;

import com.sun.deploy.uitoolkit.impl.fx.ui.UITextArea;

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
  
  public NeuronNetwork(int h, int n, int in, int out){
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
    for(int i = 1; i <= hiddenlayers_amount; i++){
      layers.add(new Layer(neuronsperlayer, layers.get(i-1).size() ) );
    }
    
    //Make the outputlayer
    Layer outgoinglayer = new Layer(outgoing_amount, layers.get(layers.size()-1).size());
    layers.add(outgoinglayer);
  }
  
  public double[] calculate_Hiddenoutputs(double[] inputs)
  {	 
	  double[] outputvector = new double[inputs.length];
	  int outputsize = 0;
	 
	  for(int j=1; j<=(hiddenlayers_amount);j++)
	  {
		  
		 for(int i = 0; i < (layers.get(j).size()); i++)
		 {
			 outputvector[i] = layers.get(j).get(i).calculate_Output(inputs);
		 }
		 
	//the outputs of the previous layer are the inputs for all the neurons on the next layer
	 inputs=null;
	//resize the output-vector array because, output-vector has a static size and the input a variable size dependent on the layer
	 inputs = arrayresize(outputvector,(layers.get(j).size())); 
	  outputsize = layers.get(j).size();
	  }
	return arrayresize(outputvector,outputsize);

  }
  
  public double[] calculate_Finaloutputs(double[] inputs)
  {	 
	  double[] outputvector = new double[outgoing_amount];
		  
		 for(int i = 0; i < outgoing_amount; i++)
		 {
			 //layer hiddenlayers_amount+1 is the last layer / output layer
			 outputvector[i] = layers.get(hiddenlayers_amount+1).get(i).calculate_Output(inputs);
		 }

	return outputvector;

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
  
  
  //calculate output errorgradients and modify output weights
  public double[] update_Outputweights(double[] outputs,double[] hiddenoutputs, double[] errorvalues, double alpha)
	{
		
	  double[] errorgradients = new double[outputs.length];
	  double[][] weightcorrections = new double[hiddenoutputs.length][outputs.length];
	  double tempweight;
	  //[step 3a.]
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
	 
	  //[step 3b.
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
  
 }
  
  
  
