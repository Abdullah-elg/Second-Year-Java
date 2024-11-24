import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// Class Maze: represents a maze
public class Maze {
	private Graph graph;
	private int start;
	private int end;
	private int coins;
	private ArrayList<GraphNode> path;

	/**
	 * Constructor for Maze
	 * @param inputFile the input file
	 * @throws MazeException if there is an error reading the input file or creating the graph
	 */
	public Maze(String inputFile) throws MazeException {
		try {
			File file = new File(inputFile); // Create a new file with the input file
			BufferedReader inputReader = new BufferedReader(new FileReader(file)); // Create a new BufferedReader with the file
			readInput(inputReader); // Read the input file
			inputReader.close(); // Close the BufferedReader
		} catch (IOException e) {
			throw new MazeException("Error reading input file");
		} catch (GraphException e) {
			throw new MazeException("Error creating graph");
		}
	}

	/**
	 * Get the graph
	 * @return the graph
	 * @throws MazeException if the graph is not created
	 */
	public Graph getGraph() throws MazeException {
		if (this.graph == null) { // If the graph is not created throw a MazeException
			throw new MazeException("Graph not created");
		}
		return this.graph; // Return the graph
	}

	/**
	 * Solve the maze
	 * @return an iterator of the path, null if there is no path
	 */
	public Iterator<GraphNode> solve() {
		try {
			this.path = new ArrayList<GraphNode>(); // Create a new ArrayList for the path
			Iterator<GraphNode> dfs = DFS(this.coins, this.graph.getNode(this.start)); // Perform a depth-first search to find the path
			return dfs; // Return the iterator of the path
		} catch (GraphException e) { // Catch a GraphException and return null
			return null; 
		}
	}

	/**
	 * Perform a depth-first search to find the path
	 * @param k the number of coins
	 * @param go the current node
	 * @return an iterator of the path, null if there is no path
	 * @throws GraphException if there is an error with the graph
	 */
	private Iterator<GraphNode> DFS(int k, GraphNode go) throws GraphException {
		try {
			go.mark(true); // Mark the current node
			this.path.add(go); // Add the current node to the path
			if (go.getName() == this.end) { // If the current node is the end node return the iterator of the path
				return this.path.iterator();
			}
			Iterator<GraphEdge> edges = this.graph.incidentEdges(go); // Get the edges incident to the current node
			while (edges.hasNext()) { // While there are more edges incident to the current node
				GraphEdge edge = edges.next(); // Get the next edge
				GraphNode next = edge.secondEndpoint(); // Get the next node
				if (next.getName() == edge.firstEndpoint().getName()) { // If the next node is the first endpoint of the edge, remove the edge from the path and return null
					this.path.remove(go);
					return null;
				}
				if (next.getName() == go.getName()) { // If the next node is the current node, set the next node to the first endpoint of the edge
					next = edge.firstEndpoint();
				}
				if (!next.isMarked()) { // If the next node is not marked
					if (edge.getLabel().equals("door")) { // If the edge is a door
						int cost = edge.getType(); // Get the cost of the edge
						if (k >= cost) { // If the number of coins is greater than or equal to the cost
							Iterator<GraphNode> dfs = DFS(k - cost, next); // Perform a depth-first search on the endpoint of the edge with the number of coins minus the cost
							if (dfs != null) { // If there is a path return the iterator of the path
								return dfs;
							}
						}
					} else { // If the edge is not a door
						Iterator<GraphNode> dfs = DFS(k, next); // Perform a depth-first search on the endpoint of the edge with the number of coins
						if (dfs != null) { // If there is a path return the iterator of the path
							return dfs;
						}
					}
				}
			}
			this.path.remove(go); // Remove the current node from the path
			return null; // Return null
		} catch (GraphException e) { // Catch a GraphException and remove the current node from the path and return null
			this.path.remove(go);
			return null;
		}
	}

	/**
	 * Read the input file
	 * @param inputReader the BufferedReader for the input file
	 * @throws IOException if there is an error reading the input file
	 * @throws GraphException if there is an error creating the graph
	 */
	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
		try {
			int scale = Integer.parseInt(inputReader.readLine()); // Read the scale
			int width = Integer.parseInt(inputReader.readLine()); // Read the width
			int height = Integer.parseInt(inputReader.readLine()); // Read the height
			this.coins = Integer.parseInt(inputReader.readLine()); // Read the number of coins
			this.graph = new Graph(width * height); // Create a new graph with the width times the height
			for (int h = 0; h < height + height - 1; h++) { // For each number up to the height plus the height minus 1
				String line = inputReader.readLine(); // Read a line
				for (int w = 0; w < width + width - 1; w++) { // For each number up to the width plus the width minus 1
					char c = line.charAt(w); // Get the character at the position
					if (h % 2 == 0 && w % 2 == 0) { // If the row is even and the column is even
						if (c == 's') { // If the character is 's', set the start node as the current node
							this.start = h/2 * width + w/2; 
						} else if (c == 'x') { // If the character is 'x', set the end node as the current node
							this.end = h/2 * width + w/2;
						}
					} else if (h % 2 == 1 && w % 2 == 0) { // If the row is odd and the column is even
						if (h/2 < height) { // If the row divided by 2 is less than the height
							int node1 = (h - 1)/2 * width + w/2; // Get the first node
							int node2 = (h + 1)/2 * width + w/2; // Get the second node
							if (c == 'c') { // If the character is 'c', insert a corridor edge between the nodes
								insertEdge(node1, node2, 0, "corridor");
							} else if (c == 'w') { // If the character is 'w', continue
								continue;
							} else { // If the character is not 'c' or 'w', insert a door edge between the nodes, with the type (cost) of the edge as the integer value of the character
								insertEdge(node1, node2, ((int) c) - 48, "door");
							}
						} 
					} else if (h % 2 == 0 && w % 2 == 1) { // If the row is even and the column is odd
						if (w/2 < width) { // If the column divided by 2 is less than the width
							int node1 = h/2 * width + (w - 1)/2; // Get the first node
							int node2 = h/2 * width + (w + 1)/2; // Get the second node
							if (c == 'c') { // If the character is 'c', insert a corridor edge between the nodes
								insertEdge(node1, node2, 0, "corridor"); 
							} else if (c == 'w') { // If the character is 'w', continue
								continue;
							} else { // If the character is not 'c' or 'w', insert a door edge between the nodes, with the type (cost) of the edge as the integer value of the character
								insertEdge(node1, node2, ((int) c) - 48, "door");
							}
						}
					}
				}
			}
		} catch (IOException e) {
			throw new IOException("Error reading input file");
		} catch (GraphException e) {
			throw new GraphException("Error creating graph");
		}
	}

	/**
	 * Insert an edge into the graph
	 * @param node1 the first endpoint of the edge
	 * @param node2 the second endpoint of the edge
	 * @param linkType the type of the edge
	 * @param label the label of the edge
	 * @throws GraphException if there is an error inserting the edge
	 */
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException {
		try {
			GraphNode u = this.graph.getNode(node1); // Get the first endpoint of the edge
			GraphNode v = this.graph.getNode(node2); // Get the second endpoint of the edge
			this.graph.insertEdge(u, v, linkType, label); // Insert the edge into the graph
		} catch (GraphException e) {
			throw new GraphException("Error inserting edge");
		}
	}
}
