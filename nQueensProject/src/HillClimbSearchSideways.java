/**
 * This class represents Hill Climbing Search with Sideways when an
 * alternative with a lower h(n) is not found. It is an extension of the HillClimbSearch Class
 * @author James Wooten
 * @version 1.0
 */

public class HillClimbSearchSideways extends HillClimbSearch{


    final int NUM_PLATEAU_LOOPS = 100; //The number of times to allow plateau traversal
    public NeighborQueue neighbors;
    protected State last;

    /**
     * Constructor that takes in the number of simulations to run
     * @param n the num of simulations to run
     */
    public HillClimbSearchSideways(int n) {
        super(n);
        this.neighbors = new NeighborQueue();
    }
    

    /**
     * Allows for simulation of rounds like the parent Hill Climb Search,
     * but includes the ability to select a neighbor with the same h(n) value
     * @param numSim the current simulation number
     */
    @Override
    public void simRound(int simNum) {
        
        int count = 0;
        int plateauTracker = 0;
        int plateauCount = 0;

        //Until board is solved
        while(current.getHValue() != 0) {

            plateauTracker = current.getHValue();

            this.generateBestNeighbors(); //Get best neighbor

            //If no neighbors then break
            if(this.neighbors.isEmpty()) {
                break;
            }

            this.last = this.current;

            //If neighbor board is the same as current then remove from queue
            while(this.neighbors.peek().compareBoards(this.last.getBoard())) {
                this.neighbors.remove();
            }

            //Get random neighbor from queue and set to current
            this.current.setBestNeighbor(this.neighbors.dequeue());
    
            this.current = this.current.getBestNeighbor();

            //If the h(n) value is the same then track the plateau 
            if(this.current.getHValue() == plateauTracker) {
                plateauCount++;
            } else {
                plateauCount = 0;
            }

            if(plateauCount == NUM_PLATEAU_LOOPS) {
                break;
            }
            count++;
            printPaths(simNum, count);
        }

        printSuccessFail(simNum);
        checkStats(count);
    }
    
     /**
     * Searches through the board and generates a new state for possible moves.
     * If the generated state is better or equal to the current state's best alternative
     * then that state is sent into a queue for random selection
     */
    @Override
    public void generateBestNeighbors() {
        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                if(this.current.getBoard()[j][i] != 1) {
                    State temp = new State(current.copyBoard(), current);

                    temp.swapBoard(i, j, temp.getQueensLocation()[i], temp);

                    temp.setHValue(0); //Reset h(n) value to recalc

                    temp.findQueens();

                    temp.calculateHeuristic();
            
                    if(this.current.getBestNeighbor() == null) {
                        this.current.setBestNeighbor(current);
                    }

                    if(temp.getHValue() <= this.current.getBestNeighbor().getHValue()) {
                        this.neighbors.enqueue(temp);
                    }
                }
            }
        }
    }

     /**
     * Prints out the name of the search type
     */
    @Override
    public void printSearchType() {
        System.out.println();
        System.out.println("-----HILL CLIMB SEARCH w/ SIDEWAYS RESULTS-----");
    }

}
