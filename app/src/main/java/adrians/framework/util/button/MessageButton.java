package adrians.framework.util.button;

import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.TextPaint;

import adrians.framework.util.Painter;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 02/04/16.
 */
public class MessageButton extends PushButton{
    String message;
    int fontColor, fontHeight;
    int numberOfLines = 1;
    TextPaint textPaint = new TextPaint();
    DynamicLayout layout;

    public MessageButton(PointF pos, PointF size, String message, int backgroundColor, int fontColor,
                         int fontHeight, Camera camera) {
        super(pos, size);
        this.message = message;
        this.color = backgroundColor;
        this.fontColor = fontColor;
        this.fontHeight = fontHeight;
        textPaint.setColor(fontColor);
        textPaint.setTextSize(camera.getScreenDistance(fontHeight));
        layout =new DynamicLayout(message, textPaint, (int)camera.getScreenDistance(size.x*1.8f),
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0f, false);
    }

    @Override
    public void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
        g.setFont(Typeface.SANS_SERIF, camera.getScreenDistance(size.y));
        g.setColor(fontColor);
        PointF coords = camera.getScreenCoords(pos.x, pos.y); //TODO make not shitty
        g.drawString(message, coords.x, coords.y);

    }
}
