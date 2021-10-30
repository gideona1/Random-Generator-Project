/**	Project 2 : TRNG vs PRNG vs CSPRNG
 * RandomGenerator : main class for comparing each random class
 * 
 * @author Gideon Antwi & Chindeum Egeh
 * @version 10/30/2021
 */

import java.util.Random;
import java.security.SecureRandom;

public class RandomGenerator {
    /**
     * TODO:
     * Generate a set of integers from a PRNG and a CSPRNG using built-in functions. [DONE]
     * Request the same size/range integers from Random.org using their HTTP Interface. [DONE]
     * Create Eucledian Algorithm. [DONE]
     * Count the number of pairs with gcd = 1 using the Eucledian algorithm [PROGRESS]
     * Calculate the statistical estimate of Pi using Cesaro's Theorem from the TRNG, PRNG, and CSPRNG. []
     */
    public static void main(String[] args) {
        int pairs = 6;
        Random random = new Random();
        SecureRandom srandom = new SecureRandom();
        TrueRandom trandom = new TrueRandom(pairs);

        int[] prng = new int[pairs];
        int[] csprng = new int[pairs];
        int[] trng = trandom.getTrueRandom();

        // Create Random Generated Numbers
        for (int i = 0; i < pairs; i++) {
            prng[i] = random.nextInt(100) + 1; // Get Random int from RandomClass
            csprng[i] = srandom.nextInt(100) + 1; // Get Random int from SecureRandomClass
        }

        // Testing
        for (int i : prng) {
            System.out.println("PRNG: " + i);
        }

        for (int i : csprng) {
            System.out.println("CSPRNG: " + i);
        }

        for (int i : trng) {
            System.out.println("TRNG: " + i);
        }

        System.out.println("GCD: " + EuclideanAlgorithm(10, 28));
    }

    /**
     * Returns the GCD of 2 numbers
     * @param x number
     * @param y number
     * @return greatest common divisor (int)
     */
    public static int EuclideanAlgorithm(int x, int y) {
        if(x < y) {
            int prevX = x;
            int prevY = y;

            x = prevY;
            y = prevX;
        }

        if( y == 0 )
            return x;

        return (EuclideanAlgorithm(y, x % y));
    }

    /**
     * Returns how many pairs have a GCD of 1
     * @param array
     * @return number of pairs with GCD = 1
     */
    public static int GCDCount(int[] array) {
        int count = 0;

        for (int i = 0; i < array.length;) {
            
        }

        return count;
    }
}
