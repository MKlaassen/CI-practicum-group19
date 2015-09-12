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
    
    Layer incominglayer = new Layer(incoming_amount, incoming_amount);
    layers.add(incominglayer);
  
    for(int i = 1; i <= hiddenlayers_amount; i++){
      layers.add(new Layer(neuronsperlayer, layers.get(i-1).size() ) );
    }
    
    Layer outgoinglayer = new Layer(outgoing_amount, layers.get(layers.size()-1).size());
    layers.add(outgoinglayer);
  }
  
  public double[] calculate_Outputs(double[] inputs)
  {	 
	  double[] outputvector = new double[inputs.length];
	 
	  for(int j=0; j<=(hiddenlayers_amount)+1;j++)
	  {
		  
	 //calculate output of layer j
		 for(int i = 0; i <= (layers.get(j).size()-1); i++)
		 {
			 outputvector[i] = layers.get(j).get(i).calculate_Output(inputs);
		 }
	//fill non used elements in the array with zeros	 
		 for(int i = (layers.get(j).size()); i <= outputvector.length-1; i++)
		 {
			 outputvector[i] = 0;
		 }
		 
	//the outputs of the previous layer are the inputs for all the neurons on the next layer
	 inputs=null;
	 inputs = arrayresize(outputvector,(layers.get(j).size())); 
	  
	  }
	return outputvector;

  }
  
  private double[] arrayresize(double[] array, int index)
  {
	  double[] output = new double[index];
	  for(int i=0;i<=index-1;i++)
	  {
		  output[i]=array[i];
	  }
	  return output;
  }
  
 }
  
  
  
