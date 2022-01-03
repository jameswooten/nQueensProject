import java.util.Scanner;

/**
 * This Class acts as the driver for the N-Queen Puzzle.
 * A random NxN game board is generated based on the user input
 * and the search will attempt to find the n queen placements where
 * no two queens conflict vertically, horizontally, or diagonally.
 * It asks for the user input and uses that to set up and 
 * run each of the different searches
 * @author James Wooten
 * @version 1.0
 */
public class Driver {

    static int n;

    public static void main(String[] args) throws Exception {

        final int NUM_SIMULATIONS = 1000; //Number of simulations *****CHANGE IF DESIRE LESS SIMULATIONS*****

        //Create HillClimbSearch
        HillClimbSearch search = new HillClimbSearch(NUM_SIMULATIONS);

        //Create HillClimbSearch w Sideways movements
        HillClimbSearch searchSideways = new HillClimbSearchSideways(NUM_SIMULATIONS);

        //Create HillClimbSearch w Random Restart
        HillClimbSearch searchRestart = new HillClimbSearchRandomRestart(NUM_SIMULATIONS);

        //Create HillClimbSearch with Random Restart and Sideways Moves
        HillClimbSearch searchRestartSideways = new HillClimbSearchRandomRestartSideways(NUM_SIMULATIONS);

        //Get user input and run searches
        getUserN();

        search.run(n);

        searchSideways.run(n);

        searchRestart.run(n);

        searchRestartSideways.run(n);
    }

    /**
     * This method gets the user input and saves the int as the N for 
     * the searches
     */
    private static void getUserN() {

        boolean validInput = false;

        Scanner input = new Scanner(System.in);

        do {

            System.out.println("Please enter the N value for the N-Queens problem");

            if(input.hasNextInt() == false) {
                System.out.println("Must be an integer.");
                input.next();
            } else {
                n = input.nextInt();
                validInput = true;
            }
            
        } while (validInput == false);
    }
}