package adrians.framework.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

/**
 * Created by pierre on 06/02/16.
 */
public class Painter {
    private Canvas canvas;
    private Paint paint, transparentPaint;
    private Rect srcRect, dstRect;
    private RectF dstRectF;

    public Painter(Canvas canvas) {
        this.canvas = canvas;
        paint = new Paint();
        transparentPaint = new Paint();
        srcRect = new Rect();
        dstRect = new Rect();
        dstRectF = new RectF();
    }

    public synchronized void setColor(int color){
        paint.setColor(color);
    }
    public synchronized void setFont(Typeface typeface, float textSize) {
        paint.setTypeface(typeface);
        paint.setTextSize(textSize);
    }
    public synchronized void drawString(String str, int x, int y) {
        canvas.drawText(str, x, y, paint);
    }
    public synchronized void fillRect(int x, int y, int width, int height) {
        dstRect.set(x, y, x+width, y+height);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(dstRect, paint);
    }
    public synchronized void drawImage(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }
    public synchronized void drawImage(Bitmap bitmap, int x, int y, int width, int height) {
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dstRect.set(x, y, x+width, y+height);
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
    }
    public synchronized void drawImage(Bitmap bitmap, int x, int y, int width, int height, float rotationAngle) {
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dstRect.set(x, y, x+width, y+height);
        canvas.save();
        canvas.rotate(rotationAngle, x + width / 2, y + height / 2);
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);
        canvas.restore();
    }
    public synchronized void drawImage(Bitmap bitmap, float x, float y) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }
    public synchronized void drawImage(Bitmap bitmap, float x, float y, float width, float height) {
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dstRectF.set(x, y, x+width, y+height);
        canvas.drawBitmap(bitmap, srcRect, dstRectF, paint);
    }
    public synchronized void drawImage(Bitmap bitmap, float x, float y, float width, float height, float rotationAngle) {
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dstRectF.set(x, y, x+width, y+height);
        canvas.save();
        canvas.rotate(rotationAngle, x + width / 2, y + height / 2);
        canvas.drawBitmap(bitmap, srcRect, dstRectF, paint);
        canvas.restore();
    }

    public synchronized void drawImageTransparent(Bitmap bitmap, float x, float y, float width, float height, int alpha) {
        transparentPaint.setAlpha(alpha);
        srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dstRectF.set(x, y, x + width, y + height);
        canvas.drawBitmap(bitmap, srcRect, dstRectF, transparentPaint);
    }

    public synchronized void fillOval(int x, int y, int width, int height) {
        paint.setStyle(Paint.Style.FILL);
        dstRectF.set(x, y, x+width, y+height);
        canvas.drawOval(dstRectF, paint);
    }

    public synchronized void fillRect(float posX, float posY, float width, float height) {
        fillRect((int) posX, (int)posY, (int) width, (int) height);
    }
}
