package adrians.game.state;

import android.view.MotionEvent;

import adrians.framework.util.Painter;
import adrians.simpleandroidgdf.GameMainActivity;

/**
 * Created by pierre on 06/02/16.
 */
public abstract class State {
    public void setCurrentState(State newState) {
        GameMainActivity.sGame.setCurrentState(newState);
    }
    public abstract void init();
    public abstract void update(float delta);
    public abstract void render(Painter g);
    public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);
}
