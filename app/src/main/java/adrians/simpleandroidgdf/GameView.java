package adrians.simpleandroidgdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import adrians.framework.util.InputHandler;
import adrians.framework.util.Painter;
import adrians.game.state.LoadState;
import adrians.game.state.State;

/**
 * Created by pierre on 06/02/16.
 */
public class GameView extends SurfaceView implements Runnable{
    private Bitmap gameImage;
    private Rect gameImageSrc, gameImageDesc;
    private Canvas gameCanvas;
    private Painter graphics;

    private Thread gameThread;
    private volatile boolean running = false;
    private volatile State currentState;

    private InputHandler inputHandler;

    public GameView(Context context, int gameWidth, int gameHeight) {
        super(context);
        gameImage = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.RGB_565);
        gameImageSrc = new Rect(0, 0, gameImage.getWidth(), gameImage.getHeight());
        gameImageDesc = new Rect();
        gameCanvas = new Canvas(gameImage);
        graphics = new Painter(gameCanvas);

        SurfaceHolder holder = getHolder();
        holder.addCallback(new Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initInput();
                if (currentState == null) {
                    setCurrentState(new LoadState());
                }
                initGame();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                pauseGame();
            }
        });
    }
    public  GameView(Context context) {
        super(context);
    }

    public void setCurrentState(State newState) {
        System.gc();
        newState.init();
        currentState = newState;
        inputHandler.setCurrentState(currentState);
    }

    private void initInput() {
        if(inputHandler == null) {
            inputHandler = new InputHandler();
        }
        setOnTouchListener(inputHandler);
    }

    @Override
    public void run() {
        long updateDurationMilis = 0, sleepDurationMilis = 0;
        while (running) {
            long beforeUpdateAndRender = System.nanoTime();
            long deltaMilis = sleepDurationMilis + updateDurationMilis;
            updateAndRender(deltaMilis);
            updateDurationMilis = (System.nanoTime() - beforeUpdateAndRender);
            sleepDurationMilis = Math.max(2, 17-updateDurationMilis);

            try {
                Thread.sleep(sleepDurationMilis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initGame() {
        running = true;
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    private void pauseGame() {
        running = false;
        while(gameThread.isAlive()) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {

            }
        }
    }

    private void updateAndRender(long delta) {
        currentState.update(delta/1000000000f);
        currentState.render(graphics);
        renderGameImage();
    }

    private void renderGameImage() {
        Canvas screen = getHolder().lockCanvas();
        if(screen != null) {
            screen.getClipBounds(gameImageDesc);
            screen.drawBitmap(gameImage, gameImageSrc, gameImageDesc, null);
            getHolder().unlockCanvasAndPost(screen);

        }
    }
}
