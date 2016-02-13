package adrians.game.state;

import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;

import adrians.framework.util.Painter;
import adrians.framework.GameMainActivity;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;
import adrians.game.model.ExampleGameObject;
import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 06/02/16.
 */
public abstract class State {
    protected PhysicalGameObject objectList[];
    protected Camera camera;
    protected HashMap<Integer, TouchPointer> pointers;
    protected HashMap<PhysicalGameObject, Integer> objectsPointedAt;

    public State() {
        pointers = new HashMap<>();
        objectsPointedAt = new HashMap<>();
    }

    public void setCurrentState(State newState) {
        GameMainActivity.sGame.setCurrentState(newState);
    }
    public abstract void init();

    public void update(float delta) {
        for(int i=0; i<objectList.length; i++) {
            objectList[i].update(delta);
        }
    }
    public void render(Painter g) {
        for(int i=0; i<objectList.length; i++) {
            camera.renderObject(objectList[i], g);
        }
    }
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(e.getActionMasked() == MotionEvent.ACTION_DOWN || e.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int index;
            if(e.getActionMasked() == MotionEvent.ACTION_DOWN) {
                index = 0;
            } else {
                index = e.getActionIndex();
            }

            TouchPointer ptr = new TouchPointer(camera.getWorldCoords(e.getX(index), e.getY(index)), e.getPointerId(index), null);
            Log.d("DOWN", e.getPointerId(index) + "");
            for(int i=objectList.length-1; i>=0; i--) {
                if(objectList[i].isInside(ptr)) {
                    ptr.setOriginObject(objectList[i]);
                    break;
                }
            }
            if(ptr.getOriginObject() != null) {
                if(objectsPointedAt.containsKey(ptr.getOriginObject())) {
                    objectsPointedAt.put(ptr.getOriginObject(), objectsPointedAt.get(ptr.getOriginObject()) + 1);
                } else {
                    objectsPointedAt.put(ptr.getOriginObject(), 1);
                }
                pointers.put(ptr.getId(), ptr);
                ptr.getOriginObject().onPointerDown(ptr);
            }
            return true;
        } else if(e.getActionMasked() == MotionEvent.ACTION_MOVE) {
            for(int i=0; i<e.getPointerCount(); i++) {
                if(pointers.get(e.getPointerId(i)) == null) {
                    continue;
                }
                pointers.get(e.getPointerId(i)).update(camera.getWorldCoords(e.getX(i), e.getY(i)));
                pointers.get(e.getPointerId(i)).getOriginObject().updatePointer(pointers.get(e.getPointerId(i)));
            }
            for(PhysicalGameObject o: objectsPointedAt.keySet()) {
                o.onPointerMove();
            }

            return true;
        } else if(e.getActionMasked() == MotionEvent.ACTION_UP || e.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            int index;
            if(e.getActionMasked() == MotionEvent.ACTION_UP) {
                index = 0;
            } else {
                index = e.getActionIndex();
            }
            TouchPointer ptr = pointers.get(e.getPointerId(index));
            if(ptr != null) {
                ptr.getOriginObject().onPointerUp(ptr);
                if(objectsPointedAt.get(ptr.getOriginObject()) == 1) {
                    objectsPointedAt.remove(ptr.getOriginObject());
                } else {
                    objectsPointedAt.put(ptr.getOriginObject(), objectsPointedAt.get(ptr.getOriginObject()) -1);
                }
                pointers.remove(ptr.getId());
            }
            return true;
        }
        return false;
    }

    public void onResume() {}
    public void onPause() {}

}
