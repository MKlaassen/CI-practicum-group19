import java.util.ArrayList;

import com.sun.deploy.uitoolkit.impl.fx.ui.UITextArea;

/**
 * 
 */

/**
 * @author Matthijs
 *
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
  
  
}
