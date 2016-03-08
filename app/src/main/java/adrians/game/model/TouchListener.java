package adrians.game.model;

import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 05/03/16.
 */
public class TouchListener extends PhysicalGameObject{
    PointF touchVel, usedVel;
    float scale = 1e3f;
    public TouchListener(PointF pos, PointF size) {
        super(pos, size);
        touchVel = new PointF();
        usedVel = new PointF();
    }


    @Override
    public synchronized void render(Painter g, Camera camera) {}

    @Override
    public synchronized void update(float delta){}

    @Override
    public synchronized void onPointerDown(TouchPointer ptr) {
        super.onPointerDown(ptr);
        if(pointers.size()==1) {
            usedVel.set(0, 0);
        }
    }

    @Override
    public synchronized void onPointerMove() {
        if(pointers.size()>0)
        {
            touchVel.set(pointers.getFirst().getCur().x-pointers.getFirst().getBeg().x,
                    pointers.getFirst().getCur().y-pointers.getFirst().getBeg().y);
        }
    }

    @Override
    public synchronized void onPointerUp(TouchPointer ptr) {
        onPointerMove();
        super.onPointerUp(ptr);
        if(pointers.size()==0) {
            touchVel.set(0, 0);
            usedVel.set(0, 0);
        }
    }
    public float getWantedVelY() {
        float ret=touchVel.y-usedVel.y;
        usedVel.set(usedVel.x, touchVel.y);
        return ret*scale;
    }
    public float getWantedVelX() {
        float ret=touchVel.x-usedVel.x;
        usedVel.set(touchVel.x, usedVel.y);
        return ret*scale;
    }

    public int getPointersSize() {
        return pointers.size();
    }
}
