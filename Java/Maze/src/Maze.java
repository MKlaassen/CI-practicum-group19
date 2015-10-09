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
	
	public void instantiateNodes()
	{
		int N;
		int E;
		int S;
		int W;
		for(int i = 0 ; i < rows ; i++) {
			for( int j = 0 ; j < cols ; j++) {
				N=E=S=W=0;
				if(layout[i][j]==1)
				{
					if(i<rows && layout[i+1][j]==1)
						N=1;
					if(i>0 && layout[i-1][j]==1)
						S=1;
					if(layout[i][j+1]==1)
						E=1;
					if(layout[i-1][j-1]==1)
						W=1;
				}
			}
	}
	}
	

}
