import java.util.ArrayList;
//import java.util.Arrays;

/**
 * @author Rick Molenaar
 * @author mklaassen
 *
 */
public class Maze {

	private int cols;
	private int rows;
	private int[][] layout;
	private ArrayList<Node> nodes;


	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public Maze(int[][] la, int col, int row) {
		cols = col;
		rows = row;
		layout = la;
		nodes = new ArrayList<Node>();
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
		for(int i = 0 ; i < rows ; i++) {
			for( int j = 0 ; j < cols ; j++) {
				if (layout[i][j]==1){
					Node newNode = new Node(new Coordinate(j,i));		//Yay for sign conventions
					nodes.add(newNode);
					if (i>0){
						if (layout[i-1][j]==1){
							for (Node node : nodes){
								Coordinate c = node.getCoordinate();
								if (c.getX()==j && c.getY()==i-1){
									node.addNeighbor(newNode, 1);
									newNode.addNeighbor(node, 1);
									break;
								}
							}
						}
					}
					if (j>0){
						if (layout[i][j-1]==1){
							for (Node node : nodes){
								Coordinate c = node.getCoordinate();
								if (c.getX()==j-1 && c.getY()==i){			//Fucking matrices
									node.addNeighbor(newNode, 1);
									newNode.addNeighbor(node, 1);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void cleanUpNodes(Coordinate start, Coordinate end){
		ArrayList<Integer> toDelete = new ArrayList<Integer>();
		for (int i = 0; i<nodes.size(); i++){
			Node node = nodes.get(i);
			if (!(node.getCoordinate().equals(start) || node.getCoordinate().equals(end)
					|| node.getNeighbors().size()!=2)){
//				System.out.println("Should be deleted "+node.getCoordinate());
				int totDist = node.getDists().get(0)+node.getDists().get(1);
				Node nb1 = node.getNeighbors().get(0);
				Node nb2 = node.getNeighbors().get(1);
				nb1.deleteNeighbor(node);
				nb2.deleteNeighbor(node);
				nb1.addNeighbor(nb2, totDist);
				nb2.addNeighbor(nb1, totDist);
				toDelete.add(0, i);						//Reverse order
			}
		}
		for (int i : toDelete){
			nodes.remove(i);
		}
	}

}
