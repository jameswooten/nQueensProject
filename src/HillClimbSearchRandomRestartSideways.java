
/**
* This class represents Hill Climbing Search with Random Restarts when an
* alternative is not found. It is an extension of the HillClimbSearch Sideways Class
* @author James Wooten
* @version 1.0
*/
 
public class HillClimbSearchRandomRestartSideways extends HillClimbSearchSideways {
    private int numRestarts; //Number of restarts used

    /**
     * Constructor that takes in the number of simulations to run
     * @param n
     */
    public HillClimbSearchRandomRestartSideways(int n) {
        super(n);
    }

    /**
     * Allows for simulation of rounds like the parent Hill Climb w Sideways Search,
     * but includes the ability to randomly create new board state if no
     * solutions are found within the loop requirements
     * @param numSim the current simulation number
     */
    @Override
    public void simRound(int numSim) {
        
        int count = 0;
        int plateauTracker = 0;
        int plateauCount = 0;

        //Until the board is solved
        while(current.getHValue() != 0) {

            plateauTracker = current.getHValue();

            this.generateBestNeighbors();

            //If there are no options, reset
            if(this.neighbors.isEmpty()) {
                this.current = new State(this.n);
                this.numRestarts++;
                count++;
                continue;
            }

            this.last = this.current;

            //If the neighbor board is the same remove from queue
            while(this.neighbors.peek().compareBoards(this.current.getBoard())) {
                this.neighbors.remove();
            } 

            //Gets random neighbor from queue
            this.current.setBestNeighbor(this.neighbors.dequeue());
    

            this.current = this.current.getBestNeighbor();

            //If the h(n) value is the same then track the plateau
            if(this.current.getHValue() == plateauTracker) {
                plateauCount++;
            } else {
                plateauCount = 0;
            }

            //If the plateau loop limit is reached then restart
            if(plateauCount == NUM_PLATEAU_LOOPS) {
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
        System.out.println("-----HILL CLIMB SEARCH w/ RANDOM RESTART & SIDEWAYS RESULTS-----");

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
