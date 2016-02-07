package adrians.framework.animation;

import android.graphics.Bitmap;

/**
 * Created by pierre on 07/02/16.
 */
public class Frame {
    private Bitmap image;
    private double duration;

    public Frame(Bitmap image, double duration) {
        this.image = image;
        this.duration = duration;
    }

    public Bitmap getImage() {
        return image;
    }

    public double getDuration() {
        return duration;
    }
}
