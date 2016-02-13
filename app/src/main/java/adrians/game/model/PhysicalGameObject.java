package adrians.game.model;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;

import java.util.LinkedList;
import java.util.Map;

import adrians.framework.GameMainActivity;
import adrians.framework.util.Painter;
import adrians.framework.util.TouchPointer;

/**
 * Created by pierre on 10/02/16.
 */
public abstract class PhysicalGameObject extends GameObject {
    protected volatile float posX, posY, velX, velY, width, height, rotationAngle;
    protected Bitmap bitmap;
    protected LinkedList<TouchPointer> pointers;
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

    public boolean isInside(TouchPointer ptr) {
        return(ptr.getBeg().x >= posX - width && ptr.getBeg().x <= posX + width &&
                ptr.getBeg().y >= posY - height && ptr.getBeg().y <=posY + height);
    }

    public void onPointerDown(TouchPointer ptr) {
        pointers.add(ptr);
    }

    public void updatePointer(TouchPointer ptr) {
        for(int i=0; i<pointers.size(); i++) {
            if(pointers.get(i).getId() == ptr.getId()) {
                pointers.set(i, ptr);
                break;
            }
        }
    }

    public void onPointerMove() {
        posX += (pointers.getFirst().getCurRel().x - pointers.getFirst().getBegRel().x) * width /2;
        posY += (pointers.getFirst().getCurRel().y - pointers.getFirst().getBegRel().y) * height /2;
        if(pointers.size() >=2) {
            double angle1 = Math.atan2(pointers.get(0).getBegRel().y - pointers.get(1).getBegRel().y,
                    pointers.get(0).getBegRel().x - pointers.get(1).getBegRel().x);
            double angle2 = Math.atan2(pointers.get(0).getCurRel().y - pointers.get(1).getCurRel().y,
                    pointers.get(0).getCurRel().x - pointers.get(1).getCurRel().x);
            rotationAngle = (float) (360f/2/Math.PI * (angle2 - angle1));
        }
    }

    public void onPointerUp(TouchPointer ptr) {
        pointers.remove(ptr);
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
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
}
