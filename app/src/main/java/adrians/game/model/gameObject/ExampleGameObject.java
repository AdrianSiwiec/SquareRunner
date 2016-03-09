package adrians.game.model.gameObject;

import android.graphics.Bitmap;
import android.graphics.PointF;

import adrians.framework.util.TouchPointer;

/**
 * Created by pierre on 10/02/16.
 */
public class ExampleGameObject extends PhysicalGameObject {
    float rotationAngleAdded;
    public ExampleGameObject(PointF pos, PointF size, float rotationAngle, Bitmap bitmap) {
        super(pos, size, rotationAngle, bitmap);
    }

    @Override
    public synchronized float getRotationAngle() {
        return rotationAngle + rotationAngleAdded;
    }

    @Override
    public synchronized void onPointerMove() {
        pos.x += (pointers.getFirst().getCurRel().x - pointers.getFirst().getBegRel().x) * size.x /2;
        pos.y += (pointers.getFirst().getCurRel().y - pointers.getFirst().getBegRel().y) * size.y /2;
        if(pointers.size() >=2) {
            pos.x += (pointers.get(1).getCurRel().x - pointers.get(1).getBegRel().x) * size.x /2;
            pos.y += (pointers.get(1).getCurRel().y - pointers.get(1).getBegRel().y) * size.y /2;
            double angle1 = Math.atan2(pointers.get(0).getBegRel().y - pointers.get(1).getBegRel().y,
                    pointers.get(0).getBegRel().x - pointers.get(1).getBegRel().x);
            double angle2 = Math.atan2(pointers.get(0).getCurRel().y - pointers.get(1).getCurRel().y,
                    pointers.get(0).getCurRel().x - pointers.get(1).getCurRel().x);
            rotationAngleAdded = (float) (360f/2/Math.PI * (angle2 - angle1));
//            size.x *= (pointers.get(0).getCurRel().x - pointers.get(1).getCurRel().x) /
//                    (pointers.get(0).getBegRel().x - pointers.get(1).getBegRel().x);
//            size.y *= (pointers.get(0).getCurRel().x - pointers.get(1).getCurRel().x) /
//                    (pointers.get(0).getBegRel().x - pointers.get(1).getBegRel().x);
        }
    }

    @Override
    public synchronized void onPointerUp(TouchPointer ptr) {
        super.onPointerUp(ptr);
        rotationAngle += rotationAngleAdded;
        rotationAngleAdded = 0;
    }


}
