package adrians.framework.util;

import android.graphics.PointF;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.TextPaint;

import adrians.game.camera.Camera;
import adrians.game.model.gameObject.PhysicalGameObject;

/**
 * Created by pierre on 02/04/16.
 */
public class MessageBox extends PhysicalGameObject{
    String message;
    int fontColor, fontHeight;
    int numberOfLines = 1;
    TextPaint textPaint = new TextPaint();
    DynamicLayout layout;

    public MessageBox(PointF pos, PointF size, String message, int backgroundColor, int fontColor,
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
        g.drawTextLayout(camera.getScreenDistance(pos.x+size.x*1.1f), camera.getScreenDistance(pos.y+size.y*2-fontHeight/2), layout);

    }
}
