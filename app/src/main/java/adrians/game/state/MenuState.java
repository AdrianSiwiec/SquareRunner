package adrians.game.state;

import android.view.MotionEvent;

import adrians.framework.util.Painter;
import adrians.simpleandroidgdf.Assets;

/**
 * Created by pierre on 07/02/16.
 */
public class MenuState extends State {
    @Override
    public void init() {
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.welcome, 0, 0);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
