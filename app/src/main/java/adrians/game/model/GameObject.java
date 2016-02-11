package adrians.game.model;

import adrians.framework.util.Painter;

/**
 * Created by pierre on 10/02/16.
 */
public abstract class GameObject {
    public abstract void update(float delta);
    public abstract void render(Painter g);
}
