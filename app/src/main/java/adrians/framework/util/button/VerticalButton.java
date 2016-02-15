package adrians.framework.util.button;

import android.graphics.Bitmap;

/**
 * Created by pierre on 15/02/16.
 */
public class VerticalButton extends AnalogButton {
    public VerticalButton(float x, float y, float width, float height, Bitmap bitmap, float buttonWidth, float buttonHeight, Bitmap buttonBitmap) {
        super(x, y, width, height, bitmap, buttonWidth, buttonHeight, buttonBitmap);
    }

    @Override
    public  synchronized void updateButton() {
        if(pointers.size()>0) {
            val.y = pointers.getFirst().getCurRel().y;
            val.y = Math.min(1, val.y);
            val.y = Math.max(-1, val.y);
        }
    }
}
