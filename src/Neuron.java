/**
 * 
 */

/**
 * @author Matthijs
 *
 */
public class Neuron {
    
   private static int numnodes;
   private int amount_inputs;
   private double[] weights;
  
   public Neuron(int a_inputs) {
     this.amount_inputs = a_inputs;
     weights = new double[amount_inputs+1];
     for(int i = 0 ; i<weights.length; i++) {
       weights[i] = Math.random();
     }
     
   }
   
   private double calculate_Outputs(double[] inputs) {
     double result = 0;
     for(int i=0; i<inputs.length; i++){
         result += inputs[i]*weights[i];
     }
     result += -1*weights[weights.length-1];
     
     result = (1/(1 + Math.pow(Math.E, (-1*result))));
     return result;
   }
  

   
   
   public int get_Inputs(){
     return amount_inputs;
   }
   
   public void set_Weights(double[] w){
     weights = w;
   }
   
   public double[] get_Weights() {
     return weights;
   }
   
   

}
