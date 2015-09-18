import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Matthijs
 *
 */
public class HiddenLayer extends Layer {


  public HiddenLayer(int number_neurons, int a_inputs) {
    super(number_neurons, a_inputs);

    neurons = new ArrayList<>();

    for(int i = 0 ; i< (number_neurons); i++) {
      neurons.add(new Neuron(a_inputs));
    }
  }

  public double[] calculate_Gradients(double[] output, double[] input, double[] gradients, double alpha, Layer nextlayer)
  {

    double[] new_gradients = new double[neurons.size()];
    double sum =0;

    for(int j=0;j<neurons.size();j++)
    {
      for(int k=0;k<nextlayer.size();k++)
      {
        sum += (nextlayer.getGradients())[k]*nextlayer.getNeurons().get(k).get_Weight(j);
      }
      new_gradients[j]=output[j]*(1-output[j])*sum;
    }

    layergradients = new_gradients;
    //calculate weight corrections:

    update_Weights(alpha, input);

    return null;
  }

  public double[] getGradients() {
    return layergradients;
  }

}
