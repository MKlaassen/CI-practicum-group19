import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Matthijs, Rick, Daniël
 * date: 11-09-2015
 */
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
        neurons.add(new Neuron(a_inputs));
      }
     
    }
    
    public double[] calculate_Outputs(double[] inputs)
    {	 
  	  double[] outputs = new double[this.size()];
  	  
  	  for(int i = 0 ; i < outputs.length ; i++) {
  	    outputs[i] = neurons.get(i).calculate_Output(inputs);
  	  }
  	  return outputs;
    }
    
    public void update_Weights(double alpha, double[] hiddenoutputs)
    {
 
    	for(int k=0;k<neurons.size();k++)
    	{
    		for(int j=0;j<neurons.get(k).get_Weights().length-1;j++)
    		{
    		  double weightcorrection = alpha*hiddenoutputs[j]*layergradients[k];
    		  neurons.get(k).update_Weight(j, weightcorrection);
    		}
    	}
    	
    }

    public double[] calculate_Gradients(double[] output, double[] input, double[] doutput, double alpha, Layer nextlayer) {
      // TODO Auto-generated method stub
      return null;
    }
    
    public Neuron get(int i){
      return neurons.get(i);
    }
    
    public int size() {
      return neurons.size();
    }
    
    public ArrayList<Neuron> getNeurons() {
      return neurons;
    }
    
    public double[] getGradients(){
      return layergradients;
    }
    
}
