/**
 * Configurations class is used to store the configurations of the game and the board.
 */
public class Configurations {
    private int board_size;
    private int lengthToWin;
    private int max_levels;
    private char[][] board;

    /**
     * Constructor for the Configurations class
     * @param board_size the size of the board
     * @param lengthToWin the length of the sequence to win
     * @param max_levels the maximum levels of the game
     */
    public Configurations(int board_size, int lengthToWin, int max_levels) {
        this.board_size = board_size;
        this.lengthToWin = lengthToWin;
        this.max_levels = max_levels;

        board = new char[board_size][board_size]; // set the board to be a 2D array of size board_size
        for (int i = 0; i < board_size; i++) { // initialize the board to be empty
            for (int j = 0; j < board_size; j++) { board[i][j] = ' '; }
        }
    }

    /**
     * Create a new hash table
     * @return a new hash table with size 6779
     */
    public HashDictionary createDictionary() {
        return new HashDictionary(6779);
    }

    /**
     * Convert the board to a string
     * @return the string representation of the board
     */
    private String configString() {
        String config = "";
        for (int i = 0; i < board_size; i++) { // iterate through the board and add the elements to the string
            for (int j = 0; j < board_size; j++) { config += board[i][j]; }
        }
        return config;
    }

    /**
     * Get the score of the configuration
     * @param hashTable the hash table to get the score from
     * @return the score of the configuration
     */
    public int repeatedConfiguration(HashDictionary hashTable) {
        return hashTable.get(configString()); // convert the board to a string and get the score from the hash table
    }

    /**
     * Add a new configuration to the hash table
     * @param hashTable the hash table to add the configuration to
     * @param score the score of the configuration
     */
    public void addConfiguration(HashDictionary hashTable, int score) {
        Data newData = new Data(configString(), score); // create a new Data object with the string configuration and score
        hashTable.put(newData); // put the Data object int the hash table
    }

    /**
     * Save the play to the board
     * @param row the row of the play
     * @param col the column of the play
     * @param symbol the symbol of the play
     */
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    /**
     * Check if the square is empty
     * @param row the row of the square
     * @param col the column of the square
     * @return true if the square is empty, false otherwise
     */
    public boolean squareIsEmpty(int row, int col) {
        return board[row][col] == ' ' ? true: false;
    }

    /**
     * Check if the symbol wins
     * @param symbol the symbol of a player
     * @return true if the symbol wins, false otherwise
     */
    public boolean wins(char symbol) {
        int countRow = 0, countCol = 0, countDiag = 0, countInvDiag = 0; // initialize the counts to 0
        for (int i = 0; i < board_size; i++) { // iterate through the rows
            if (board[i][i] == symbol) { countDiag++; } // check if the diagonal has the symbol, increment the diagonal count if it does
            for (int j = 0; j < board_size; j++) { // iterate through the columns
                if (board[i][j] == symbol) { countRow++; } // check if the row has the symbol, increment the row count if it does
                if (board[j][i] == symbol) { countCol++; } // check if the column has the symbol, increment the col count if it does
                if (i + j == board_size - 1 && board[i][j] == symbol) { countInvDiag++; } // check if the inverse diagonal has the symbol, increment the inv diagonal count if it does
            }
            if (countRow >= lengthToWin || countCol >= lengthToWin) { return true; } // if the row or col count is greater than or equal to the length to win return true
            countRow = 0;
            countCol = 0;
        }
        if (countDiag >= lengthToWin || countInvDiag >= lengthToWin) { return true; } // if the diagonal or inv diagonal count is greater than or equal to the length to win return true
        return false; // return false otherwise
    }

    /**
     * Check if the game is a draw
     * @return true if the game is a draw, false otherwise
     */
    public boolean isDraw() {
        if (wins('X') || wins('O')) { return false; } // if X or O wins return false
        for (int i = 0; i < board_size; i++) { // iterate through the board
            for (int j = 0; j < board_size; j++) { // if the square is empty return false
                if (squareIsEmpty(i, j)) { return false; }
            }
        }
        return true; // return true otherwise
    }

    /**
     * Get the score of the board
     * @return the score of the board
     */
    public int evalBoard() {
        if (wins('X')) { // if X wins return 0
            return 0;
        } else if (wins('O')) { // if O wins return 3
            return 3;
        } else if (isDraw()){ // if the game is a draw return 2
            return 2;
        } else { // if the game is undecided return 1
            return 1;
        }
    }
}
