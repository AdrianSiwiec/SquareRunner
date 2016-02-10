package adrians.game.model;

import adrians.framework.util.RandomNumberGenerator;

/**
 * Created by pierre on 07/02/16.
 */
public class Cloud {
    private float x, y;
    private static final int VEL_X = -15;

    public Cloud(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        x+=VEL_X * delta;
        if(x<=-200) {
            x+=1000;
            y = RandomNumberGenerator.getRandIntBetween(20, 100);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
