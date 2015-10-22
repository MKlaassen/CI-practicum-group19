package TSP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Ants.Coordinate;
import Ants.Reader;

public class Main {
	
	private static ArrayList<Coordinate> coordinates;

	public static void main(String[] args) throws FileNotFoundException {
		//Read all product locations for TSP
		try
		{
			File inputFileProducts = new File("products\\tsp products.txt");
			coordinates = Reader.readProducts(inputFileProducts);
		}catch(FileNotFoundException e)
		{
			System.out.println(":(");
		}

	}
	
	
	
	
}
