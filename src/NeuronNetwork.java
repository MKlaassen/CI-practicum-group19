import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Matthijs, Rick, Daniël
 * date: 11-09-2015
 */
public class NeuronNetwork {


	
	private int hiddenlayer_amount;
	private int neurons_per_layer;
	private int n_outputs;
	private int incoming_amount;
	
	private ArrayList<Layer> layers;

	public NeuronNetwork(int hl_amount, int np_layer, int out, int in_amount)
	{
		layers = new ArrayList<>(); 
		hiddenlayer_amount = hl_amount;
		neurons_per_layer = np_layer;
		n_outputs = out;
		incoming_amount = in_amount;
		
		layers.add(new HiddenLayer(neurons_per_layer, incoming_amount));
		
		for(int i = 0 ; i<hiddenlayer_amount ; i++) {
		  layers.add(new HiddenLayer(neurons_per_layer, neurons_per_layer));
		}
		
		layers.add(new OutputLayer(n_outputs, neurons_per_layer));
	
		
	}

	public double[] calculate_Outputs(double[] inputs, int layer)
	{	
	  if(layer < 0) return inputs;
	  if(layer == 0){
	    return layers.get(layer).calculate_Outputs(inputs);
	  }
	  
	  return layers.get(layer).calculate_Outputs(calculate_Outputs(inputs, layer-1));
	  
	}
	
	public void update(double[] doutputs, double alpha, double[] inputs) {
	   
	  //Update output layer.
	  layers.get(layers.size()-1).calculate_Gradients(calculate_Outputs(inputs, layers.size()-1), calculate_Outputs(inputs, layers.size()-2), doutputs, alpha, null);
	  
	  //Update hidden layers.
	  for(int i = layers.size()-2 ; i >= 0 ; i--) {
	    layers.get(i).calculate_Gradients(calculate_Outputs(inputs, i), calculate_Outputs(inputs,i-1), doutputs, alpha, layers.get(i+1));
	  }
	}

	public int size() {
	  return layers.size();
	}

	public int neurons_In_Layer(int layer){
	  if(layer >= layers.size()) return 0;
	  return layers.get(layer).size();
	}


}



