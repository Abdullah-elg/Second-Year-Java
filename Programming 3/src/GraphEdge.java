// Class GraphEdge: represents an edge in a graph
public class GraphEdge {
	private GraphNode origin;
	private GraphNode destination;
	private int type;
	private String label;
	
	/**
	 * Constructor for GraphEdge
	 * @param u the origin node
	 * @param v the destination node
	 * @param type the type of the edge
	 * @param label the label of the edge
	 */
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.origin = u;
		this.destination = v;
		this.type = type;
		this.label = label;
	}
	
	/**
	 * Get the first endpoint of the edge
	 * @return the first endpoint of the edge
	 */
	public GraphNode firstEndpoint() {
		return this.origin;
	}
	
	/**
	 * Get the second endpoint of the edge
	 * @return the second endpoint of the edge
	 */
	public GraphNode secondEndpoint() {
		return this.destination;
	}
	
	/**
	 * Get the type of the edge
	 * @return the type of the edge
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * Set the type of the edge
	 * @param type the type to set the edge to
	 */
	public void setype(int type) {
		this.type = type;
	}
	
	/**
	 * Get the label of the edge
	 * @return the label of the edge
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Set the label of the edge
	 * @param label the label to set the edge to
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
