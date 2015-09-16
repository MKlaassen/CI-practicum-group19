import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Matthijs, Rick, Daniël
 * date: 11-09-2015
 */
public class Layer {
    private ArrayList<Neuron> neurons;
    
    public Layer(int number_neurons, int a_inputs){
      neurons = new ArrayList<>();
      
      for(int i = 0 ; i< (number_neurons); i++) {
        neurons.add(new Neuron(a_inputs));
      }
    }
    
    public double[] calculate_Outputs(double[] inputs)
    {	 
  	  double[] outputvector = new double[neurons.size()];
  	 
  		 for(int i = 0; i < (neurons.size()); i++)
  		 {
  			 outputvector[i] = this.get(i).calculate_Output(inputs);
  		 }
  	  return outputvector;
    }
    
    public void update_Weights(double alpha, double[] hiddenoutputs, double[] gradients)
    {
 
    	for(int j=0;j<neurons.size();j++)
    	{
    		for(int k=0;k<gradients.length;k++)
    		{
    			neurons.get(j).set_Weight(k,(neurons.get(j).get_Weight(k)+alpha*hiddenoutputs[j]*gradients[k]));
    		}
    	}
    	
    }
    
    public double[] calculate_Gradients(double[] output, double[] input, double[] gradients, double alpha)
    {
    	double[] new_gradients = new double[neurons.size()];
    	for(int j = 0;j<neurons.size();j++)
    	{
    		double sum = 0;
    		for(int k = 0;k<gradients.length;k++)
    		{
    			sum = sum + gradients[k]*neurons.get(j).get_Weight(k);
    		}
    		new_gradients[j]=output[j]*(1-output[j])*sum;
    	}
    	
    	//Update weights
    	update_Weights(alpha, input, new_gradients);
    	
    	
    	
    	return new_gradients;
    }
    public Neuron get(int i){
      return neurons.get(i);
    }
    
    public int size() {
      return neurons.size();
    }
    
    
    
}
