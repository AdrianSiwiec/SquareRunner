package adrians.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import adrians.framework.Assets;
import adrians.framework.util.Painter;
import adrians.game.camera.Camera;
import adrians.game.model.ExampleGameObject;
import adrians.framework.GameMainActivity;

/**
 * Created by pierre on 10/02/16.
 */
public class ExampleState extends State{
    private ExampleGameObject object1;
    Camera camera;
    @Override
    public void init() {
        object1 = new ExampleGameObject(0, 0, 100, 100, Assets.sampleBitmap);
        camera = new Camera(-20, -20, 400, 200, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        camera.renderObject(object1, g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
