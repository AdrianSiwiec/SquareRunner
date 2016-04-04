package adrians.game.model;

import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;
import adrians.game.model.gameObject.PhysicalGameObject;

/**
 * Created by pierre on 05/03/16.
 */
public class TouchListener extends PhysicalGameObject {
    PointF touchVel, usedVel;
    boolean jump = false;
    float scale = 7e3f;
    public TouchListener(PointF pos, PointF size) {
        super(pos, size);
        touchVel = new PointF();
        usedVel = new PointF();
    }


    @Override
    public synchronized void render(Painter g, Camera camera) {}

    @Override
    public synchronized void update(float delta){
    }

    @Override
    public synchronized boolean isInside(TouchPointer ptr) {
        return true;
    }

    @Override
    public synchronized void onPointerDown(TouchPointer ptr) {
        super.onPointerDown(ptr);
        if(pointers.size()==1) {
            usedVel.set(0, 0);
        }
        if(pointers.size()==2) {
            jump=true;
        }
    }

    @Override
    public synchronized void onPointerMove() {
        if(pointers.size()>0)
        {
            touchVel.set(pointers.getFirst().getCurRel().x-pointers.getFirst().getBegRel().x,
                    pointers.getFirst().getCurRel().y-pointers.getFirst().getBegRel().y);
            normalizeWithMax(touchVel);
        }
    }

    private void normalizeWithMax(PointF point) {
        point.x = Math.max(point.x, -1);
        point.x = Math.min(point.x, 1);
        point.y = Math.max(point.y, -1);
        point.y = Math.min(point.y, 1);
    }

    @Override
    public synchronized void onPointerUp(TouchPointer ptr) {
        onPointerMove();
        super.onPointerUp(ptr);
        if(pointers.size()==0) {
            touchVel.set(0, 0);
            usedVel.set(0, 0);
        } if(pointers.size()<2) {
            jump=false;
        }
    }
    public float getWantedVelY() {
//        if(Math.abs(touchVel.y)*5 >= Math.abs(touchVel.x)) {
//            float ret = touchVel.y - usedVel.y;
//            usedVel.set(usedVel.x, touchVel.y);
//            return ret * scale;
//        } else {
//            return 0;
//        }
        return 0;
    }
    public float getWantedVelX() {
        if(Math.abs(touchVel.x)*5 >= Math.abs(touchVel.y)) {
            float ret = touchVel.x - usedVel.x;
            usedVel.set(touchVel.x, usedVel.y);
            return ret * scale;
        } else {
            return 0;
        }
    }

    public int getPointersSize() {

        return pointers.size();
    }


    public boolean getWantedJump() {
        if(jump) {
            jump=false;
            return true;
        } else {
            return false;
        }

    }
}
