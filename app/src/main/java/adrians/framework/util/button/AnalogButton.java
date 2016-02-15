package adrians.framework.util.button;

import android.graphics.Bitmap;
import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;
import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 15/02/16.
 */
public class AnalogButton extends PhysicalGameObject {
    protected Bitmap buttonBitmap;
    protected PointF val, buttonSize;
    public AnalogButton(float x, float y, float width, float height, Bitmap bitmap,
                              float buttonWidth, float buttonHeight, Bitmap buttonBitmap) {
       super(x, y, width, height, bitmap);
        buttonSize = new PointF(buttonWidth, buttonHeight);
        val = new PointF(0, 0);
        this.buttonBitmap = buttonBitmap;
    }

    @Override
    public synchronized void update(float delta) {

    }

    @Override
    public synchronized void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
        camera.renderBitmap(posX + width*val.x, posY + height*val.y, buttonSize.x, buttonSize.y, buttonBitmap, g);
    }

    @Override
    public synchronized void onPointerMove() {
        updateButton();
    }
    @Override
    public synchronized void onPointerDown(TouchPointer ptr) {
        super.onPointerDown(ptr);
        updateButton();
    }

    public  synchronized void updateButton() {
        if(pointers.size()>0) {
            val = pointers.getFirst().getCurRel();
            val.x = Math.min(1, val.x);
            val.x = Math.max(-1, val.x);
            val.y = Math.min(1, val.y);
            val.y = Math.max(-1, val.y);
        }
    }

    public PointF getVal() {
        return val;
    }
}
