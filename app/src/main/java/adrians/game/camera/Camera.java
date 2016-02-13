package adrians.game.camera;

import android.graphics.Matrix;
import android.graphics.PointF;
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
    private Matrix matrix, reversedMatrix;
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
        reversedMatrix = new Matrix();
        calculateMatrix();
    }

    public Camera(float posX, float posY, float width, float rotationAngle, int screenWidth, int screenHeight) {
        this(posX, posY, width, screenWidth, screenHeight);
        this.rotationAngle = rotationAngle;
        calculateMatrix();
    }

    public void update(float delta) {
    }

    public PointF getWorldCoords(float x, float y) {
        tmpPoints[0] = x;
        tmpPoints[1] = y;
        reversedMatrix.mapPoints(tmpPoints);
        PointF point = new PointF();
        point.x = tmpPoints[0];
        point.y = tmpPoints[1];
        return point;
    }

    private void calculateMatrix() {
        matrix.reset();
        matrix.preScale(screenWidth / width / 2, screenHeight / height / 2);
        matrix.preTranslate(-posX + width, -posY + height);
        matrix.preRotate(-rotationAngle, posX, posY);
        matrix.invert(reversedMatrix);
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

        g.drawImage(object.getBitmap(), tmpPoints[0] - nW, tmpPoints[1] - nH,
                nW * 2, nH * 2, -rotationAngle + object.getRotationAngle());

    }
}
