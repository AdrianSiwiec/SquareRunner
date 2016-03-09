package adrians.game.model.gameObject;

import adrians.framework.util.Painter;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 10/02/16.
 */
public abstract class GameObject {
    public abstract void update(float delta);
    public abstract void render(Painter g, Camera camera);
}
