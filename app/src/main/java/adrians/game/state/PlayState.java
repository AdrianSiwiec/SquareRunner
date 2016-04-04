package adrians.game.state;

import android.graphics.PointF;

import adrians.framework.Assets;
import adrians.framework.GameMainActivity;
import adrians.framework.util.Painter;
import adrians.framework.util.button.PushButton;
import adrians.game.camera.Camera;
import adrians.game.model.TouchListener;
import adrians.game.model.gameObject.PhysicalGameObject;
import adrians.game.model.gameObject.PlayerSquare;
import adrians.game.model.level.Level;
import adrians.game.model.level.LevelGenerator;

/**
 * Created by pierre on 10/02/16.
 */
public class PlayState extends State{
    PushButton pauseButton;
    PlayerSquare player;
    Level currentLevel;
    TouchListener touchListener;
    public PlayState() {
        fixedObjects.addElement(new PushButton(new PointF(85, -45), new PointF(10, 10),
                Assets.pauseButtonBitmap, Assets.pauseButtonBitmap));
        pauseButton = (PushButton) fixedObjects.elementAt(0);


        touchListener = new TouchListener(new PointF(0, 0), new PointF(40, 50));
        fixedObjects.add(0, touchListener);

        currentLevel = LevelGenerator.generateLevel("1");

        worldCamera = new Camera(currentLevel.getCameraPos().x, currentLevel.getCameraPos().y,
                currentLevel.getCameraSize().x, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        player = new PlayerSquare(currentLevel.getPlayerPos(), currentLevel.getPlayerSize(),
                currentLevel.getPlayerColor(), touchListener);
        worldCamera.setModeFollowLoosely(player, 10, 10);
    }

    @Override
    public void render(Painter g) {
        currentLevel.render(g, worldCamera);
        player.render(g, worldCamera);
//        super.render(g);
        for(PhysicalGameObject o: fixedObjects) {
            o.render(g, fixedCamera);
        }
    }

    @Override
    public void update(float delta) {
        if(pauseButton.gotPushed()) {
            onPause();
        }

        player.update(delta, currentLevel.rectangles);
//        currentLevel.update(delta);
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
