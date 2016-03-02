package adrians.framework.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import adrians.framework.GameMainActivity;
import adrians.game.state.StateManager;

/**
 * Created by pierre on 06/02/16.
 */
public class InputHandler implements OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH);
        int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
        return StateManager.getCurrentState().onTouch(event, scaledX, scaledY);
    }
}
