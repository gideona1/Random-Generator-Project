/**	Project 2 : TRNG vs PRNG vs CSPRNG
 * TrueRandom : generates array with fetched random numbers from random.org
 * 
 * @author Gideon Antwi & Chindeum Egeh
 * @version 10/23/2021
 */

import java.util.Scanner;
import java.net.*;
import java.io.*;

public class TrueRandom {
    private int max, min, pairs;

    /**
     * TrueRandom: constructor for getTrueRandom method
     * @param pairs length of array
     */
    public TrueRandom (int pairs) {
        this.min = 1;
        this.max = 100;
        this.pairs = pairs;
    }

    /**
     * TrueRandom: constructor for getTrueRandom method
     * @param min minimum range
     * @param max maximun range
     * @param pairs length of array
     */
    public TrueRandom (int min, int max, int pairs) {
        this.min = min;
        this.max = max;
        this.pairs = pairs;
    }

    /**
     * Return true random number from random.org
     * @return array of random int numbers
     */
    public int[] getTrueRandom() {
        String address = "https://www.random.org/integers/?num=" + pairs + "&min=" + min +"&max=" + max +"&col=2&base=10&format=plain&rnd=new";
        int[] numbers = new int[pairs];
        
        try {
            int ticks = 0;
            URL u = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            InputStream stream = connection.getInputStream();
            Scanner in = new Scanner(stream);

            while (in.hasNext()) {
                numbers[ticks] = in.nextInt();
                ticks++;
            }

            in.close();
        } catch (java.net.MalformedURLException ex) {
            System.out.println("Invalid URL, Returning zeros.");
        } catch (java.io.IOException ex) {
            System.out.println("IO Errors, Returning zeros: " + ex);
        }

        return numbers;
    }
}
