package adrians.framework.util.button;

import android.graphics.Bitmap;
import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;
import adrians.game.model.gameObject.PhysicalGameObject;

/**
 * Created by pierre on 15/02/16.
 */
public class Button extends PhysicalGameObject {
    protected Bitmap buttonBitmap;
    protected PointF val, buttonSize;
    public Button(PointF pos, PointF size) {super(pos, size);}
    public Button(PointF pos, PointF size, Bitmap bitmap) {
        super(pos, size, bitmap);
    }
    public Button(PointF pos, PointF size, Bitmap bitmap,
                  PointF buttonSize, Bitmap buttonBitmap) {
       super(pos, size, bitmap);
        this.buttonSize = buttonSize;
        val = new PointF(0, 0);
        this.buttonBitmap = buttonBitmap;
    }

//    @Override
//    public void update(float delta) {
//
//    }

    @Override
    public void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
        camera.renderBitmap(pos.x + size.x*val.x, pos.y + size.y*val.y, buttonSize.x, buttonSize.y, buttonBitmap, g);
    }

    @Override
    public void onPointerMove() {
        updateButton();
    }
    @Override
    public void onPointerDown(TouchPointer ptr) {
        super.onPointerDown(ptr);
        updateButton();
    }

    public void updateButton() {
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
