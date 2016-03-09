package adrians.game.model.level;

import java.util.Vector;

import adrians.framework.util.Painter;
import adrians.game.camera.Camera;
import adrians.game.model.gameObject.PhysicalRectangle;

/**
 * Created by pierre on 09/03/16.
 */
public class Level {
    public Vector<PhysicalRectangle> rectangles = new Vector<>();

    public void update(float delta) {
        for(PhysicalRectangle rect: rectangles) {
            rect.update(delta);
        }
    }

    public void render(Painter g, Camera camera) {
        for(PhysicalRectangle rect: rectangles) {
            rect.render(g, camera);
        }
    }

    public void addRectangle(PhysicalRectangle rectangle) {
        rectangles.addElement(rectangle);
    }
}
