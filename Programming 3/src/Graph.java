import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Class Graph: represents a graph
public class Graph implements GraphADT {
	private Map<GraphNode, ArrayList<GraphEdge>> nodes = new HashMap<GraphNode, ArrayList<GraphEdge>>();
	
	/**
	 * Constructor for Graph
	 * @param n the number of nodes in the graph
	 */
	public Graph(int n) {
		for (int i = 0; i < n; i++) { // For each number up to n
			GraphNode node = new GraphNode(i); // Create a new node with the name i
			nodes.put(node, new ArrayList<GraphEdge>()); // Add the node to the graph
		}
	}
	
	/**
	 * Insert an edge into the graph
	 * @param nodeu the first endpoint of the edge
	 * @param nodev the second endpoint of the edge
	 * @param type the type of the edge
	 * @param label the label of the edge
	 * @throws GraphException if the nodes do not exist or the edge already exists
	 */
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		if(!nodes.containsKey(nodeu) || !nodes.containsKey(nodev)) { // If the nodes do not exist in the graph throw a GraphException
			throw new GraphException("Node does not exist");
		}
		ArrayList<GraphEdge> u = nodes.get(nodeu); // Get the edges of the first endpoint
		ArrayList<GraphEdge> v = nodes.get(nodev); // Get the edges of the second endpoint
		Iterator<GraphEdge> iterator = u.iterator(); // Create an iterator for the edges of the first endpoint
		while(iterator.hasNext()) { // While there are more edges in the first endpoint
			GraphEdge edge = iterator.next(); // Get the next edge
			if (edge.secondEndpoint().getName() == nodev.getName() || edge.firstEndpoint().getName() == nodev.getName()) { // If the edge already exists throw a GraphException
				throw new GraphException("Edge already exists");
			}
		}
		GraphEdge edge = new GraphEdge(nodeu, nodev, type, label); // Create a new edge
		u.add(edge); // Add the edge to the edges of the first endpoint
		v.add(edge); // Add the edge to the edges of the second endpoint
	}

	/**
	 * Get the node with the given name
	 * @param u the name of the node
	 * @return the node with the given name
	 * @throws GraphException if the node does not exist
	 */
	@Override
	public GraphNode getNode(int u) throws GraphException {
		for (GraphNode node : nodes.keySet()) { // For each node in the graph
			if (node.getName() == u) { // If the name of the node is equal to u, return the node
				return node;
			}
		} 
		throw new GraphException("Node does not exist"); // If the node does not exist throw a GraphException
	}

	/**
	 * Get the edges incident to the node
	 * @param u the node to get the edges of
	 * @return an iterator of the edges incident to the node
	 * @throws GraphException if the node does not exist
	 */
	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		if (!nodes.containsKey(u)) { // If the node does not exist throw a GraphException
			throw new GraphException("Node does not exist");
		}
		ArrayList<GraphEdge> edges = nodes.get(u); // Get the edges of the node
		return edges.iterator(); // Return an iterator of the edges
	}

	/**
	 * Get the edge between the two nodes
	 * @param u the first endpoint of the edge
	 * @param v the second endpoint of the edge
	 * @return the edge between the two nodes
	 * @throws GraphException if the nodes do not exist or the edge does not exist
	 */
	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		if (!nodes.containsKey(u) || !nodes.containsKey(v)) { // If the nodes do not exist throw a GraphException
			throw new GraphException("Node does not exist");
		}
		ArrayList<GraphEdge> edges = nodes.get(u); // Get the edges of the first endpoint
		for (GraphEdge edge : edges) { // For each edge of the first endpoint
			if (edge.secondEndpoint().getName() == v.getName() || edge.firstEndpoint().getName() == v.getName()) { // If the edge is between the two nodes return the edge
				return edge;
			}
		}
		throw new GraphException("Edge does not exist"); // If the edge does not exist throw a GraphException
	}

	/**
	 * Check if the two nodes are adjacent
	 * @param u the first node
	 * @param v the second node
	 * @return true if the nodes are adjacent, false otherwise
	 * @throws GraphException if the nodes do not exist
	 */
	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		try {
			getEdge(u, v); // If an edge exists between the two nodes return true
			return true;
		} catch (GraphException e) { // If an edge does not exist between the two nodes return false
			return false;
		}
	}
}
