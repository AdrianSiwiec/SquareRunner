package adrians.game.model.level;

import android.graphics.PointF;

import java.util.Vector;

import adrians.framework.util.Painter;
import adrians.framework.util.button.MessageButton;
import adrians.game.camera.Camera;
import adrians.game.model.gameObject.PhysicalRectangle;

/**
 * Created by pierre on 09/03/16.
 */
public class Level {
    public Vector<PhysicalRectangle> rectangles = new Vector<>();
    public Vector<MessageButton> messages = new Vector<>();
    private PointF playerPos, playerSize;
    private int playerColor, backgroundColor;
    private PointF cameraPos, cameraSize;
    public PhysicalRectangle goal;

    public void update(float delta) {
        for(PhysicalRectangle rect: rectangles) {
            rect.update(delta);
        }
    }

    public void render(Painter g, Camera camera) {
        g.fillBackground(backgroundColor);
        for(PhysicalRectangle rect: rectangles) {
            rect.render(g, camera);
        }
        for(MessageButton mes: messages) {
            mes.render(g, camera);
        }
        goal.render(g, camera);
    }

    public void addRectangle(PhysicalRectangle rectangle) {

        rectangles.addElement(rectangle);
    }
    public void addGoal(PhysicalRectangle rectangle) {
        rectangles.add(0, rectangle);
        goal = rectangle;
    }

    public void addMessage (MessageButton messageButton) {
        messages.addElement(messageButton);
    }

    public PointF getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(PointF playerPos) {
        this.playerPos = playerPos;
    }

    public PointF getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(PointF playerSize) {
        this.playerSize = playerSize;
    }

    public int getPlayerColor() {
        return playerColor;
    }

    public PointF getCameraPos() {
        return cameraPos;
    }

    public void setCameraPos(PointF cameraPos) {
        this.cameraPos = cameraPos;
    }

    public PointF getCameraSize() {
        return cameraSize;
    }

    public void setCameraSize(PointF cameraSize) {
        this.cameraSize = cameraSize;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPlayerColor(int playerColor) {
        this.playerColor = playerColor;
    }
}
