/**Class that is used to extend the layer class and construct a output layer containing neurons
 * @author Matthijs Klaassen, Rick Molenaar, Daniël Brouwer
 * @version: 18-09-2015
 */

import java.util.ArrayList;

public class OutputLayer extends Layer {
  
  public OutputLayer(int number_neurons, int a_inputs) {
    super(number_neurons, a_inputs);

    neurons = new ArrayList<>();

    for(int i = 0 ; i< (number_neurons); i++) {
      neurons.add(new Neuron(a_inputs));
    }
  }

  @Override
  public double[] calculate_Gradients(double[] output, double[] input, double[] doutput, double alpha, Layer nextlayer)
  {
    double[] gradients = new double[neurons.size()];
    double[] errors = new double[neurons.size()];

    for(int i=0;i<neurons.size();i++)
    {
    	//calculate the error between the desired output and the 'real' output
      errors[i]=doutput[i]-output[i];
    }

    //calculate the gradients of the output layer
    for(int i=0;i<neurons.size();i++)
    {
      gradients[i]=output[i]*(1-output[i])*errors[i];
    }
    
    layergradients = gradients; 
    //call the update_weights function to update the weights
    update_Weights(alpha, input);

    return gradients;


  }
  
  public double[] getGradients() {
	  //returns the gradients of this layer
    return layergradients;
  }

}
