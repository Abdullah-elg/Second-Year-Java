// Class Key to store the label and type of a key
public class Key {
    private String label;
    private int type;

    /**
     * Constructor for the Key class.
     * @param theLabel the label of the key
     * @param theType the type of the key
     */
    public Key(String theLabel, int theType) {
        this.label = theLabel.toLowerCase();
        this.type = theType;
    }
    
    /**
     * Returns the label of the key.
     * @return label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the type of the key.
     * @return type
     */
    public int getType() {
        return this.type;
    }

    /**
     * Compares this key to another key.
     * @param k the key to compare to
     * @return 0 if the keys are equal, -1 if this key is less than the other key, 1 if this key is greater than the other key
     */
    public int compareTo(Key k) {
        if (this.label.equals(k.label) && this.type == k.type) {
            return 0;
        } else if (this.label.compareTo(k.label) < 0 || (this.label.equals(k.label) && this.type < k.type)) {
            return -1;
        } else {
            return 1;
        }
    }
}
