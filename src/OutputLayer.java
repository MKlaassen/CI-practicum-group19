import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
public class OutputLayer extends Layer {

	private ArrayList<Neuron> neurons;
	 
	public OutputLayer(int number_neurons, int a_inputs) {
		super(number_neurons, a_inputs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double[] calculate_Gradients(double[] outputsfinal, double[] inputs, double[] desiredoutputs, double alpha)
	{
    	double[] gradients = new double[neurons.size()];
		double[] errors = new double[neurons.size()];
		
		for(int i=0;i<neurons.size();i++)
		{
			errors[i]=desiredoutputs[i]-outputsfinal[i];
		}
		
		for(int i=0;i<neurons.size();i++)
		{
			gradients[i]=outputsfinal[i]*(1-outputsfinal[i])*errors[i];
		}
		
		update_Weights(alpha, inputs, gradients);

    	
  
    	return gradients;
    	
    		
	}

}
