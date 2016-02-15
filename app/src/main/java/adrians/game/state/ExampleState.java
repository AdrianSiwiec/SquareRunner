package adrians.game.state;

import android.graphics.Color;

import adrians.framework.Assets;
import adrians.framework.util.button.AnalogButton;
import adrians.framework.util.Painter;
import adrians.framework.util.button.CenteringButton;
import adrians.framework.util.button.RotationButton;
import adrians.framework.util.button.VerticalButton;
import adrians.game.camera.Camera;
import adrians.game.model.ExampleGameObject;
import adrians.framework.GameMainActivity;
import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 10/02/16.
 */
public class ExampleState extends State{
    CenteringButton movementButton;
    VerticalButton zoomButton;
    RotationButton rotationButton;
    private float minimumCameraWidth = 70, maximumCameraWidth = 400;
    @Override
    public void init() {
        worldObjects = new PhysicalGameObject[1];
        fixedObjects = new PhysicalGameObject[3];
        fixedObjects[0] = new CenteringButton(-78, 40, 20, 20, Assets.stickBitmap, 13, 13, Assets.stickButtonBitmap, 2);
        fixedObjects[1] = new VerticalButton(82, 0, 5, 35, Assets.squareButtonBitmap, 8, 8, Assets.stickButtonBitmap);
        fixedObjects[2] = new RotationButton(-78, 0, 18, 18, Assets.stickBitmap, 8, 8, Assets.stickButtonBitmap);
        movementButton = (CenteringButton) fixedObjects[0];
        zoomButton = (VerticalButton) fixedObjects[1];
        rotationButton = (RotationButton) fixedObjects[2];
        worldObjects[0] = new ExampleGameObject(0, 0, 40, 40, 0, Assets.sampleBitmap);
        worldCamera = new Camera(0, 0, 200, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        super.render(g);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        worldCamera.move(movementButton.getVal().x * worldCamera.getWidth() * delta * 0.4f,
                movementButton.getVal().y * worldCamera.getHeight() * delta * 0.4f);
        worldCamera.setWidth(minimumCameraWidth + (maximumCameraWidth - minimumCameraWidth) * (zoomButton.getVal().y + 1) / 2);
        if(rotationButton.getVal().length() > 0.10) {
            worldCamera.setRotationAngle((float) (-Math.atan2(rotationButton.getVal().x, -rotationButton.getVal().y) * 360 / Math.PI / 2));
        }
    }

//    @Override
//    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
//
//    }
}
