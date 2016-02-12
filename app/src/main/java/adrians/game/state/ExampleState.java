package adrians.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import adrians.framework.Assets;
import adrians.framework.util.Painter;
import adrians.framework.util.RandomNumberGenerator;
import adrians.game.camera.Camera;
import adrians.game.model.ExampleGameObject;
import adrians.framework.GameMainActivity;

/**
 * Created by pierre on 10/02/16.
 */
public class ExampleState extends State{
    private ExampleGameObject[] list;
    Camera camera;
    @Override
    public void init() {
        list = new ExampleGameObject[5];
        list[0] = new ExampleGameObject(-50, -50, 10, 10, 0, Assets.sampleBitmap);
        list[1] = new ExampleGameObject(50, -50, 10, 10, 0, Assets.sampleBitmap);
        list[2] = new ExampleGameObject(-50, 50, 10, 10, 0, Assets.sampleBitmap);
        list[3] = new ExampleGameObject(50, 50, 10, 10, 0, Assets.sampleBitmap);
        list[4] = new ExampleGameObject(100, 0, 15, 40, 0, Assets.sampleBitmap);
        camera = new Camera(100, 0, 200, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
    }

    @Override
    public void update(float delta) {
        for(int i=0; i<list.length; i++) {
            list[i].update(delta);
        }
        camera.update(delta);
    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        for(int i=0; i<list.length; i++) {
            camera.renderObject(list[i], g);
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
