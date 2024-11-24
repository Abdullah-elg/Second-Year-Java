// Class BinarySearchTree to store the root of the tree and perform operations on the tree
public class BinarySearchTree {
    private BSTNode root;

    /**
     * Constructor for the BinarySearchTree class.
     */
    public BinarySearchTree() {
        this.root = new BSTNode(null);
    }

    /**
     * Returns the root of the tree.
     * @return root
     */
    public BSTNode getRoot() {
        return this.root;
    }

    /**
     * Returns the node with the given key.
     * @param r the root of the tree
     * @param k the key to search for
     * @return the node with the given key
     */
    public BSTNode get(BSTNode r, Key k) {
        BSTNode temp = getNode(r, k); // get the node with the key
        if (temp.isLeaf()) { // if the node is a leaf, the key does not exist
            return null;
        }
        return temp;
    }

    /**
     * Inserts a record into the tree.
     * @param r the root of the tree
     * @param d the record to insert
     * @throws DictionaryException if the key already exists in the dictionary
     */
    public void insert(BSTNode r, Record d) throws DictionaryException {
        BSTNode temp = getNode(r, d.getKey()); // get the node with the key
        if (!temp.isLeaf()) { // if the node is not a leaf, the key already exists
            throw new DictionaryException("Key already exists in dictionary");
        }
        temp.setRecord(d); // set the record in the node
        BSTNode left = new BSTNode(null); // create a new leaf node for the left child
        BSTNode right = new BSTNode(null); // create a new leaf node for the right child
        left.setParent(temp); // set the parent of the left child
        right.setParent(temp); // set the parent of the right child
        temp.setLeftChild(left); // set the left child of the node
        temp.setRightChild(right); // set the right child of the node
    }

    /**
     * Removes a record from the tree.
     * @param r the root of the tree
     * @param k the key of the record to remove
     * @throws DictionaryException if the key does not exist in the dictionary
     */
    public void remove(BSTNode r, Key k) throws DictionaryException {
        BSTNode temp = getNode(r, k); // get the node with the key
        if (temp.isLeaf()) { // if the node is a leaf, the key does not exist
            throw new DictionaryException("Key does not exist in dictionary");
        }
        BSTNode parent = temp.getParent(); // get the parent of the node
        if (temp.getLeftChild().isLeaf() && temp.getRightChild().isLeaf()) { // if the node has no children set the node to a leaf
            temp.setRecord(null); 
            temp.setLeftChild(null);
            temp.setRightChild(null);
        } else if (temp.getLeftChild().isLeaf()) { // if the node has only a right child set the node to the right child
            if (parent != null) { // if the node has a parent
                if (parent.getLeftChild() == temp) { // if the node is the left child of the parent
                    parent.setLeftChild(temp.getRightChild()); // set the right child of the node as the left child of the parent
                } else { // if the node is the right child of the parent
                    parent.setRightChild(temp.getRightChild()); // set the right child of the node as the right child of the parent
                }
                temp.getRightChild().setParent(parent); // set the parent of the right child
            } else { // if the node is the root
                this.root = temp.getRightChild(); // set the right child of the node as the root
                temp.getRightChild().setParent(null); // set the parent of the right child to null
            }
        } else if (temp.getRightChild().isLeaf()) { // if the node has only a left child set the node to the left child
            if(parent != null) { // if the node has a parent
                if (parent.getLeftChild() == temp) { // if the node is the left child of the parent
                    parent.setLeftChild(temp.getLeftChild()); // set the left child of the node as the left child of the parent
                } else {  // if the node is the right child of the parent
                    parent.setRightChild(temp.getLeftChild()); // set the left child of the node as the right child of the parent
                }
                temp.getLeftChild().setParent(parent); // set the parent of the left child
            } else { // if the node is the root
                this.root = temp.getLeftChild(); // set the left child of the node as the root
                temp.getLeftChild().setParent(null); // set the parent of the left child to null
            }
        } else { // if the node has two children
            BSTNode small = smallest(temp.getRightChild()); // get the smallest node in the right subtree
            temp.setRecord(small.getRecord()); // set the record of the smallest node of the right subtree to the node
            remove(small, small.getRecord().getKey()); // remove the smallest node of the right subtree
        }
    }

    /**
     * Returns the successor of the node with the given key.
     * @param r the root of the tree
     * @param k the key of the node
     * @return the successor of the node with the given key
     */
    public BSTNode successor(BSTNode r, Key k) {
        BSTNode temp = get(r, k); // get the node with the key
        if(temp == null) { // if the node does not exist return null
            return null;
        }
        if(temp.getRightChild().isLeaf()) { // if the right child of the node is a leaf
            BSTNode parent = temp.getParent(); // get the parent of the node
            while(parent != null && parent.getRecord().getKey().compareTo(k) < 0) { // while the parent is not null and the key of the parent is less than the key
                parent = parent.getParent(); // set the parent to the parent of the parent
            }
            return parent;
        }
        return smallest(temp.getRightChild()); // return the smallest node in the right subtree
    }

    /**
     * Returns the predecessor of the node with the given key.
     * @param r the root of the tree
     * @param k the key of the node
     * @return the predecessor of the node with the given key
     */
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode temp = get(r, k); // get the node with the key
        if(temp == null) { // if the node does not exist return null
            return null; 
        }
        if(temp.getLeftChild().isLeaf()) { // if the left child of the node is a leaf
            BSTNode parent = temp.getParent(); // get the parent of the node
            while(parent != null && parent.getRecord().getKey().compareTo(k) > 0) { // while the parent is not null and the key of the parent is greater than the key
                parent = parent.getParent(); // set the parent to the parent of the parent
            }
            return parent;
        }
        return largest(temp.getLeftChild()); // return the largest node in the left subtree
    }

    /**
     * Returns the smallest node in the tree.
     * @param r the root of the tree
     * @return the smallest node in the tree
     */
    public BSTNode smallest(BSTNode r) {
        while(!r.getLeftChild().isLeaf() && !r.isLeaf()) { // while the left child of the node is not a leaf and the node is not a leaf set the node to the left child
            r = r.getLeftChild();
        }
        return r;
    }

    /**
     * Returns the largest node in the tree.
     * @param r the root of the tree
     * @return the largest node in the tree
     */
    public BSTNode largest(BSTNode r) { 
        while(!r.getRightChild().isLeaf() && !r.isLeaf()) { // while the right child of the node is not a leaf and the node is not a leaf set the node to the right child
            r = r.getRightChild();
        }
        return r;
    }

    /**
     * Returns the node with the given key.
     * @param r the root of the tree
     * @param k the key to search for
     * @return the node with the given key
     */
    private BSTNode getNode(BSTNode r, Key k) {
        if (r.isLeaf()) { // if the node is a leaf return the node
            return r;
        }
        int cmp = k.compareTo(r.getRecord().getKey()); // compare the key to the key of the node
        if (cmp == 0) { // if the keys are equal return the node
            return r;
        } else if (cmp < 0) { // if the key is less than the key of the node search the left subtree
            return getNode(r.getLeftChild(), k);
        } else { // if the key is greater than the key of the node search the right subtree
            return getNode(r.getRightChild(), k);
        }
    }
}
