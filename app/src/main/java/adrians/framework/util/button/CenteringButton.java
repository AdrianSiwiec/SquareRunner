package adrians.framework.util.button;

import android.graphics.Bitmap;
import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 15/02/16.
 */
public class CenteringButton extends AnalogButton{
    public long visibleNanos, vanishingNanos, sinceLastActionNanos;
    public int alpha;
    public CenteringButton(float x, float y, float width, float height, Bitmap bitmap,
                           float buttonWidth, float buttonHeight, Bitmap buttonBitmap,
                           float visibleTime) {
        super(x, y, width, height, bitmap, buttonWidth, buttonHeight, buttonBitmap);
        this.visibleNanos = (long) (visibleTime * 1e9f);
        vanishingNanos = (long) 1e10;
        sinceLastActionNanos = 0;
    }

    @Override
    public  synchronized void update(float delta) {
        if(pointers.size()==0) {
            val.x *= 0.01 / delta;
            val.y *= 0.01 / delta;
            sinceLastActionNanos += delta*1e9f;
        }
        if(sinceLastActionNanos < visibleNanos) {
            alpha = 255;
        } else {
            alpha-= (255*delta*vanishingNanos/1e9f);
            alpha = Math.max(0, alpha);
        }
    }

    @Override public synchronized void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
        PointF coords = camera.getScreenCoords(posX + val.x*width, posY + val.y*height);
        float nW = camera.getScreenDistance(buttonSize.x);
        float nH = camera.getScreenDistance(buttonSize.y);
        g.drawImageTransparent(buttonBitmap, coords.x - nW , coords.y - nH, nW*2, nH*2, alpha);
    }

    @Override
    public synchronized void onPointerMove() {
        super.onPointerMove();
        sinceLastActionNanos = 0;
    }
    @Override
    public synchronized void onPointerDown(TouchPointer ptr) {
        super.onPointerDown(ptr);
        sinceLastActionNanos = 0;
    }
}
