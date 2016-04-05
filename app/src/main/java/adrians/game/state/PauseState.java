package adrians.game.state;

import android.graphics.Color;
import android.graphics.PointF;

import adrians.framework.GameMainActivity;
import adrians.framework.util.Caller;
import adrians.framework.util.button.MessageButton;
import adrians.game.model.gameObject.PhysicalRectangle;

/**
 * Created by pierre on 17/03/16.
 */
public class PauseState extends State {
    private boolean rendered = false;
    private MessageButton resumeButton, restartButton, exitButton;
    private int fontColor = Color.rgb(78, 78, 78), backColor;
    String levelName;
    public PauseState(int playerColor, int backColor, String levelName) {
        this.backColor = playerColor;
        this.levelName = levelName;
        worldCamera.setWidth(0.1f);
        worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(100, 100 * GameMainActivity.GAME_HEIGHT / GameMainActivity.GAME_WIDTH), 0.2f);
        backgroundColor = Color.rgb(127, 127, 127);
        resumeButton = new MessageButton(new PointF(0, -25), new PointF(30, 10), "Resume", fontColor,
                Color.BLACK, 8, worldCamera);
        worldObjects.addElement(resumeButton);
        restartButton = new MessageButton(new PointF(0, 0), new PointF(30, 10), "Restart", fontColor,
                Color.BLACK, 8, worldCamera);
        worldObjects.addElement(restartButton);
        exitButton = new MessageButton(new PointF(0, 25), new PointF(30, 10), "Menu", fontColor,
                Color.BLACK, 8, worldCamera);
        worldObjects.addElement(exitButton);
        worldObjects.addElement(new PhysicalRectangle(new PointF(-205, 0), new PointF(100, 100), playerColor));
        worldObjects.addElement(new PhysicalRectangle(new PointF(205, 0), new PointF(100, 100), backColor));
    }

//    @Override
//    public void render(Painter g) {
//
//    }


    @Override
    public void update(float delta) {
        super.update(delta);
        if(resumeButton.gotPushed()) {
            worldCamera.moveSmoothly(worldCamera.getPos(), resumeButton.getPos(), 0.2f);
            worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(0.1f, 0.1f), 0.2f, new Caller() {
                @Override
                public void call() {
                    StateManager.popState();
                }
            });
        }
        if(restartButton.gotPushed()) {
            worldCamera.moveSmoothly(worldCamera.getPos(), new PointF(-200, 0), 0.3f, new Caller() {
                @Override
                public void call() {
                    StateManager.popState();
                    StateManager.changeState(new PlayState(levelName));
                }
            });
        }
        if(exitButton.gotPushed()) {
            worldCamera.moveSmoothly(worldCamera.getPos(), new PointF(200, 0), 0.3f, new Caller() {
                @Override
                public void call() {
                    StateManager.popState();
                    StateManager.changeState(new MenuState());
                }
            });
        }
    }
}
