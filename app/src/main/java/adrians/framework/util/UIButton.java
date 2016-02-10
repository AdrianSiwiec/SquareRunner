package adrians.framework.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by pierre on 10/02/16.
 */
public class UIButton {
    protected Rect butRect;
    protected boolean buttonDown = false;
    protected Bitmap buttonImage, buttonDownImage;

    public UIButton(int left, int top, int right, int bottom, Bitmap buttonImage, Bitmap buttonDownImage) {
        butRect = new Rect(left, top, right, bottom);
        this.buttonImage = buttonImage;
        this.buttonDownImage = buttonDownImage;
    }

    public void render(Painter g) {
        Bitmap currentButtonImage = buttonDown ? buttonDownImage : buttonImage;
        g.drawImage(currentButtonImage, butRect.left, butRect.top, butRect.width(), butRect.height());
    }

    public void onTouchDown(int touchX, int touchY) {
        if(butRect.contains(touchX, touchY)) {
            buttonDown = true;
        } else {
            buttonDown = false;
        }
    }

    public void cancel() {
        buttonDown = false;
    }

    public boolean isPressed(int touchX, int touchY) {
        return buttonDown && butRect.contains(touchX, touchY);
    }

    public Rect getRect() {
        return butRect;
    }
}
