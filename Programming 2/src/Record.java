// Class Record to store the key and data of a record
public class Record {
    private Key key;
    private String data;

    /**
     * Constructor for the Record class.
     * @param k the key of the record
     * @param theData the data of the record
     */
    public Record(Key k, String theData) {
        this.key = k;
        this.data = theData;
    }

    /**
     * Returns the key of the record.
     * @return key
     */
    public Key getKey() {
        return this.key;
    }

    /**
     * Returns the data of the record.
     * @return data
     */
    public String getDataItem() {
        return this.data;
    }
}
