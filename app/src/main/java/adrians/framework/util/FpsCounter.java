package adrians.framework.util;

import android.graphics.Color;
import android.graphics.Typeface;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by pierre on 11/02/16.
 */
public class FpsCounter {
    private static LinkedList<Long> updates = new LinkedList<Long>();
    private static String[] stringTable = new String[120];
    public static void update(long updateNano) {
        updates.add(updateNano);
        while(!updates.isEmpty() && updateNano - updates.peekFirst() > 1e9) {
            updates.removeFirst();
        }
    }
    public static int getFps() {
        return updates.size();
    }

    public static void printFps(Painter g) {
        g.setFont(Typeface.SANS_SERIF, 80);
        g.setColor(Color.BLACK);
        g.drawString(fpsString(getFps()), 10, 100);
    }

    private static String fpsString(int n) {
        if(stringTable[n] == null) {
            stringTable[n] = "FPS: " + n;
        }
        return stringTable[n];
    }
}
