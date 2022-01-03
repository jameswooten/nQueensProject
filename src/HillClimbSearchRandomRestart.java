/**
 * This class represents Hill Climbing Search with Random Restarts when an
 * alternative is not found. It is an extension of the HillClimbSearch Class
 * @author James Wooten
 * @version 1.0
 */
public class HillClimbSearchRandomRestart extends HillClimbSearch{
    private int numRestarts;

    /**
     * Constructor that takes in the number of simulations to run
     * @param n
     */
    public HillClimbSearchRandomRestart(int n) {
        super(n);

    }

    /**
     * Allows for simulation of rounds like the parent Hill Climb Search,
     * but includes the ability to randomly create new board state if no
     * solutions are found
     * @param numSim the current simulation number
     */
    @Override
    public void simRound(int numSim) {
        int count = 0;

        //While the board is not solved
        while(current.getHValue() != 0) {
            this.generateBestNeighbors();

            this.current = this.current.getBestNeighbor();

            //If there are no neighbor options then reset
            if(this.current.getBestNeighbor() == this.current) {
                this.current = new State(this.n);
                this.numRestarts++;
            }
            count++;
        }

        checkStats(count);
    }

    /**
     * Prints the simulation stats including the random restart count
     */
    @Override
    public void printResults() {

        System.out.println();
        System.out.println("-----HILL CLIMB SEARCH w/ RANDOM RESTART RESULTS-----");

        if(this.successes > 0) {
            System.out.println("Number of Simulations: " + this.numSimulations);
            System.out.println("Number of Restarts: " + this.numRestarts);
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
