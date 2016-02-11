package adrians.game.camera;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import adrians.framework.util.Painter;
import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 10/02/16.
 */
public class Camera {
    private float posX, posY, width, height;
    private int screenHeight, screenWidth;
    private int rX, rY, rW, rH;
    private RectF rect;

    public Camera(float posX, float posY, float width, int screenWidth, int screenHeight) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = width * (float)screenHeight / (float) screenWidth;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        rect = new RectF();
        updateRect();
    }

    private void updateRect() {
        rect.set(posX - width, posY - height, posX + width, posY + height);
    }

    public void renderObject(PhysicalGameObject object, Painter g) {
        if(object == null) {
            return;
        }
        RectF oRect = object.getRect();
        if(!RectF.intersects(object.getRect(), rect)){
            return;
        }
        rX = scale(object.getPosX(), rect.left, width*2, screenWidth);
        rY = scale(object.getPosY(), rect.top, height*2, screenHeight);
        rW = scale(object.getPosX()+object.getWidth(), rect.left, width*2, screenWidth) - rX;
        rH = scale(object.getPosY()+object.getHeight(), rect.top, height*2, screenHeight) - rY;
        g.drawImage(object.getBitmap(), rX, rY, rW, rH, object.getRotationAngle());
    }

    private int scale(float position, float beg, float size, int screenSize) {
        return (int) ((position - beg)/(double)(size)*screenSize);
    }
}
