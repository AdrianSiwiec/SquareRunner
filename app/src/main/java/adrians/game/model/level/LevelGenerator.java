package adrians.game.model.level;

import android.graphics.Color;
import android.graphics.PointF;

import adrians.game.model.gameObject.PhysicalRectangle;

/**
 * Created by pierre on 09/03/16.
 */
public class LevelGenerator {
    public static Level generateLevel(int number) {
        Level level = new Level();
        switch (number) {
            case 1:
                level.addRectangle(new PhysicalRectangle(new PointF(0, 0), new PointF(10000, 20), Color.RED));
                level.addRectangle(new PhysicalRectangle(new PointF(100, 0), new PointF(20, 100), Color.GREEN));
                break;
        }
        return level;
    }
}
