/**	Project 2 : TRNG vs PRNG vs CSPRNG
 * RandomGenerator : main class for comparing each random class
 * 
 * @author Gideon Antwi & Chindeum Egeh
 * @version 11/7/2021
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
     * Calculate the statistical estimate of Pi using Cesaro's Theorem from the TRNG, PRNG, and CSPRNG. [DONE -]
     * Create 30 statistical estimates of Pi [DONE]
     * Generate a frequency distribution table, calculate the average, and the standard deviation for each RNG. [DONE]
     */
    
    public static void main(String[] args) {
        int pairs = 100;
        Random random = new Random();
        SecureRandom srandom = new SecureRandom();
        TrueRandom trandom = new TrueRandom(pairs);

        int[] prng = new int[pairs * 2];
        int[] csprng = new int[pairs * 2];
        int[] trng = trandom.getTrueRandom();

        double[] prngEstimates = new double[30];
        double[] csprngEstimates = new double[30];
        double[] trngEstimates = new double[30];

        for (int i = 0; i < 30; i++) {
            // Create Random Generated Numbers
            for (int j = 0; j < pairs * 2; j++) {
                prng[j] = random.nextInt(100) + 1; // Get Random int from RandomClass
                csprng[j] = srandom.nextInt(100) + 1; // Get Random int from SecureRandomClass
            }

            trng = trandom.getTrueRandom();

            // Display Cases
            System.out.println("CASE: "+ (i+1));
            System.out.println("-----------------------------");

            System.out.println("(TRNG) GCD Equal to 1: " + GCDCount(trng));
            trngEstimates[i] = GetPIEstimate(GCDCount(trng), 100);
            System.out.println("(TRNG) PI Estimate: " + trngEstimates[i]);
            System.out.println("(TRNG) Difference From PI: " + (trngEstimates[i] -  Math.PI)  + "\n");

            System.out.println("(PRNG) GCD Equal to 1: " + GCDCount(prng));
            prngEstimates[i] = GetPIEstimate(GCDCount(prng), 100);
            System.out.println("(PRNG) PI Estimate: " + prngEstimates[i]);
            System.out.println("(PRNG) Difference From PI: " + (prngEstimates[i] -  Math.PI)  + "\n");

            System.out.println("(CSPRNG) GCD Equal to 1: " + GCDCount(csprng));
            csprngEstimates[i] = GetPIEstimate(GCDCount(csprng), 100);
            System.out.println("(CSPRNG) PI Estimate: " + csprngEstimates[i]);
            System.out.println("(CSPRNG) Difference From PI: " + (csprngEstimates[i] -  Math.PI)  + "\n");
        }

        // Averages
        System.out.println("AVERAGE: ");
        System.out.println("-----------------------------");

        System.out.println("(TRNG) Pi Estimate Average: " + GetAverage(trngEstimates));
        System.out.println("(PRNG) Pi Estimate Average: " + GetAverage(prngEstimates));
        System.out.println("(CSPRNG) Pi Estimate Average: " + GetAverage(csprngEstimates) + "\n");

        // Frequency Table
        System.out.println("FREQUENCY TABLE: ");
        System.out.println("-----------------------------");
        GenerateTable(prngEstimates, trngEstimates, csprngEstimates);

        // Standard Deviation
        System.out.println("STANDARD DEVIATION: ");
        System.out.println("-----------------------------");

        System.out.println("(TRNG) Standard Deviation: " + GetStandardDev(trngEstimates));
        System.out.println("(PRNG) Standard Deviation: " + GetStandardDev(prngEstimates));
        System.out.println("(CSPRNG) Standard Deviation: " + GetStandardDev(csprngEstimates) + "\n");

        // Check for TRNG server errors
        System.out.println(ErrorCheck(trngEstimates));
    }

    /**
     * Get average (mean) of all values in array
     * @param array
     * @return
     */
    public static double GetAverage(double[] array) {
        double total = 0;
        for (int i = 0; i < array.length; i++) {
            total += array[i];
        }

        return total / array.length;
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

    /**
     * Counts how many values in value are between the min and max
     * @param estimate
     * @param min
     * @param max
     * @return count
     */
    public static int EstimateCount(double[] estimate, double min, double max) {
        var count = 0;
        for (int i = 0; i < estimate.length; i++) {
            if(estimate[i] >= min && estimate[i] < max) {
                count++;
            }
        }
        return count;
    }

    /**
     * Generate frequency dist. table
     * @param prngEstimate PRNG PI estimate array
     * @param trngEstimate TRNG PI estimate array
     * @param csprngEstimate CSPRNG PI estimate array
     */
    public static void GenerateTable(double[] prngEstimate, double[] trngEstimate, double[] csprngEstimate) {
        System.out.printf("%10s| %8s| %8s| %8s| %8s%n", "ESTIMATES", "TRNG", "PRNG", "CSPRNG", "OVERALL");
        System.out.printf("%10s| %8d| %8d| %8d| %8d%n", "< 3", EstimateCount(trngEstimate, -999, 3), EstimateCount(prngEstimate, -999, 3), EstimateCount(csprngEstimate, -999, 3), (EstimateCount(trngEstimate, -999, 3) + EstimateCount(prngEstimate, -999, 3) + EstimateCount(csprngEstimate, -999, 3)));
        System.out.printf("%10s| %8d| %8d| %8d| %8d%n", "3 - 3.2", EstimateCount(trngEstimate, 3, 3.2), EstimateCount(prngEstimate, 3, 3.2), EstimateCount(csprngEstimate, 3, 3.2), (EstimateCount(trngEstimate, 3, 3.2) + EstimateCount(prngEstimate, 3, 3.2) + EstimateCount(csprngEstimate, 3, 3.2)));
        System.out.printf("%10s| %8d| %8d| %8d| %8d%n", "3.2 - 3.4", EstimateCount(trngEstimate, 3.2, 3.4), EstimateCount(prngEstimate, 3.2, 3.4), EstimateCount(csprngEstimate, 3.2, 3.4), (EstimateCount(trngEstimate, 3.2, 3.4) + EstimateCount(prngEstimate, 3.2, 3.4) + EstimateCount(csprngEstimate, 3.2, 3.4)));
        System.out.printf("%10s| %8d| %8d| %8d| %8d%n", "3.4 - 3.6", EstimateCount(trngEstimate, 3.4, 3.6), EstimateCount(prngEstimate, 3.4, 3.6), EstimateCount(csprngEstimate, 3.4, 3.6), (EstimateCount(trngEstimate, 3.4, 3.6) + EstimateCount(prngEstimate, 3.4, 3.6) + EstimateCount(csprngEstimate, 3.4, 3.6)));
        System.out.printf("%10s| %8d| %8d| %8d| %8d%n", "3.6 - 3.8", EstimateCount(trngEstimate, 3.6, 3.8), EstimateCount(prngEstimate, 3.6, 3.8), EstimateCount(csprngEstimate, 3.6, 3.8), (EstimateCount(trngEstimate, 3.6, 3.8) + EstimateCount(prngEstimate, 3.6, 3.8) + EstimateCount(csprngEstimate, 3.6, 3.8)));
        System.out.printf("%10s| %8d| %8d| %8d| %8d%n", "3.8 - 4", EstimateCount(trngEstimate, 3.8, 4), EstimateCount(prngEstimate, 3.8, 4), EstimateCount(csprngEstimate, 3.8, 4), (EstimateCount(trngEstimate, 3.8, 4) + EstimateCount(prngEstimate, 3.8, 4) + EstimateCount(csprngEstimate, 3.8, 4)));
        System.out.printf("%10s| %8d| %8d| %8d| %8d%n", "> 4", EstimateCount(trngEstimate, 4, 999), EstimateCount(prngEstimate, 4, 999), EstimateCount(csprngEstimate, 4, 999), (EstimateCount(trngEstimate, 4, 999) + EstimateCount(prngEstimate, 4, 999) + EstimateCount(csprngEstimate, 4, 999)));
        System.out.println();
    }

    /**
     * Get standard deviation from array
     * @param estimate - array
     * @return standard deviation
     */
    public static double GetStandardDev(double[] estimate) {
        // Get Mean of array
        double mean = GetAverage(estimate);

        // Subtract each value in array by mean and square it, then store result in another array
        double[] newSet = new double[estimate.length];
        for (int i = 0; i < estimate.length; i++) {
            newSet[i] = Math.pow((estimate[i] - mean), 2);
        }

        double newSetMean = GetAverage(newSet);

        return Math.sqrt(newSetMean);
    }

    /**
     * Display whether there was error with the true random generator
     * @param trngValue
     * @return success or error message
     */
    public static String ErrorCheck(double[] trngValue) {
        for(double values : trngValue) {
            if(values == 0.0) {
                return "Done. Results for TRNG may not be accurate due to one or more website connection error.";
            }
        }

        return "Done. The program ran with no errors.";
    }
}
