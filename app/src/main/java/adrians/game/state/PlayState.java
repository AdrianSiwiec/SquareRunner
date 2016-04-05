package adrians.game.state;

import android.graphics.PointF;

import adrians.framework.Assets;
import adrians.framework.GameMainActivity;
import adrians.framework.util.Caller;
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
    String levelName;
    public PlayState(String levelName) {
        this.levelName = levelName;
        fixedObjects.addElement(new PushButton(new PointF(120, -45), new PointF(10, 10),
                Assets.pauseButtonBitmap, Assets.pauseButtonBitmap));
        pauseButton = (PushButton) fixedObjects.elementAt(0);
        pauseButton.moveSmoothly(pauseButton.getPos(), new PointF(85, -45), 0.7f);


        touchListener = new TouchListener(new PointF(0, 0), new PointF(40, 50));
        fixedObjects.add(0, touchListener);

        currentLevel = LevelGenerator.generateLevel(levelName);

        worldCamera = new Camera(currentLevel.getCameraPos().x, currentLevel.getCameraPos().y,
                0.1f, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(currentLevel.getCameraSize().x,
                currentLevel.getCameraSize().x*GameMainActivity.GAME_HEIGHT/GameMainActivity.GAME_WIDTH), 1);
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
            fixedCamera.moveSmoothly(fixedCamera.getPos(), new PointF(pauseButton.getPos().x+pauseButton.getSize().x*0.22f,
                            pauseButton.getPos().y), 0.2f);
            fixedCamera.moveSmoothly(fixedCamera.getSize(), new PointF(0.03f, 0.03f), 0.2f, new Caller() {
                @Override
                public void call() {
                    onPause();
                }
            });
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
        StateManager.pushState(new PauseState(player.getColor(), currentLevel.getBackgroundColor(), levelName));
    }
     @Override
    public void onResume() {
         fixedCamera.moveSmoothly(fixedCamera.getPos(), new PointF(0, 0), 0.2f);
         fixedCamera.moveSmoothly(fixedCamera.getSize(), new PointF(100, 100 * GameMainActivity.GAME_HEIGHT / GameMainActivity.GAME_WIDTH), 0.2f);
     }

}
