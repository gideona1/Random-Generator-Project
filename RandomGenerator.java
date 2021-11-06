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
     * Count the number of pairs with gcd = 1 using the Eucledian algorithm [DONE]
     * Calculate the statistical estimate of Pi using Cesaro's Theorem from the TRNG, PRNG, and CSPRNG. []
     * Create 30 statistical estimates of Pi []
     * Generate a frequency distribution table, calculate the average, and the standard deviation for each RNG. []
     */
    
    public static void main(String[] args) {
        int pairs = 100;
        Random random = new Random();
        SecureRandom srandom = new SecureRandom();
        TrueRandom trandom = new TrueRandom(pairs);

        int[] prng = new int[pairs * 2];
        int[] csprng = new int[pairs * 2];
        int[] trng = trandom.getTrueRandom();

        // // Create Random Generated Numbers
        // for (int i = 0; i < pairs * 2; i++) {
        //     prng[i] = random.nextInt(100) + 1; // Get Random int from RandomClass
        //     csprng[i] = srandom.nextInt(100) + 1; // Get Random int from SecureRandomClass
        // }

        double[] prngEstimates = new double[30];
        double[] csprngEstimates = new double[30];
        double[] trngEstimates = new double[30];

        // Testing
        // for (int i : prng) {
        //     System.out.println("PRNG: " + i);
        // }

        // for (int i : csprng) {
        //     System.out.println("CSPRNG: " + i);
        // }

        // for (int i : trng) {
        //     System.out.println("TRNG: " + i);
        // }

        for (int i = 0; i < 30; i++) {
            // Create Random Generated Numbers
            for (int j = 0; j < pairs * 2; j++) {
                prng[j] = random.nextInt(100) + 1; // Get Random int from RandomClass
                csprng[j] = srandom.nextInt(100) + 1; // Get Random int from SecureRandomClass
            }

            trng = trandom.getTrueRandom();

            System.out.println("CASE: "+ (i+1));
            System.out.println("-----------------------------");

            System.out.println("(TRNG) GCD Equal to 1: " + GCDCount(trng));
            trngEstimates[i] = GetPIEstimate(GCDCount(trng), 100);
            System.out.println("(TRNG) PI Estimate: " + trngEstimates[i]);
            System.out.println("(TRNG) Difference: " + (trngEstimates[i] -  Math.PI)  + "\n");

            System.out.println("(PRNG) GCD Equal to 1: " + GCDCount(prng));
            prngEstimates[i] = GetPIEstimate(GCDCount(prng), 100);
            System.out.println("(PRNG) PI Estimate: " + prngEstimates[i]);
            System.out.println("(PRNG) Difference: " + (prngEstimates[i] -  Math.PI)  + "\n");

            System.out.println("(CSPRNG) GCD Equal to 1: " + GCDCount(csprng));
            csprngEstimates[i] = GetPIEstimate(GCDCount(csprng), 100);
            System.out.println("(CSPRNG) PI Estimate: " + csprngEstimates[i]);
            System.out.println("(CSPRNG) Difference: " + (csprngEstimates[i] -  Math.PI)  + "\n");
        }

        // Averages
        System.out.println("(TRNG) Pi Estimate Average: " + GetAverage(trngEstimates));
        System.out.println("(PRNG) Pi Estimate Average: " + GetAverage(prngEstimates));
        System.out.println("(CSPRNG) Pi Estimate Average: " + GetAverage(csprngEstimates));
    }

    public static double GetAverage(double[] estimates) {
        double total = 0;
        for (int i = 0; i < estimates.length; i++) {
            total += estimates[i];
        }

        return total / estimates.length;
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
            if(EuclideanAlgorithm(array[i], array[i+1]) == 1) {
                count++;
            }

            i += 2;
        }

        return count;
    }

    /**
     * Returns statistical estimate of Pi
     * @param count number of pairs with GCD of 1
     * @param total number of pairs
     * @return pi estimate
     */
    public static double GetPIEstimate(int count, int total) {
        return (double)(6 * count) / total;
    }
}
