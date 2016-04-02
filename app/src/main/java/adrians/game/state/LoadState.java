package adrians.game.state;

import android.view.MotionEvent;

import adrians.framework.util.Painter;
import adrians.framework.Assets;

/**
 * Created by pierre on 07/02/16.
 */
public class LoadState extends State {
    public LoadState() {
        Assets.load();
    }

    @Override
    public void update(float delta) {
        StateManager.changeState(new MenuState());
    }

    @Override
    public void render(Painter g) {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
