package adrians.game.state;

import android.view.MotionEvent;

import adrians.framework.util.Painter;
import adrians.simpleandroidgdf.Assets;

/**
 * Created by pierre on 07/02/16.
 */
public class LoadState extends State {
    @Override
    public void init() {
        Assets.load();
    }

    @Override
    public void update(float delta) {
        setCurrentState(new MenuState());
    }

    @Override
    public void render(Painter g) {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
