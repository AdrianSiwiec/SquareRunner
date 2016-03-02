package adrians.game.state;

import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Vector;

import adrians.framework.util.Painter;
import adrians.framework.GameMainActivity;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;
import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 06/02/16.
 */
public abstract class State {
    protected Vector<PhysicalGameObject> worldObjects, fixedObjects;
    protected Camera worldCamera, fixedCamera;
    protected HashMap<Integer, TouchPointer> pointers;
    protected HashMap<PhysicalGameObject, Integer> objectsPointedAt;

    public State() {
        worldObjects = new Vector<>();
        fixedObjects = new Vector<>();
        pointers = new HashMap<>();
        objectsPointedAt = new HashMap<>();
        worldCamera = new Camera(0, 0, 100, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        fixedCamera = new Camera(0, 0, 100, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
    }

    public void update(float delta) {
        fixedCamera.update(delta);
        for(int i=0; i< fixedObjects.size(); i++) {
            fixedObjects.get(i).update(delta);
        }
        worldCamera.update(delta);
        for(int i=0; i< worldObjects.size(); i++) {
            worldObjects.get(i).update(delta);
        }
    }
    public void render(Painter g) {
        for(int i=0; i< worldObjects.size(); i++) {
            worldObjects.get(i).render(g, worldCamera);
        }
        for(int i=0; i< fixedObjects.size(); i++) {
            fixedObjects.get(i).render(g, fixedCamera);
        }
    }
    public synchronized boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(e.getActionMasked() == MotionEvent.ACTION_DOWN || e.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int index;
            if(e.getActionMasked() == MotionEvent.ACTION_DOWN) {
                index = 0;
            } else {
                index = e.getActionIndex();
            }

            TouchPointer ptr = new TouchPointer(fixedCamera.getWorldCoords(e.getX(index), e.getY(index)), e.getPointerId(index), null);
            ptr.setIsWorld(false);
            for(int i= fixedObjects.size() -1; i>=0; i--) {
                if(fixedObjects.get(i).isInside(ptr)) {
                    ptr.setOriginObject(fixedObjects.get(i));
                    break;                }
            }
            if(ptr.getOriginObject()==null) {
                ptr.setIsWorld(true);
                ptr.setBeg(worldCamera.getWorldCoords(e.getX(index), e.getY(index)));
                for (int i = worldObjects.size() - 1; i >= 0; i--) {
                    if (worldObjects.get(i).isInside(ptr)) {
                        ptr.setOriginObject(worldObjects.get(i));
                        break;
                    }
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
                if(pointers.get(e.getPointerId(i)).isWorld()) {
                    pointers.get(e.getPointerId(i)).update(worldCamera.getWorldCoords(e.getX(i), e.getY(i)));
                    pointers.get(e.getPointerId(i)).getOriginObject().updatePointer(pointers.get(e.getPointerId(i)));
                } else {
                    pointers.get(e.getPointerId(i)).update(fixedCamera.getWorldCoords(e.getX(i), e.getY(i)));
                    pointers.get(e.getPointerId(i)).getOriginObject().updatePointer(pointers.get(e.getPointerId(i)));
                }
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
