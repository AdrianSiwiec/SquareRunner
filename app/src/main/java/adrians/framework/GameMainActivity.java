package adrians.framework;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import adrians.game.model.GameObject;

/**
 * Created by pierre on 06/02/16.
 */
public class GameMainActivity extends Activity {
    public static int GAME_WIDTH, GAME_HEIGHT;
    public static GameView sGame;
    public static AssetManager assets;
    public static boolean playSound = true;

    private static SharedPreferences prefs;
    private static final String highScoreKey = "highScoreKey"; //TODO Clean up
    private static int highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        if(Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            GAME_WIDTH = size.x;
            GAME_HEIGHT = size.y;
        } else {
            GAME_WIDTH = display.getWidth();
            GAME_HEIGHT = display.getHeight();
        }
        prefs = getPreferences(Activity.MODE_PRIVATE);
        highScore = retrieveHighScore();
        assets=getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void setHighScore(int highScore) {
        GameMainActivity.highScore = highScore;
        Editor editor = prefs.edit();
        editor.putInt(highScoreKey, highScore);
        editor.commit();
    }

    private int retrieveHighScore() {
        return prefs.getInt(highScoreKey, 0);
    }

    public static int getHighScore() {
        return highScore;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Assets.onResume();
        sGame.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Assets.onPause();
        sGame.onPause();
    }

    public static void toggleSound() {
        playSound ^= true;
        if(playSound) {
            Assets.resumeMusic();
        } else {
            Assets.pauseMusic();
        }
    }
}
