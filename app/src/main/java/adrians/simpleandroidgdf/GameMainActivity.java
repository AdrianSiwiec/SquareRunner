package adrians.simpleandroidgdf;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by pierre on 06/02/16.
 */
public class GameMainActivity extends Activity {
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 450;
    public static GameView sGame;
    public static AssetManager assets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assets=getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
