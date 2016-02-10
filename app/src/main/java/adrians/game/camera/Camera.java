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
    private int ScreenHeight, ScreenWidth;
    private RectF rect;
    private Painter g;

    public Camera(float posX, float posY, float width, float height, int screenWidth, int screenHeight) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        rect = new RectF(posX, posY, posX + width, posY + height);
        this.ScreenWidth = screenWidth;
        this.ScreenHeight = screenHeight;
    }

    public void renderObject(PhysicalGameObject object, Painter g) {
        RectF oRect = object.getRect();
        if(!RectF.intersects(object.getRect(), rect)){
            return;
        }
        int rX, rY, rW, rH;
        rX = scale(object.getPosX(), posX, posX + width, width, ScreenWidth);
        rY = scale(object.getPosY(), posY, posY + height, height, ScreenHeight);
        rW = scale(object.getPosX()+object.getWidth(), posX, posX + width, width, ScreenWidth) - rX;
        rH = scale(object.getPosY()+object.getHeight(), posY, posY + height, height, ScreenHeight) - rY;
        g.drawImage(object.getBitmap(), rX, rY, rW, rH);
    }

    private int scale(float position, float beg, float end, float size, int screenSize) {
        return (int) ((position - beg)/(double)(size)*screenSize);
    }
}
