package adrians.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import adrians.framework.util.Painter;

/**
 * Created by pierre on 02/04/16.
 */
public class MessageState extends State {
    private String message;
    private float elapsedTime=0, duration;
    int backgroundColor, fontColor;

    public MessageState(String message, float duration, int backgroundColor, int fontColor) {
        this.message = message;
        this.duration = duration;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
    }

    @Override
    public void update(float delta) {
        elapsedTime+=delta;
    }

    @Override
    public void render(Painter g) {
        if(elapsedTime<duration) {
            g.fillBackground(Color.argb(1, 0, 0, 0));
        }

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return super.onTouch(e, scaledX, scaledY);
    }
}
