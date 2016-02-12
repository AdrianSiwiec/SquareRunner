package adrians.game.camera;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import adrians.framework.util.Painter;
import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 10/02/16.
 */
public class Camera {
    private float posX, posY, width, height, rotationAngle;
    private int screenHeight, screenWidth;
    private Matrix matrix;
    private float[] tmpPoints;
    private float nW, nH;
    public Camera(float posX, float posY, float width, int screenWidth, int screenHeight) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        rotationAngle = 0;
        tmpPoints = new float[2];
        nW = nH = 0;
        setHeight();
        matrix = new Matrix();
        calculateMatrix();
    }

    public Camera(float posX, float posY, float width, float rotationAngle, int screenWidth, int screenHeight) {
        this(posX, posY, width, screenWidth, screenHeight);
        this.rotationAngle = rotationAngle;
        calculateMatrix();
    }

    public void update(float delta) {
        //posX-=30*delta;
        calculateMatrix();
    }

    private void calculateMatrix() {
        matrix.reset();
        matrix.preScale(screenWidth / width / 2, screenWidth / width / 2);
        matrix.preTranslate(-posX + width, -posY + height);
        matrix.preRotate(-rotationAngle, posX, posY);
    }

    private void setHeight() {
        height = width * screenHeight/screenWidth;
    }

    public void renderObject(PhysicalGameObject object, Painter g) {
        if (object == null) {
            return;
        }
        tmpPoints[0] = object.getPosX();
        tmpPoints[1] = object.getPosY();

        matrix.mapPoints(tmpPoints);
        nW = matrix.mapRadius(object.getWidth());
        nH = matrix.mapRadius(object.getHeight());

        g.drawImage(object.getBitmap(), (int) (tmpPoints[0] - nW), (int) (tmpPoints[1] - nH),
                (int) nW * 2, (int) nH * 2, -rotationAngle + object.getRotationAngle());

    }
}
