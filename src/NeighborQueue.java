import java.util.ArrayList;
import java.util.Random;


/**
 * This class represents a queue of the possible neighbors to select.
 * Neighbors within this queue must be of the same h(n). If a lower option is found
 * then the queue is cleared and the new state is added. Dequeue method selects a random 
 * neighbor from the queue to prevent the selection being location biased
 * @author James Wooten
 * @version 1.0
 */
public class NeighborQueue {
    private ArrayList<State> neighborList;

   /**
     * Constructor that takes in the number of simulations to run
     * @param n simulations to run
     */
    public NeighborQueue(){
        this.neighborList = new ArrayList<>();
    }

    /**
     * Adds the state to the queue if the h(n) is lower or than the current queue h(n).
     * If lower then the queue is reset before adding
     * @param e
     */
    public void enqueue(State e) {

        //Add if empty
        if(this.neighborList.isEmpty()) {
            this.neighborList.add(e);
        }

        //If less than queue then clear queue and add state
        if(this.neighborList.get(0).getHValue() > e.getHValue()) {
            this.neighborList.clear();
            this.neighborList.add(e);

        } else if (this.neighborList.get(0).getHValue() == e.getHValue()) {
            this.neighborList.add(e);
        }
    }

    /**
     * Removes and returns a random neighbor from the queue and clears queue
     * @return random neighbor state
     */
    public State dequeue() {
        if(this.neighborList.isEmpty()) {
            return null;
        }

        Random rng = new Random();

        int selection = rng.nextInt(neighborList.size());

        State temp = this.neighborList.get(selection);

        this.neighborList.clear();

        return temp;
    }

    /**
     * Checks if queue is empty
     * @return if the queue is empty
     */
    public boolean isEmpty() {
        return this.neighborList.isEmpty();
    }

    /**
     * Removes the front neighbor from the queue
     */
    public void remove() {
        this.neighborList.remove(0);
    }

    /**
     * Returns the front state from the queue
     * @return the front state
     */
    public State peek() {
        return this.neighborList.get(0);
    }

}
