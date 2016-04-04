package adrians.framework.util.button;

import android.graphics.Bitmap;
import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;
import adrians.game.model.gameObject.PhysicalGameObject;

/**
 * Created by pierre on 17/03/16.
 */
public class PushButton extends Button{
    PhysicalGameObject pressed;
    private boolean isPressed = false, isReleased = false;
    public PushButton(PointF pos, PointF size, Bitmap bitmap, Bitmap pressedBitmap) {
        super(pos, size, bitmap);
        pressed = new Button(pos, size, pressedBitmap);
    }

    public PushButton(PointF pos, PointF size) {
        super(pos, size);
    }

    @Override
    public void render(Painter g, Camera camera) {
        if(isPressed) {
            camera.renderObject(pressed, g);
        } else {
            camera.renderObject(this, g);
        }
    }

    @Override
    public void onPointerDown(TouchPointer ptr) {
        super.onPointerDown(ptr);
        isPressed = true;
    }

    @Override
    public synchronized void onPointerUp(TouchPointer ptr) {
        super.onPointerUp(ptr);
        isReleased = true;
    }

    public boolean gotPushed() {
        if(isReleased) {
            isReleased = false;
            return true;
        } else {
            return false;
        }
    }
}
