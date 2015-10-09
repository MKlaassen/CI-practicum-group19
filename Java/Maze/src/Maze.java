import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author mklaassen
 *
 */
public class Maze {

	private int cols;
	private int rows;
	private int[][] layout;


	public Maze(int[][] la, int col, int row) {
		cols = col;
		rows = row;
		layout = la;
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public int[][] getLayout() {
		return layout;
	}

	@Override
	public String toString() {
		String output = "Columns: "  + cols + " Rows: " + rows ;
		for(int i = 0 ; i < rows ; i++) {
			output = output + "\n";
			for( int j = 0 ; j < cols ; j++) {

				output = output + layout[i][j];
			}
		}

		return output;
	}

	public void instantiateNodes(Coordinate start, Coordinate end)
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i = 0 ; i < rows ; i++) {
			for( int j = 0 ; j < cols ; j++) {
				if (layout[i][j]==1){
					nodes.add(new Node(new Coordinate(i,j)));
					
				}
			}
		}
	}
	
	

}
