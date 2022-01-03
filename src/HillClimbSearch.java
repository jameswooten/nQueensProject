/**
 * This class represents the Hill Climbing Local Search Algorithm
 * It takes a current state and finds the best neightboring state
 * based on the h(n) value. It will run the simulations for the 
 * specified number entered into the constructor.
 * @author James Wooten
 * @version 1.0
 */
public class HillClimbSearch {
    protected State current;
    protected int numSimulations;
    protected int successes;
    protected int failures;
    protected int numStepsSuccess;
    protected int numStepsFail;
    protected int n;


    /**
     * This constructor passes the user desired number of
     * simulations to run and saves to n variable
     * 
     * @param n the number of simulation to run 
     */
    public HillClimbSearch(int n) {

        this.numSimulations = n;
        
    }

/** 
 * This method manages the iteration of the specified number of 
 * simulations
 * 
 * @param n the desired n value of the NxN board and number of queens
 */

    protected void run(int n) {

        this.n = n;

        //Loop through the number of desired simulations
        for(int i = 0; i < this.numSimulations; i++) {

            this.current = new State(this.n);

            //Simulate a search
            simRound(i);

        }

        //Prints the search summary stats
        printResults();
    }

/** 
 * This method will continue to call methods searching
 * for the best neighbor until the h(n) == 0 or there are
 * no better neighbors
 * @param simNum the current simulation round
 */

    protected void simRound(int simNum) {
        
        int count = 0;

        //Loop to continue until the solved state is found
        while(current.getHValue() != 0) {

            //Find and set the best neighbor as the current state
            this.generateBestNeighbors();

            this.current = this.current.getBestNeighbor();

            //If no better neighbors, break out of round
            if(this.current.getBestNeighbor() == this.current) {
                break;
            }
            count++;

            //Prints search path if within last 4 rounds
            printPaths(simNum, count);

        }

        //Print out success or fail if last 4 rounds
        printSuccessFail(simNum);

        //Check whether to add stats to success or failures
        checkStats(count);
    }

/** 
 * This method checks whether to attribute the stats of the current round
 * into the success or failure category
 * @param count the number of steps it took to reach the end state
 */

    protected void checkStats(int count){

        //If solved -> success
        if(this.current.getHValue() == 0) {
            this.successes++;
            this.numStepsSuccess += count;

        //Else -> failure
        } else {
            this.failures++;
            this.numStepsFail += count;
        }
    }


    /**
     * Searches through the board and generates a new state for possible moves.
     * If the generated state is better than the current state's best alternative
     * then that state is set as the best option
     */
    public void generateBestNeighbors() {
        //Loops through board
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                //Check that the queen is not in the location
                if(this.current.getBoard()[j][i] != 1) {
                    State temp = new State(current.copyBoard(), current);

                    temp.swapBoard(i, j, temp.getQueensLocation()[i], temp);

                    temp.setHValue(0); //Reset h(n) value to recalc

                    temp.findQueens();

                    temp.calculateHeuristic();

                    //If no best neighbor, set to current
                    if(this.current.getBestNeighbor() == null) {
                        this.current.setBestNeighbor(current);
                    }

                    //If better than current best neighbor, then set as new best neighbor
                    if(temp.getHValue() < this.current.getBestNeighbor().getHValue()) {
                        this.current.setBestNeighbor(temp);
                    }
                }
            }
        }
    }
    
    /** 
     * 
     * This method prints the search path if the simulation is one of the last 4
     * @param simNum the current simulation number
     * @param count the number of steps in the round
     */
    
    protected void printPaths(int simNum, int count) {
        
        if(simNum == this.numSimulations - 4 && count == 1) {
            printSearchType();
        }

        if(simNum > this.numSimulations - 5) {
            this.current.printBoard();
            System.out.println("h(n): " + this.current.getHValue());
            System.out.println();
        }
    }

/** 
 * This method prints if the search was a success or a failure
 * @param simNum the current simualtion number
 */

    protected void printSuccessFail(int simNum) {
        
        if(this.current.getHValue() == 0 && simNum > this.numSimulations - 5) {
            System.out.println("Success!");
        } else if (this.current.getHValue() != 0 && simNum > this.numSimulations - 5) {
            System.out.println("Failure!");
        }
    }

    /**
     * Prints out the name of the search type
     */
    public void printSearchType() {
        System.out.println();
        System.out.println("-----HILL CLIMB SEARCH RESULTS-----");
    }

    /**
     * Prints out the stats of the simulations
     */
    public void printResults() {

        this.printSearchType();

        if(this.successes > 0) {
            System.out.println("Number of Simulations: " + this.numSimulations);
            System.out.println("Number of Successes: " + this.successes);
            System.out.println("Number of Moves: " + this.numStepsSuccess);
            System.out.println("Average Steps for Success: " +  (double)(this.numStepsSuccess / this.successes));
        }
        
        if(this.failures > 0) {
            System.out.println("Number of Failures: " + this.failures);
            System.out.println("Number of Moves: " + this.numStepsFail);
            System.out.println("Average Steps for Failure: " + (double)(this.numStepsFail / this.failures));
        }
    }

}
