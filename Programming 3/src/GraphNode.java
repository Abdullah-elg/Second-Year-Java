// Class GraphNode: represents a node in a graph
public class GraphNode {
	private boolean marked;
	private int name;
	
	/**
	 * Constructor for GraphNode
	 * @param name the name of the node
	 */
	public GraphNode(int name) { 
		this.name = name;
		this.marked = false;
	}

	/**
	 * Mark the node
	 * @param mark boolean value to mark the node
	 */
	public void mark(boolean mark) {
		this.marked = mark;
	}
	
	/**
	 * Check if the node is marked
	 * @return true if the node is marked, false otherwise
	 */
	public boolean isMarked() {
		return this.marked;
	}
	
	/**
	 * Get the name of the node
	 * @return the name of the node
	 */
	public int getName() {
		return this.name;
	}
}
