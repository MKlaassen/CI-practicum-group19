package Java.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

/**
 * 
 */

/**
 * @author Matthijs en Daniel en Rick
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		readFiles();
	}
	public static void readFiles(){
		Scanner sc = null;
		try {
			sc = new Scanner(new File("src\\features.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double[][] features = new double[7854][10];
		
//		features[0] = new double[] {1,2,3,4,5,6,7,8,9,0};
		
		for(int i=0; i<7854; i++){
			String line = sc.next();
			String[] values = line.split(",");
			for (int j=0; j<10; j++){
				features[i][j] = Double.valueOf(values[j]);
			}
			
		}
		
		for (double[] ar : features){
			System.out.println(Arrays.toString(ar));
		}
	}

}
