
public class neuron_test {
	private static int aantal;
	private int amount_inputs;
	private double theta;
	private double[] weights;
	private double[] outputs;
	
	
	public neuron_test(int amount_inputs, double theta)
	{
		this.amount_inputs = amount_inputs;
		this.theta = theta;
		aantal++;
		weights = new double[amount_inputs+1];
	}
	
	public double[] update(double[] inputs)
	{
		
		
		return null;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
