package adrians.framework.util.button;

import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.TextPaint;

import java.util.LinkedList;

import adrians.framework.util.Painter;
import adrians.game.camera.Camera;
import adrians.game.model.gameObject.PhysicalRectangle;

/**
 * Created by pierre on 02/04/16.
 */
// This whole class is just a stub. Its only reason of existence is that it was quick to write it
// that way. TODO make more generic
public class MessageButton extends PushButton{
    String message;
    int fontColor, fontHeight;
    TextPaint textPaint = new TextPaint();
    private LinkedList<PhysicalRectangle> borders = new LinkedList<>();

    public MessageButton(PointF pos, PointF size, String message, int backgroundColor, int fontColor,
                         int fontHeight, Camera camera) {
        super(pos, size);
        this.message = message;
        this.color = backgroundColor;
        this.fontColor = fontColor;
        this.fontHeight = fontHeight;
        textPaint.setColor(fontColor);
        textPaint.setTextSize(camera.getScreenDistance(fontHeight));
        float borderThickness = size.y*0.07f;
        borders.addLast(new PhysicalRectangle(new PointF(pos.x, pos.y + size.y-borderThickness), new PointF(size.x, borderThickness), fontColor));
        borders.addLast(new PhysicalRectangle(new PointF(pos.x, pos.y - size.y+borderThickness), new PointF(size.x, borderThickness), fontColor));
        borders.addLast(new PhysicalRectangle(new PointF(pos.x-size.x+borderThickness, pos.y), new PointF(borderThickness, size.y), fontColor));
        borders.addLast(new PhysicalRectangle(new PointF(pos.x+size.x-borderThickness, pos.y), new PointF(borderThickness, size.y), fontColor));
    }

    @Override
    public void render(Painter g, Camera camera) {
        camera.renderObject(this, g);
        for(PhysicalRectangle border: borders) {
            border.render(g, camera);
        }
        PointF sPos;
        g.setFont(Typeface.SANS_SERIF, camera.getScreenDistance(size.y));
        g.setColor(fontColor);
        sPos = camera.getScreenCoords(pos.x-message.length()*size.y/4, pos.y+size.y/2.2f);
        g.drawString(message, sPos.x, sPos.y);

    }

    public String getMessage() {
        return message;
    }
}
