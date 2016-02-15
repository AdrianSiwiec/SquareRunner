package adrians.game.model;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;

import adrians.framework.GameMainActivity;
import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 10/02/16.
 */
public abstract class PhysicalGameObject extends GameObject {
    protected volatile float posX, posY, velX, velY, width, height, rotationAngle; //TODO Migrate to Point
    protected Bitmap bitmap;
    protected LinkedList<TouchPointer> pointers;
    protected boolean isPosRelative;
    public PhysicalGameObject() {}
    public PhysicalGameObject(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        velX = 0;
        velY = 0;
        bitmap = null;
        pointers = new LinkedList<TouchPointer>();
    }

    public void update(float delta){}

    public synchronized void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
    }

    public PhysicalGameObject(float posX, float posY, float width, float height, Bitmap bitmap) {
        this(posX, posY, width, height);
        this.bitmap = bitmap;
        this.rotationAngle = 0;
    }

    public PhysicalGameObject(float posX, float posY, float width, float height, float rotationAngle, Bitmap bitmap) {
        this(posX, posY, width, height);
        this.bitmap = bitmap;
        this.rotationAngle = rotationAngle;
    }

    public synchronized boolean isInside(TouchPointer ptr) {
        return(ptr.getBeg().x >= posX - width && ptr.getBeg().x <= posX + width &&
                ptr.getBeg().y >= posY - height && ptr.getBeg().y <=posY + height);
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

    public synchronized float getPosX() {
        return posX;
    }

    public synchronized void setPosX(float posX) {
        this.posX = posX;
    }

    public synchronized float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public synchronized float getWidth() {
        return width;
    }

    public synchronized float getHeight() {
        return height;
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
}
