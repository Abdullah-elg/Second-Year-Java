// Class BSTNode to store the record, the left and right children, and the parent of a node
public class BSTNode {
    private Record record;
    private BSTNode left;
    private BSTNode right;
    private BSTNode parent;

    /**
     * Constructor for the BSTNode class.
     * @param item the record to store in the node
     */
    public BSTNode(Record item) {
        this.record = item;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    /**
     * Returns the record stored in the node.
     * @return record
     */
    public Record getRecord() {
        return this.record;
    }

    /**
     * Sets the record stored in the node.
     * @param d the record to store in the node
     */
    public void setRecord(Record d) {
        this.record = d;
    }

    /**
     * Returns the left child of the node.
     * @return left
     */
    public BSTNode getLeftChild() {
        return this.left;
    }

    /**
     * Returns the right child of the node.
     * @return right
     */
    public BSTNode getRightChild() {
        return this.right;
    }

    /**
     * Returns the parent of the node.
     * @return parent
     */
    public BSTNode getParent() {
        return this.parent;
    }

    /**
     * Sets the left child of the node.
     * @param u the node to set as the left child
     */
    public void setLeftChild(BSTNode u) {
        this.left = u;
    }

    /**
     * Sets the right child of the node.
     * @param u the node to set as the right child
     */
    public void setRightChild(BSTNode u) {
        this.right = u;
    }

    /**
     * Sets the parent of the node.
     * @param u the node to set as the parent
     */
    public void setParent(BSTNode u) {
        this.parent = u;
    }

    /**
     * Returns whether the node is a leaf.
     * @return true if the node is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }
}
