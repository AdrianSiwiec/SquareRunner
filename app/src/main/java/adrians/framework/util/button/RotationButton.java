package adrians.framework.util.button;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Created by pierre on 15/02/16.
 */
public class RotationButton extends AnalogButton {
    float centeringDistance = 0.2f;
    public RotationButton(PointF pos, PointF size, Bitmap bitmap, PointF buttonSize, Bitmap buttonBitmap) {
        super(pos, size, bitmap, buttonSize, buttonBitmap);
        val.y = -1/1.2f;
    }

    @Override
    public  synchronized void updateButton() {
        if(pointers.size()>0) {
            val = pointers.getFirst().getCurRel();
            float length = val.length();
            val.x /= length * 1.2;
            val.y /= length * 1.2;
            if(val.x > 1 - centeringDistance) {
                val.x = 1;
                val.y = 0;
            }
            if(val.x < -1 + centeringDistance) {
                val.x = -1;
                val.y = 0;
            }
            if(val.y > 1 -centeringDistance) {
                val.y = 1;
                val.x = 0;
            }
            if(val.y < -1 + centeringDistance) {
                val.y = -1;
                val.x = 0;
            }
        }
    }
}
