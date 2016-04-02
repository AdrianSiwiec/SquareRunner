package adrians.game.state;

import android.graphics.PointF;

import adrians.framework.Assets;
import adrians.framework.util.button.PushButton;

/**
 * Created by pierre on 17/03/16.
 */
public class MenuState extends State {
    PushButton playButton;
    public MenuState() {
        super();
        fixedObjects.addElement(new PushButton(new PointF(0, 0), new PointF(30, 30),
                Assets.playButtonBitmap, Assets.playButtonBitmap));
        playButton = (PushButton) fixedObjects.elementAt(0);
        System.gc();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(playButton.gotPushed()) {
            StateManager.changeState(new PlayState());
        }
    }
}
