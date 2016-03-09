package adrians.game.camera;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.game.model.gameObject.PhysicalGameObject;

/**
 * Created by pierre on 10/02/16.
 */
public class Camera {
    private float posX, posY, width, height, rotationAngle;
    private int screenHeight, screenWidth;
    private Matrix matrix, reversedMatrix;
    private float[] tmpPoints;
    private float nW, nH;
    private PointF tmpPoint = new PointF();
    private PhysicalGameObject followedObject;
    private float ropeLength, followSpeed;
    public enum Mode {
        FIXED, FOLLOW, FOLLOW_LOOSELY
    }
    Mode mode = Mode.FIXED;
    public Camera(float posX, float posY, float width, int screenWidth, int screenHeight) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        rotationAngle = 0;
        tmpPoints = new float[2];
        nW = nH = 0;
        calculateHeight();
        matrix = new Matrix();
        reversedMatrix = new Matrix();
        calculateMatrix();
    }

    public Camera(float posX, float posY, float width, float rotationAngle, int screenWidth, int screenHeight) {
        this(posX, posY, width, screenWidth, screenHeight);
        this.rotationAngle = rotationAngle;
        calculateMatrix();
    }

    public void setModeFollow(PhysicalGameObject obj) {
        followedObject = obj;
        posX = followedObject.getPos().x;
        posY = followedObject.getPos().y;
        calculateMatrix();
        mode = Mode.FOLLOW;
    }

    public void setModeFollowLoosely(PhysicalGameObject obj, float ropeLength, float followSpeed) {
        followedObject = obj;
        posX = followedObject.getPos().x;
        posY = followedObject.getPos().y;
        this.ropeLength = ropeLength;
        this.followSpeed = followSpeed;
        calculateMatrix();
        mode = Mode.FOLLOW_LOOSELY;
    }


    public void update(float delta) {
        switch (mode) {
            case FIXED:
                break;
            case FOLLOW:
                posX = followedObject.getPos().x;
                posY = followedObject.getPos().y;
                calculateMatrix();
                break;
            case FOLLOW_LOOSELY:
                    posX += (followedObject.getPos().x - posX)*delta*followSpeed;
                    posY += (followedObject.getPos().y - posY)*delta*followSpeed;
                break;
        }
    }

    public synchronized PointF getWorldCoords(float x, float y) {
        tmpPoints[0] = x;
        tmpPoints[1] = y;
        reversedMatrix.mapPoints(tmpPoints);
        tmpPoint.x = tmpPoints[0];
        tmpPoint.y = tmpPoints[1];
        return tmpPoint;
    }

    public synchronized PointF getScreenCoords(float x, float y) {
        tmpPoints[0] = x;
        tmpPoints[1] = y;
        matrix.mapPoints(tmpPoints);
        tmpPoint.x = tmpPoints[0];
        tmpPoint.y = tmpPoints[1];
        return tmpPoint;
    }

    public synchronized float getWorldDistance(float r) {
        return reversedMatrix.mapRadius(r);
    }

    public synchronized float getScreenDistance(float r) {
        return matrix.mapRadius(r);
    }

    private synchronized void calculateMatrix() {
        matrix.reset();
        matrix.preScale(screenWidth / width / 2, screenHeight / height / 2);
        matrix.preTranslate(-posX + width, -posY + height);
        matrix.preRotate(-rotationAngle, posX, posY);
        matrix.invert(reversedMatrix);
    }

    private synchronized void calculateHeight() {
        height = width * screenHeight/screenWidth;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
        calculateMatrix();
    }

    public synchronized void setWidth(float width) {
        this.width = width;
        calculateHeight();
        calculateMatrix();

    }

    public synchronized void renderObject(PhysicalGameObject object, Painter g) {
        if (object == null) {
            return;
        }
        tmpPoints[0] = object.getPos().x;
        tmpPoints[1] = object.getPos().y;

        matrix.mapPoints(tmpPoints);
        nW = matrix.mapRadius(object.getSize().x);
        nH = matrix.mapRadius(object.getSize().y);

        if(object.getBitmap()!=null) {
            g.drawImage(object.getBitmap(), tmpPoints[0] - nW, tmpPoints[1] - nH,
                    nW * 2, nH * 2, -rotationAngle + object.getRotationAngle());
        } else {
            g.fillRect(tmpPoints[0]-nW, tmpPoints[1]-nH, nW*2, nH*2,
                    -rotationAngle+object.getRotationAngle(), object.getColor());
        }

    }

    public synchronized void renderBitmap(float posX, float posY, float width, float height, Bitmap bitmap, Painter g) {
        tmpPoints[0] = posX;
        tmpPoints[1] = posY;
        matrix.mapPoints(tmpPoints);
        nW = matrix.mapRadius(width);
        nH = matrix.mapRadius(height);
        g.drawImage(bitmap, tmpPoints[0] - nW, tmpPoints[1] - nH,
                nW * 2, nH * 2);
    }

    public void move(float x, float y) {
        posX +=x;
        posY +=y;
        calculateMatrix();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
