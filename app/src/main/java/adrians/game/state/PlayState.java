package adrians.game.state;

import android.graphics.PointF;

import adrians.framework.Assets;
import adrians.framework.GameMainActivity;
import adrians.framework.util.Painter;
import adrians.framework.util.button.CenteringButton;
import adrians.framework.util.button.PushButton;
import adrians.framework.util.button.RotationButton;
import adrians.framework.util.button.VerticalButton;
import adrians.game.camera.Camera;
import adrians.game.model.TouchListener;
import adrians.game.model.gameObject.PlayerSquare;
import adrians.game.model.level.Level;
import adrians.game.model.level.LevelGenerator;

/**
 * Created by pierre on 10/02/16.
 */
public class PlayState extends State{
    CenteringButton movementButton;
    VerticalButton zoomButton;
    RotationButton rotationButton;
    PushButton pauseButton;
    PlayerSquare player;
    Level currentLevel;
    TouchListener touchListener;
    private float minimumCameraWidth = 70, maximumCameraWidth = 800;
    public PlayState() {
        fixedObjects.addElement(new CenteringButton(new PointF(-78, 40), new PointF(20, 20),
                Assets.stickBitmap, new PointF(13, 13), Assets.stickButtonBitmap, 2));
//        fixedObjects.addElement(new VerticalButton(new PointF(82, 0), new PointF(5, 35),
//                Assets.squareButtonBitmap, new PointF(8, 8), Assets.stickButtonBitmap));
        fixedObjects.addElement(new RotationButton(new PointF(-78, 0), new PointF(18, 18),
                Assets.stickBitmap, new PointF(8, 8), Assets.stickButtonBitmap));
        fixedObjects.addElement(new PushButton(new PointF(85, -45), new PointF(10, 10),
                Assets.pauseButtonBitmap, Assets.pauseButtonBitmap));
        movementButton = (CenteringButton) fixedObjects.elementAt(0);
//        zoomButton = (VerticalButton) fixedObjects.elementAt(1);
        rotationButton = (RotationButton) fixedObjects.elementAt(1); //ugly as fuck but it'll be gone
        pauseButton = (PushButton) fixedObjects.elementAt(2);


        touchListener = new TouchListener(new PointF(0, 0), new PointF(70, 50));
        fixedObjects.add(0, touchListener);
//        fixedObjects.addElement(new PhysicalRectangle(touchListener.getPos(), touchListener.getSize(), Color.BLACK));



        currentLevel = LevelGenerator.generateLevel("2");

        worldCamera = new Camera(currentLevel.getCameraPos().x, currentLevel.getCameraPos().y,
                currentLevel.getCameraSize().x, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        player = new PlayerSquare(currentLevel.getPlayerPos(), currentLevel.getPlayerSize(),
                currentLevel.getPlayerColor(), touchListener);
//        player = new PlayerSquare(new PointF(95, 140), new PointF(20, 20), Color.BLUE, touchListener);
        worldCamera.setModeFollowLoosely(player, 10, 10);
    }

    @Override
    public void render(Painter g) {
        super.render(g);
        currentLevel.render(g, worldCamera);
        player.render(g, worldCamera);
    }

    @Override
    public void update(float delta) {
        if(pauseButton.gotPushed()) {
            onPause();
        }
        worldCamera.move(movementButton.getVal().x * worldCamera.getWidth() * delta * 0.4f,
                movementButton.getVal().y * worldCamera.getHeight() * delta * 0.4f);
//        worldCamera.setWidth(minimumCameraWidth + (maximumCameraWidth - minimumCameraWidth) * (zoomButton.getVal().y + 1) / 2);
        if(rotationButton.getVal().length() > 0.10) {
            worldCamera.setRotationAngle((float) (-Math.atan2(rotationButton.getVal().x, -rotationButton.getVal().y) * 360 / Math.PI / 2));
        }

        player.update(delta, currentLevel.rectangles);
        currentLevel.update(delta);
        super.update(delta);

    }

//    @Override
//    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
//
//    }


    @Override
    public void onPause() {
        StateManager.pushState(new PauseState());
    }
}
