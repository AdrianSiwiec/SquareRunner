package adrians.game.camera;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;

import adrians.framework.util.Painter;
import adrians.game.model.gameObject.PhysicalGameObject;

/**
 * Created by pierre on 10/02/16.
 */
public class Camera extends PhysicalGameObject{
    private float rotationAngle;
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
    public Camera(float posX, float posY, float sizeX, int screenWidth, int screenHeight) {
        super(new PointF(posX, posY), new PointF(sizeX, sizeX*screenHeight/screenWidth));
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

    public Camera(float posX, float posY, float sizeX, float rotationAngle, int screenWidth, int screenHeight) {
        this(posX, posY, sizeX, screenWidth, screenHeight);
        this.rotationAngle = rotationAngle;
        calculateMatrix();
    }

    public void setModeFollow(PhysicalGameObject obj) {
        followedObject = obj;
        pos.x = followedObject.getPos().x;
        pos.y = followedObject.getPos().y;
        calculateMatrix();
        mode = Mode.FOLLOW;
    }

    public void setModeFollowLoosely(PhysicalGameObject obj, float ropeLength, float followSpeed) {
        followedObject = obj;
        pos.x = followedObject.getPos().x;
        pos.y = followedObject.getPos().y;
        this.ropeLength = ropeLength;
        this.followSpeed = followSpeed;
        calculateMatrix();
        mode = Mode.FOLLOW_LOOSELY;
    }

    public void setModeFixed() {
        mode = Mode.FIXED;
    }


    public void update(float delta) {
        super.update(delta);
        switch (mode) {
            case FIXED:
                break;
            case FOLLOW:
                pos.x = followedObject.getPos().x;
                pos.y = followedObject.getPos().y;
                calculateMatrix();
                break;
            case FOLLOW_LOOSELY:
                pos.x += (followedObject.getPos().x - pos.x)*delta*followSpeed;
                pos.y += (followedObject.getPos().y - pos.y)*delta*followSpeed;
                calculateMatrix();
                break;
        }
    }

    @Override
    protected void updateMovements(float delta) {
        super.updateMovements(delta);
        calculateMatrix();
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
        matrix.preScale(screenWidth / size.x / 2, screenHeight / size.y / 2);
        matrix.preTranslate(-pos.x + size.x, -pos.y + size.y);
        matrix.preRotate(-rotationAngle, pos.x, pos.y);
        matrix.invert(reversedMatrix);
    }

    private synchronized void calculateHeight() {
        size.y = size.x * screenHeight/screenWidth;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
        calculateMatrix();
    }

    public synchronized void setWidth(float sizeX) {
        this.size.x = sizeX;
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

    public synchronized void renderObjectWithColor(PhysicalGameObject object, Painter g, int color) {
        if(object==null) return;
        tmpPoints[0]=object.getPos().x;
        tmpPoints[1]=object.getPos().y;
        matrix.mapPoints(tmpPoints);
        nW=matrix.mapRadius(object.getSize().x);
        nH=matrix.mapRadius(object.getSize().y);
        g.fillRect(tmpPoints[0]-nW, tmpPoints[1]-nH, nW*2, nH*2,
                -rotationAngle+object.getRotationAngle(), color);
    }

    public synchronized void renderBitmap(float posX, float posY, float sizeX, float sizeY, Bitmap bitmap, Painter g) {
        tmpPoints[0] = posX;
        tmpPoints[1] = posY;
        matrix.mapPoints(tmpPoints);
        nW = matrix.mapRadius(sizeX);
        nH = matrix.mapRadius(sizeY);
        g.drawImage(bitmap, tmpPoints[0] - nW, tmpPoints[1] - nH,
                nW * 2, nH * 2);
    }

    public void move(float x, float y) {
        pos.x +=x;
        pos.y +=y;
        calculateMatrix();
    }

    public float getWidth() {
        return size.x;
    }

    public float getHeight() {
        return size.y;
    }
}
