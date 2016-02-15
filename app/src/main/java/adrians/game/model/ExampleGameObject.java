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
    public synchronized void onPointerMove() {
        posX += (pointers.getFirst().getCurRel().x - pointers.getFirst().getBegRel().x) * width /2;
        posY += (pointers.getFirst().getCurRel().y - pointers.getFirst().getBegRel().y) * height /2;
        if(pointers.size() >=2) {
            double angle1 = Math.atan2(pointers.get(0).getBegRel().y - pointers.get(1).getBegRel().y,
                    pointers.get(0).getBegRel().x - pointers.get(1).getBegRel().x);
            double angle2 = Math.atan2(pointers.get(0).getCurRel().y - pointers.get(1).getCurRel().y,
                    pointers.get(0).getCurRel().x - pointers.get(1).getCurRel().x);
            rotationAngle = (float) (360f/2/Math.PI * (angle2 - angle1));
        }
    }


}
