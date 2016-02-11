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
        list = new ExampleGameObject[20];
        for(int i=0; i<list.length; i++) {
            list[i] = new ExampleGameObject(RandomNumberGenerator.getRandIntBetween(-400, 400), RandomNumberGenerator.getRandIntBetween(-200, 200),
                    RandomNumberGenerator.getRandIntBetween(10, 100), RandomNumberGenerator.getRandIntBetween(10, 100), RandomNumberGenerator.getRandIntBetween(0, 360),
                    Assets.sampleBitmap);
        }
        camera = new Camera(0, 0, 400, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
    }

    @Override
    public void update(float delta) {
        for(int i=0; i<list.length; i++) {
            list[i].update(delta*2*(i+1)/list.length);
        }
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
