package adrians.game.model;

import android.graphics.Bitmap;
import android.graphics.PointF;

import java.util.LinkedList;

import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 10/02/16.
 */
public abstract class PhysicalGameObject extends GameObject {
    protected PointF pos, vel, size;
    protected float rotationAngle;
    protected Bitmap bitmap;
    protected LinkedList<TouchPointer> pointers;
    public PhysicalGameObject(PointF pos, PointF size) {
        this.pos = pos;
        this.size = size;
        vel = new PointF(0, 0);
        bitmap = null;
        pointers = new LinkedList<>();
    }

    public void update(float delta){}

    public synchronized void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
    }

    public PhysicalGameObject(PointF pos, PointF size, Bitmap bitmap) {
        this(pos, size);
        this.bitmap = bitmap;
        this.rotationAngle = 0;
    }

    public PhysicalGameObject(PointF pos, PointF size, float rotationAngle, Bitmap bitmap) {
        this(pos, size);
        this.bitmap = bitmap;
        this.rotationAngle = rotationAngle;
    }

    public synchronized boolean isInside(TouchPointer ptr) {
        return(ptr.getBeg().x >= pos.x - size.x && ptr.getBeg().x <= pos.x + size.x &&
                ptr.getBeg().y >= pos.y - size.y && ptr.getBeg().y <=pos.y + size.y);
    }

    public synchronized void onPointerDown(TouchPointer ptr) {
        pointers.add(ptr);
    }

    public synchronized void updatePointer(TouchPointer ptr) {
        for(int i=0; i<pointers.size(); i++) {
            if(pointers.get(i).getId() == ptr.getId()) {
                pointers.set(i, ptr);
                break;
            }
        }
    }

    public void onPointerMove() {

    }

    public synchronized void onPointerUp(TouchPointer ptr) {
        pointers.remove(ptr);
    }

    public synchronized Bitmap getBitmap() {
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
}
