package adrians.framework.util;

import java.util.Random;

/**
 * Created by pierre on 07/02/16.
 */
public class MathUtil {
    private static Random rand = new Random();
    public static int getRandIntBetween(int lowerBound, int upperBound) {
        return rand.nextInt(upperBound-lowerBound) + lowerBound;
    }
    public static int getRandInt(int upperBound) {
        return rand.nextInt(upperBound);
    }
    public static boolean isBetween(float x, float beg, float end) {
        return (x >= beg && x <= end);
    }
}
