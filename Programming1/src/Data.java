/**
 * Data class to store the score and the configuration of the game
 */
public class Data {
    private int score;
    private String config;

    /**
     * Constructor for the Data class
     * @param config the configuration of the game
     * @param score the score of the game
     */
    public Data(String config, int score) {
        this.config = config;
        this.score = score;
    }

    /**
     * Get the score of the game
     * @return the score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the configuration of the game
     * @return the configuration of the game
     */
    public String getConfiguration() {
        return config;
    }
}
