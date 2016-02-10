package adrians.game.state;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.concurrent.Callable;

import adrians.framework.util.Painter;
import adrians.framework.util.ToggleButton;
import adrians.simpleandroidgdf.Assets;
import adrians.simpleandroidgdf.GameMainActivity;

/**
 * Created by pierre on 10/02/16.
 */
public class ScoreState extends State {
    private String highScore;
    private ToggleButton soundButton;
    @Override
    public void init() {
        highScore = GameMainActivity.getHighScore() + "";
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

    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.rgb(53,156,253));
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD, 50);
        g.drawString("The All-Time High Score", 120, 175);
        g.setFont(Typeface.DEFAULT_BOLD, 70);
        g.drawString(highScore, 370, 260);
        g.setFont(Typeface.DEFAULT_BOLD, 50);
        g.drawString("Touch the screen.", 220, 350);
        soundButton.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(e.getAction() == MotionEvent.ACTION_UP) {
            if(soundButton.getRect().contains(scaledX, scaledY)) {
                soundButton.onTouchDown(scaledX, scaledY);
            } else {
                setCurrentState(new MenuState());
            }
        }
        return true;
    }
}
