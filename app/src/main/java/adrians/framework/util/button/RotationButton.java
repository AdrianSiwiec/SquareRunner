package adrians.framework.util.button;

import android.graphics.Bitmap;
import android.graphics.PointF;

import java.lang.Math;

/**
 * Created by pierre on 15/02/16.
 */
public class RotationButton extends AnalogButton {
    public RotationButton(float x, float y, float width, float height, Bitmap bitmap, float buttonWidth, float buttonHeight, Bitmap buttonBitmap) {
        super(x, y, width, height, bitmap, buttonWidth, buttonHeight, buttonBitmap);
    }

    @Override
    public  synchronized void updateButton() {
        if(pointers.size()>0) {
            val = pointers.getFirst().getCurRel();
            val.x = Math.min(1, val.x);
            val.y = Math.max(-1, val.y);
            val.y = Math.min(1, val.y);
            val.y = Math.max(-1, val.y);
            float length = val.length();
            if(length != 0) {
                val.x /= length * 1.2;
                val.y /= length * 1.2;
            }
        }
    }
}
