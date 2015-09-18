/**
 * 
 */

/**
 * @author Matthijs, Rick, Daniël
 * date: 11-09-2015
 */
public class Neuron {
    
   private int amount_inputs;
   private double[] weights;
  
   public Neuron(int a_inputs) {
     this.amount_inputs = a_inputs;
     //the amount of weights is equal to the amount of inputs.
     weights = new double[amount_inputs+1];
     for(int i = 0 ; i<(weights.length-2); i++) {
     //give all weights a random value between -0.5 and 0.5 except for the threshold 
     weights[i] = Math.random()-0.5;
     }
     //the last weight corrosponds to the threshold.
     weights[weights.length-1]=0.7;
     
   }
   
   public double calculate_Output(double[] inputs) {
     double result = 0;
     //summation of weights*inputs
     for(int i=0; i<(inputs.length-1); i++){
         result += inputs[i]*weights[i];
     }
     //last weight represents theta, the threshold;
     result += -1*weights[weights.length-1];
     
     //sigmoid function:
     result = (1/(1 + Math.pow(Math.E, (-1*result))));
     return result;
   }
  

   
   
   public int get_Inputs(){
     return amount_inputs;
   }
   
   public void set_Weights(double[] w){
     weights = w;
   }
   
   public void update_Weight(int num, double correction)
   {
	   weights[num] += correction;
   }
   
   public double get_Weight(int num) 
   {
	     return weights[num];
   }
   
   public double[] get_Weights() 
   {
     return weights;
   }

   
   

}
