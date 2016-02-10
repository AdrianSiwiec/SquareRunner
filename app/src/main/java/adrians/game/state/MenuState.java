package adrians.game.state;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.concurrent.Callable;

import adrians.framework.util.Painter;
import adrians.framework.util.ToggleButton;
import adrians.framework.util.UIButton;
import adrians.simpleandroidgdf.Assets;
import adrians.simpleandroidgdf.GameMainActivity;

/**
 * Created by pierre on 07/02/16.
 */
public class MenuState extends State {
    private UIButton playButton, scoreButton;
    private ToggleButton soundButton;

    @Override
    public void init() {
        playButton = new UIButton(316, 227, 484, 286, Assets.start, Assets.startDown);
        scoreButton = new UIButton(316, 300, 484, 359, Assets.score, Assets.scoreDown);
        soundButton = new ToggleButton(20, 20, 80, 80, Assets.soundOn, Assets.soundOff, !GameMainActivity.playSound, new Callable<Integer>() {
            @Override
            public Integer call() {
                GameMainActivity.toggleSound();
                return 0;
            }
        });
    }

    @Override
    public void update(float delta) {
        GameMainActivity.playSound = !soundButton.isToggled();
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.welcome, 0, 0);
        playButton.render(g);
        scoreButton.render(g);
        soundButton.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
        }
        if(e.getAction() == MotionEvent.ACTION_UP) {
            scoreButton.onTouchDown(scaledX, scaledY);
            soundButton.onTouchDown(scaledX, scaledY);
            if(playButton.isPressed(scaledX, scaledY)) {
                playButton.cancel();
                setCurrentState(new PlayState());
            } else if(scoreButton.isPressed(scaledX, scaledY)) {
                scoreButton.cancel();
                setCurrentState(new ScoreState());
            } else {
                scoreButton.cancel();
                playButton.cancel();
            }
        }
        return true;
    }
}
