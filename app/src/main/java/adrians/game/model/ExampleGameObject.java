package adrians.game.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import adrians.framework.util.Painter;

/**
 * Created by pierre on 10/02/16.
 */
public class ExampleGameObject extends PhysicalGameObject {
    public ExampleGameObject(float posX, float posY, float width, float height, Bitmap bitmap) {
        super(posX, posY, width, height, bitmap);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.RED);
        g.fillRect(posX, posY,width, height);
    }
}
