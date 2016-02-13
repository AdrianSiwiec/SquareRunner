package adrians.game.state;

import android.graphics.Color;
import android.view.MotionEvent;

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
    @Override
    public void init() {
        objectList = new ExampleGameObject[3];
        objectList[0] = new ExampleGameObject(100, 0, 40, 40, 0, Assets.sampleBitmap);
        objectList[1] = new ExampleGameObject(0, 0, 40, 40, 0, Assets.sampleBitmap);
        objectList[2] = new ExampleGameObject(-100, 0, 40, 40, 0, Assets.sampleBitmap);
        camera = new Camera(0, 0, 200, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
    }

    @Override
    public void update(float delta) {
        for(int i=0; i<objectList.length; i++) {
            objectList[i].update(delta);
        }
        camera.update(delta);
    }

    @Override
    public void render(Painter g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        for(int i=0; i<objectList.length; i++) {
            camera.renderObject(objectList[i], g);
        }

    }

//    @Override
//    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
//
//    }
}
