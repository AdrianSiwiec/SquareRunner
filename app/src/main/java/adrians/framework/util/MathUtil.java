package adrians.framework.util;

import android.graphics.PointF;

import java.util.Random;

/**
 * Created by pierre on 07/02/16.
 */
public class MathUtil {
    public enum Directions {
        UP, DOWN, LEFT, RIGHT
    }
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

    public static float distance(PointF a, PointF b) {
        return (float) Math.sqrt( (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y) );
    }

    public static PointF getPointBetween(PointF p, PointF q, float ratio) {
        return new PointF(p.x + (q.x-p.x)*ratio, p.y+(q.y-p.y)*ratio); //TODO make faster

    }
}
