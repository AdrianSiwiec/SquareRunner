package adrians.framework.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.concurrent.Callable;

/**
 * Created by pierre on 10/02/16.
 */
public class ToggleButton extends UIButton  {
    private Callable<Integer> action;
    public ToggleButton(int left, int top, int right, int bottom, Bitmap buttonImage, Bitmap buttonDownImage,
                        boolean initialState, Callable<Integer> action) {
        super(left, top, right, bottom, buttonImage, buttonDownImage);
        this.action = action;
        buttonDown = initialState;
    }

    @Override
    public void onTouchDown(int touchX, int touchY) {
        if(butRect.contains(touchX, touchY)) {
            buttonDown ^= true;
            try {
                action.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isToggled() {
        return buttonDown;
    }

    public void setState(boolean state) {
        buttonDown = state;
    }
}
