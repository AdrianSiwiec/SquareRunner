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
        g.drawString("FPS: " + getFps(), 10, 100);
    }
}
