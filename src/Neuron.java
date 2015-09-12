/**
 * 
 */

/**
 * @author Matthijs, Rick, Daniël
 * date: 11-09-2015
 */
public class Neuron {
    
   private static int numnodes;
   private int amount_inputs;
   private double[] weights;
  
   public Neuron(int a_inputs) {
     this.amount_inputs = a_inputs;
     weights = new double[amount_inputs+1];
     for(int i = 0 ; i<(weights.length-1); i++) {
     //give weights a random value between -0.5 and 0.5  
     weights[i] = Math.random()-0.5;
     }
     
   }
   
   public double calculate_Output(double[] inputs) {
     double result = 0;
     for(int i=0; i<(inputs.length-1); i++){
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
