package adrians.game.model.gameObject;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.LinkedList;
import java.util.Vector;

import adrians.framework.util.Caller;
import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 10/02/16.
 */
public class PhysicalGameObject extends GameObject {
    protected PointF pos, vel, size;
    protected float rotationAngle;
    protected RectF rectangle;
    protected Bitmap bitmap;
    protected int color;
    protected volatile LinkedList<TouchPointer> pointers;
    protected Vector<MovementHolder> movements = new Vector<>();
    public PhysicalGameObject(PointF pos, PointF size) {
        this.pos = pos;
        this.size = size;
        rectangle = new RectF();
        updateRectangle();
        vel = new PointF(0, 0);
        bitmap = null;
        pointers = new LinkedList<>();
        this.rotationAngle = 0;
    }

    public void update(float delta){
        updateMovements(delta);
        updateRectangle();
    }

    public void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
    }

    public PhysicalGameObject(PointF pos, PointF size, int color) {
        this(pos, size);
        this.color = color;
    }
    public PhysicalGameObject(PointF pos, PointF size, Bitmap bitmap) {
        this(pos, size);
        this.bitmap = bitmap;
    }

    public PhysicalGameObject(PointF pos, PointF size, float rotationAngle, Bitmap bitmap) {
        this(pos, size);
        this.bitmap = bitmap;
        this.rotationAngle = rotationAngle;
    }

    public boolean isInside(TouchPointer ptr) {
        return(ptr.getBeg().x >= pos.x - size.x && ptr.getBeg().x <= pos.x + size.x &&
                ptr.getBeg().y >= pos.y - size.y && ptr.getBeg().y <=pos.y + size.y);
    }

    public void onPointerDown(TouchPointer ptr) {
        pointers.add(ptr);
    }

//    public synchronized void updatePointer(TouchPointer ptr) {
//        for(int i=0; i<pointers.size(); i++) {
//            if(pointers.get(i).getId() == ptr.getId()) {
//                pointers.set(i, ptr);
//                break;
//            }
//        }
//    }

    public void onPointerMove() {}

    public void onPointerUp(TouchPointer ptr) {
        pointers.remove(ptr);
    }

    protected void updateRectangle() {
        rectangle.set(pos.x - size.x, pos.y - size.y, pos.x + size.x, pos.y + size.y);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {

        this.rotationAngle = rotationAngle;
    }

    public PointF getPos() {
        return pos;
    }

    public void setPos(PointF pos) {
        this.pos = pos;
    }

    public PointF getVel() {
        return vel;
    }

    public void setVel(PointF vel) {
        this.vel = vel;
    }

    public PointF getSize() {
        return size;
    }

    public void setSize(PointF size) {

        this.size = size;
    }
    public RectF getRectangle() {
        return rectangle;
    }

    public int getColor() {
        return color;
    }

    public void moveSmoothly(PointF object, PointF dest, float durationTime) {
        movements.add(new MovementHolder(object, dest, durationTime));
    }
    public void moveSmoothly(PointF object, PointF dest, float durationTime, Caller caller) {
        movements.add(new MovementHolder(object, dest, durationTime, caller));
    }
    public class MovementHolder {
        PointF object, dest, orig;
        float durationTime;
        float elapsedTime;
        Caller caller;

        public MovementHolder(PointF object, PointF dest, float durationTime) {
            this.object = object;
            this.dest = dest;
            this.durationTime = durationTime;
            orig=new PointF(object.x, object.y);
            elapsedTime=0;
            caller = null;
        }

        public MovementHolder(PointF object, PointF dest, float durationTime, Caller caller) {
            this(object, dest, durationTime);
            this.caller = caller;
        }

        private void deleteItself() {
            movements.remove(this);
            if(caller!=null) {
                caller.call();
            }
//            movements.addLast(new MovementHolder(object, orig, durationTime)); //looks rad
        }

        private boolean update(float delta) {
            elapsedTime+=delta;
            if(elapsedTime>=durationTime) {
                setSmooth(object, orig, dest, 1);
                deleteItself();
                return false;
            } else {
                setSmooth(object, orig, dest, elapsedTime / durationTime);
                return true;
            }
        }

        public void setSmooth(PointF object, PointF orig, PointF dest, float arg) {
            object.set(calculateSmooth(orig.x, dest.x, arg), calculateSmooth(orig.y, dest.y, arg));
        }

        public float calculateSmooth(float orig, float dest, float arg) {
            double t= (Math.sin((arg-0.5)*Math.PI)+1)/2;
            return (float) (orig*(1-t)+dest*t);
        }
    }

    protected void updateMovements(float delta) {
        for(int i=0; i<movements.size(); i++) {
            if(!movements.elementAt(i).update(delta)) {
                i--;
            }
        }
    }
}
