import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Matthijs, Rick, Dani�l
 * date: 11-09-2015
 */
public class Layer {
    private ArrayList<Neuron> neurons;
    
    public Layer(int number_neurons, int a_inputs){
      neurons = new ArrayList<>();
      
      for(int i = 0 ; i< number_neurons; i++) {
        neurons.add(new Neuron(a_inputs));
      }
    }
    
    public Neuron get(int i){
      return neurons.get(i);
    }
    
    public int size() {
      return neurons.size();
    }
    
    
    
}
