package adrians.game.model;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * Created by pierre on 10/02/16.
 */
public abstract class PhysicalGameObject extends GameObject {
    protected float posX, posY, velX, velY, width, height, rotationAngle;
    protected Bitmap bitmap;
    public PhysicalGameObject() {}
    public PhysicalGameObject(float posX, float posY, float width, float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        velX = 0;
        velY = 0;
        bitmap = null;
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
