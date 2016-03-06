package adrians.game.state;

import android.graphics.Color;
import android.graphics.PointF;

import java.util.Vector;

import adrians.framework.Assets;
import adrians.framework.GameMainActivity;
import adrians.framework.util.Painter;
import adrians.framework.util.button.CenteringButton;
import adrians.framework.util.button.RotationButton;
import adrians.framework.util.button.VerticalButton;
import adrians.game.camera.Camera;
import adrians.game.model.PhysicalGameObject;
import adrians.game.model.PhysicalRectangle;
import adrians.game.model.PlayerSquare;
import adrians.game.model.TouchListener;

/**
 * Created by pierre on 10/02/16.
 */
public class PlayState extends State{
    CenteringButton movementButton;
    VerticalButton zoomButton;
    RotationButton rotationButton;
    PlayerSquare player;
    Vector<PhysicalRectangle> rectangles;
    TouchListener touchListener;
    private float minimumCameraWidth = 70, maximumCameraWidth = 400;
    public PlayState() {
        fixedObjects.addElement(new CenteringButton(new PointF(-78, 40), new PointF(20, 20),
                Assets.stickBitmap, new PointF(13, 13), Assets.stickButtonBitmap, 2));
        fixedObjects.addElement(new VerticalButton(new PointF(82, 0), new PointF(5, 35),
                Assets.squareButtonBitmap, new PointF(8, 8), Assets.stickButtonBitmap));
        fixedObjects.addElement(new RotationButton(new PointF(-78, 0), new PointF(18, 18),
                Assets.stickBitmap, new PointF(8, 8), Assets.stickButtonBitmap));
        movementButton = (CenteringButton) fixedObjects.elementAt(0);
        zoomButton = (VerticalButton) fixedObjects.elementAt(1);
        rotationButton = (RotationButton) fixedObjects.elementAt(2);
        worldCamera = new Camera(0, -40, 200, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        touchListener = new TouchListener(new PointF(0, 0), new PointF(100, 100));
        fixedObjects.add(0, touchListener);
        player = new PlayerSquare(new PointF(0, -90), new PointF(20, 20), Color.BLUE, touchListener);
        worldObjects.addElement(player);
        rectangles = new Vector<>();
        rectangles.addElement(new PhysicalRectangle(new PointF(0, 0), new PointF(100, 20), Color.RED));
        rectangles.addElement(new PhysicalRectangle(new PointF(100, 0), new PointF(20, 100), Color.GREEN));
    }

    @Override
    public void render(Painter g) {
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT, Color.WHITE);
        for(PhysicalRectangle rectangle: rectangles) {
            rectangle.render(g, worldCamera);
        }
        super.render(g);
    }

    @Override
    public void update(float delta) {
        worldCamera.move(movementButton.getVal().x * worldCamera.getWidth() * delta * 0.4f,
                movementButton.getVal().y * worldCamera.getHeight() * delta * 0.4f);
        worldCamera.setWidth(minimumCameraWidth + (maximumCameraWidth - minimumCameraWidth) * (zoomButton.getVal().y + 1) / 2);
        if(rotationButton.getVal().length() > 0.10) {
            worldCamera.setRotationAngle((float) (-Math.atan2(rotationButton.getVal().x, -rotationButton.getVal().y) * 360 / Math.PI / 2));
        }

        for(PhysicalGameObject obj : fixedObjects){
            obj.update(delta);
        }


        player.update(delta, rectangles);

        for(PhysicalRectangle rectangle: rectangles) {
            rectangle.update(delta);
        }

    }

//    @Override
//    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
//
//    }
}
