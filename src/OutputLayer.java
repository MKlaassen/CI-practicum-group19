import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
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
      errors[i]=doutput[i]-output[i];
    }

    for(int i=0;i<neurons.size();i++)
    {
      gradients[i]=output[i]*(1-output[i])*errors[i];
    }
    
    layergradients = gradients; 
    update_Weights(alpha, input);

    return gradients;


  }
  
  public double[] getGradients() {
    return layergradients;
  }

}
