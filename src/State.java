import java.util.Random;

/**
 * This class represent a board state withing the n-Puzzle game
 * @author James Wooten
 * @version 1.0
 */
public class State {

    private int hValue; //h(n) value
    private int n; //number of queens and NxN board
    private int[][] board; //Game board
    private int[] queenLocations; //Location of queens on current board
    private State bestNeighbor = null;

   /**
     * Constructor that takes in the number of simulations to run
     * @param n the num of simulations to run
     */
    public State(int n) {
        this.n = n;
        this.board = new int[n][n];
        this.queenLocations = new int[n];

        this.generateRandomBoard();
        this.calculateHeuristic();
        this.findQueens();
    }

    /**
     * Constructor that copies the parents board and n value
     * @param board the game board to copy
     * @param parent the parent to copy the n value and queens locations from
     */
    public State(int[][] board, State parent) {
        this.board = board;
        this.n = parent.n;
        this.queenLocations = this.copyArray(parent.getQueensLocation());
        this.hValue = 0;
        this.findQueens();
        this.calculateHeuristic();
    }

    /**
     * Generates board and randomly populates with queens
     */
    public void generateRandomBoard() {
        //Fill in all spots with 0
        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < n; j++) {
                this.board[i][j] = 0;
            }
        }

        //Randomly fill in the board with 1s to represent the queens until remaining Queens = 0
        Random rng = new Random();

        //Place queens in random spots
        for(int i = 0; i < n; i++) {
            int queenPlacement = rng.nextInt(n);
            this.board[queenPlacement][i] = 1;
            this.queenLocations[i] = queenPlacement;
        }
    }

    
    /** 
     * Deep copies a 1-dim array
     * @param arr the array to copy
     * @return int[] new copy of array
     */
    protected int[] copyArray(int[] arr) {

        int[] temp = new int[arr.length];

        for(int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }

        return temp;
    }

    
    /** 
     * Getter for best neighbor
     * @return State the best neighbor
     */
    public State getBestNeighbor() {
        return bestNeighbor;
    }


    /**
     * Calls the horizontal and diagonal check method for each queen
     */
    public void calculateHeuristic() {
        for(int i = 0; i < this.queenLocations.length; i++) {
            checkDiagonalConflicts(i);
            checkHorizontalConflicts(i);
        }

    }

    
    /** 
     * Checks if there are any queens that diagonally conflict with a queen
     * @param i the queen location to check for conflicts
     */
    public void checkDiagonalConflicts(int i) {
        for(int j = 0; j < this.queenLocations.length; j++) {
            if(j != i) {
                if(Math.abs(i - j) == Math.abs(this.queenLocations[i] - this.queenLocations[j])) {
                    this.hValue++;
                }
            }
        }
                
    }

    
    /** 
     * Checks if there are any horizontal conflicts for a queen
     * @param i the queen to check for conflicts
     */
    public void checkHorizontalConflicts(int i) {
            for(int j = 0; j < this.queenLocations.length; j++) {
                if(j != i) {
                    if(this.queenLocations[i] == this.queenLocations[j]) {
                        this.hValue++;
                    }
                }
            }
    }

    
    /** 
     * Sets the game board 
     * @param board the board to set
     */
    public void setBoard(int[][] board) {
        this.board = board;
        this.findQueens(); //Get location of queens
        this.hValue = 0;
        this.calculateHeuristic(); //Calculate h(n)
    }

    
    /** 
     * Copies the current board
     * @return int[][] the copy of the current board
     */
    public int[][] copyBoard() {

        int[][] newBoard = new int[this.n][this.n];

        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                newBoard[i][j] = this.board[i][j];
            }
        }

        return newBoard;
    }

    

    
    /** 
     * Deep copies a game board
     * @param arr the board to copy
     * @return int[][] the new copy
     */
    public int[][] copyBoard(int[][] arr) {

        int[][] newBoard = new int[this.n][this.n];

        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                newBoard[i][j] = arr[i][j];
            }
        }

        return newBoard;
    }

    
    /** 
     * Getter for h(n)
     * @return int the h(n)
     */
    public int getHValue() {
        return hValue;
    }

    
    /** 
     * Swaps the location of a queen with a non-queen location
     * @param i the column
     * @param newQueen the row of the new queen
     * @param oldQueen the row of the old queen
     * @param board the state to swap the game board
     */
    public void swapBoard(int i, int newQueen, int oldQueen, State board) {
        board.board[newQueen][i] = 1;
        board.board[oldQueen][i] = 0;
    }

    /**
     * Finds the locations of the queens on the game boards
     */
    public void findQueens() {
        for(int i = 0; i < n; i ++) {
            for( int j = 0; j < n; j++) {
                if(this.board[j][i] == 1) {
                    this.queenLocations[i] = j;
                }
            }
        }
    }

    
    /** 
     * Getter for game board
     * @return int[][] game board
     */
    public int[][] getBoard() {
        return board;
    }

    
    /** 
     * Getter for queens location
     * @return int[] the location of the queens
     */
    public int[] getQueensLocation() {
        return this.queenLocations;
    }

    
    /** 
     * Setter for h(n)
     * @param h the new h(n)
     */
    public void setHValue(int h) {
        this.hValue = h;
    }

    
    /** 
     * Setter for best neighbor
     * @param neighbor new best neighbor
     */
    public void setBestNeighbor(State neighbor) {
        this.bestNeighbor = neighbor;
    }

    
    /** 
     * Compares gameboards to see if they are identical
     * @param arr the board to check against
     * @return Boolean if identical
     */
    public Boolean compareBoards(int[][] arr) {
        for(int i = 0; i < this.n; i++) {
            for( int j = 0; j < this.n; j++) {
                if(this.board[i][j] != arr[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints formatted game board
     */
    public void printBoard() {
        for(int i = 0; i < n; i++) {
            for( int j = 0; j < n; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }
}