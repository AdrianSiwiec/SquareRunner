package adrians.game.model;

import android.graphics.Bitmap;

import adrians.framework.util.Painter;

/**
 * Created by pierre on 10/02/16.
 */
public class ExampleGameObject extends PhysicalGameObject {
    public ExampleGameObject(float posX, float posY, float width, float height, float rotationAngle, Bitmap bitmap) {
        super(posX, posY, width, height, rotationAngle, bitmap);
    }

    @Override
    public void update(float delta) {
        rotationAngle+=delta * 36;
    }

    @Override
    public void render(Painter g) {

    }
}
