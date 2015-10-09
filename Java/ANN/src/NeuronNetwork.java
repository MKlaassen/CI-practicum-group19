/**Class that is construct a neuralnetwork consisting of several layers
 * @author Matthijs Klaassen, Rick Molenaar, Daniël Brouwer
 * @version: 18-09-2015
 */

import java.util.ArrayList;

public class NeuronNetwork {
	
	private int hiddenlayer_amount;
	private int neurons_per_layer;
	private int n_outputs;
	private int incoming_amount;
	
	private ArrayList<Layer> layers;

	public NeuronNetwork(int hl_amount, int np_layer, int out, int in_amount)
	{
		//Parameters of the network
		layers = new ArrayList<>(); 
		hiddenlayer_amount = hl_amount;
		neurons_per_layer = np_layer;
		n_outputs = out;
		incoming_amount = in_amount;
		
		//Add an incoming layer
		layers.add(new HiddenLayer(neurons_per_layer, incoming_amount));
		
		//Add i hidden layers
		for(int i = 0 ; i<hiddenlayer_amount ; i++) {
		  layers.add(new HiddenLayer(neurons_per_layer, neurons_per_layer));
		}
		
		//Add an output layer
		layers.add(new OutputLayer(n_outputs, neurons_per_layer));
	
		
	}

	public double[] calculate_Outputs(double[] inputs, int layer)
	{	
	//calculates the output at layer (int layer) using recursion
	  if(layer < 0) return inputs;
	  if(layer == 0){
	    return layers.get(layer).calculate_Outputs(inputs);
	  }
	  
	  return layers.get(layer).calculate_Outputs(calculate_Outputs(inputs, layer-1));
	  
	}
	
	public void update(double[] doutputs, double alpha, double[] inputs) {
	//Method for updating the weights
	   
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
		//returns the amount of neurons in layer (int layer)
	  if(layer >= layers.size()) return 0;
	  return layers.get(layer).size();
	}


}



