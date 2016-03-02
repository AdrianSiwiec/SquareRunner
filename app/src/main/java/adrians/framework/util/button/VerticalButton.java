package adrians.framework.util.button;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Created by pierre on 15/02/16.
 */
public class VerticalButton extends AnalogButton {
    public VerticalButton(PointF pos, PointF size, Bitmap bitmap, PointF buttonSize, Bitmap buttonBitmap) {
        super(pos, size, bitmap, buttonSize, buttonBitmap);
    }

    @Override
    public void updateButton() {
        if(pointers.size()>0) {
            val.y = pointers.getFirst().getCurRel().y;
            val.y = Math.min(1, val.y);
            val.y = Math.max(-1, val.y);
        }
    }
}
