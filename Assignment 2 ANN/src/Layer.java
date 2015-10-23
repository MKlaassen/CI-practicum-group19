/**Class that is used to construct a layer containing neurons
 * @author Matthijs Klaassen, Rick Molenaar, Daniël Brouwer
 * @version: 18-09-2015
 */


import java.util.ArrayList;

public abstract class Layer {
    protected ArrayList<Neuron> neurons;
    protected double[] layergradients;
    private static int layernumber = 0;
    private int thisLayer = 0;
    
    public Layer(int number_neurons, int a_inputs){
      neurons = new ArrayList<>();
      thisLayer=layernumber;
      layernumber++;
      
      for(int i = 0 ; i< (number_neurons); i++) {
    	 //Add neurons to the layer
        neurons.add(new Neuron(a_inputs));
      }
     
    }
    
    public double[] calculate_Outputs(double[] inputs)
    {	 
    //calculate the output of the layer
  	  double[] outputs = new double[this.size()];
  	  
  	  for(int i = 0 ; i < outputs.length ; i++) {
  		  //call the calculate_output function for each neuron
  	    outputs[i] = neurons.get(i).calculate_Output(inputs);
  	  }
  	  return outputs;
    }
    
    public void update_Weights(double alpha, double[] hiddenoutputs)
    {
    	//merhod for updating the weights for each neuron on the layer
 
    	for(int k=0;k<neurons.size();k++)
    	{
    		for(int j=0;j<neurons.get(k).get_Weights().length-1;j++)
    		{
    			//calculate the weight corrections
    		  double weightcorrection = alpha*hiddenoutputs[j]*layergradients[k];
    		  //call the update_weight function for each neuron
    		  neurons.get(k).update_Weight(j, weightcorrection);
    		}
    	}
    	
    }

    public double[] calculate_Gradients(double[] output, double[] input, double[] doutput, double alpha, Layer nextlayer) {
      // method defined in output and hiddenlayer extensions
      return null;
    }
    
    public Neuron get(int i){
    	//return the neuron
      return neurons.get(i);
    }
    
    public int size() {
    	//return the size of the layer
      return neurons.size();
    }
    
    public ArrayList<Neuron> getNeurons() {
    	//return the layer
      return neurons;
    }
    
    public double[] getGradients(){
    	//return the gradients
      return layergradients;
    }
    
}
