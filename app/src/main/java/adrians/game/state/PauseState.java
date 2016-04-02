package adrians.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import adrians.framework.GameMainActivity;
import adrians.framework.util.Painter;

/**
 * Created by pierre on 17/03/16.
 */
public class PauseState extends State {
    private boolean rendered = false;
    public PauseState() {
        super();
        System.gc();
    }

    @Override
    public void render(Painter g) {
        if(!rendered) {
            g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT, Color.argb(120, 0, 0, 0));
            rendered = true;
        }
    }

    @Override
    public synchronized boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(StateManager.getCurrentState()==this) {
            StateManager.popState();
        }
        return false;
    }
}
