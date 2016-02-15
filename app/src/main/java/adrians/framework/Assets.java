package adrians.framework;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.content.res.AssetFileDescriptor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pierre on 07/02/16.
 */
public class Assets {
    private static SoundPool soundPool;
    private static MediaPlayer mediaPlayer;
    public static Bitmap sampleBitmap, stickBitmap, stickButtonBitmap, squareButtonBitmap;

    public static void load() {
        sampleBitmap = loadBitmap("sampleBitmap.png", false);
        stickBitmap = loadBitmap("stickBase.png", true);
        stickButtonBitmap = loadBitmap("stickButton.png", true);
        squareButtonBitmap = loadBitmap("squareButton.png", true);
    }

    private static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try {
            inputStream = GameMainActivity.assets.open(filename);
        } catch (IOException e){
            e.printStackTrace();
        }
        Options options = new Options();
        if (transparency) {
            options.inPreferredConfig = Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, new Options());
        return bitmap;
    }

    private static int loadSound(String filename) {
        int soundID = 0;
        if(soundPool == null) {
            soundPool = buildSoundPool();
        }
        try {
            soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return soundID;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static SoundPool buildSoundPool() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(25)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        return  soundPool;
    }

    public static void playSound(int soundID) {
        if(!GameMainActivity.playSound) {
            return;
        }
        if(soundPool != null ) {
            soundPool.play(soundID, 1, 1, 1, 0, 1);
        }
    }

    public static void playMusic(String filename, boolean looping) {
        if(!GameMainActivity.playSound) {
            return;
        }
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onResume() {
        resumeMusic();
    }

    public static void onPause() {
        if(soundPool!=null) {
            soundPool.release();
            soundPool = null;
        }
        pauseMusic();
    }

    public static void resumeMusic() {
        playMusic("bgmusic.mp3", true);

    }

    public static void pauseMusic() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
