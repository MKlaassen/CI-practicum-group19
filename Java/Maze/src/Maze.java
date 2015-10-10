import java.util.ArrayList;

/** Class that can construct a Maze from the given layout and transform it into a graph consiting of Nodes.
 * @author Rick Molenaar
 * @author Matthijs Klaassen
 * @author Daniël Brouwer
 * @version 9 October 2015
 */
public class Maze {

	private int cols;
	private int rows;
	private int[][] layout;
	private ArrayList<Node> nodes;


	public Maze(int[][] la, int col, int row) {
		cols = col;
		rows = row;
		layout = la;
		nodes = new ArrayList<Node>();
	}

	public ArrayList<Node> getNodes() {
		return nodes;
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

	/**Instantiates all the Nodes in the Maze
	 * 
	 */
	public void instantiateNodes()
	{
		for(int i = 0 ; i < rows ; i++) {
			for( int j = 0 ; j < cols ; j++) {
				if (layout[i][j]==1){
					Node newNode = new Node(new Coordinate(j,i));		//Yay for sign conventions
					nodes.add(newNode);
					//When node i,j is not on the bottom edge (i>0)
					if (i>0){
						//Check if there is a '1' above the current node 
						if (layout[i-1][j]==1){
							//for all Node from ArrayList nodes
							for (Node node : nodes){
								//get its coordinate
								Coordinate c = node.getCoordinate();
								//check if node is a neighbor of newNode and add each neighbor to both nodes
								if (c.getX()==j && c.getY()==i-1){
									node.addNeighbor(newNode, 1);
									newNode.addNeighbor(node, 1);
									break;
								}
							}
						}
					}
					//When node i,j is not on the left edge (j>0) 
					if (j>0){
						//Check if there is a '1' left from the current node
						if (layout[i][j-1]==1){
							//for all Node from ArrayList nodes
							for (Node node : nodes){
								//get its coordinate
								Coordinate c = node.getCoordinate();
								//check if node is a neighbor of newNode and add each neighbor to both nodes
								if (c.getX()==j-1 && c.getY()==i){
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


	/**Removes all 'in between' nodes and updates the neighbors which results in a graph representation of the maze consisting of crossings a begin and end node
	 * 
	 */
	public void cleanUpNodes(Coordinate start, Coordinate end){
		ArrayList<Integer> toDelete = new ArrayList<Integer>();
		//Loop through all the Node from ArrayList nodes
		for (int i = 0; i<nodes.size(); i++){
			Node node = nodes.get(i);
			//If node i is not a start-node, not an end-node and it has only 2 neighbors -> it is a 'in between' node an it should be deleted
			if (!(node.getCoordinate().equals(start) || node.getCoordinate().equals(end)
					|| node.getNeighbors().size()!=2)){
				//System.out.println("Should be deleted "+node.getCoordinate());

				//Add the Node to the delete list, and update the neighbors of the nodes to the side of the deleted node
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
			//remove all 'in between' nodes
			nodes.remove(i);
		}
	}

	
	/**String representing the maze using Nodes, Walls and Passages
	 * 
	 */
	public String GraphtoString() {
		//Output the amount of Nodes
		String output = "Nodes: "  + nodes.size();
		boolean foundnode = false;
		//Loop through the size of the matrix
		for(int i = 0 ; i < rows ; i++) {
			output = output + "\n";
			for( int j = 0 ; j < cols ; j++) {
				for (Node node : nodes){
					if (node.getCoordinate().getX()==j && node.getCoordinate().getY()==i)
					{
						//If on location i,j there is a node -> add it to the string
						output = output + "0";
						foundnode = true;
					}
				}
				//No Node is added, so a wall or passage has to be added
				if(foundnode == false)
				{
					if(layout[i][j]==1)
						//Add a passage
						output = output + " ";
					else	
						//Add a wall
						output = output + "#";
				}
				else
				{
					//Node is added so no wall or passage is added to the string
					foundnode = false;
				}
			}
		}
		return output;
	}
}
