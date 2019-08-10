package adrians.game.state;

import android.graphics.PointF;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import adrians.framework.Assets;
import adrians.framework.GameMainActivity;
import adrians.framework.util.Caller;
import adrians.framework.util.Painter;
import adrians.framework.util.XmlParser;
import adrians.framework.util.button.PushButton;
import adrians.game.camera.Camera;
import adrians.game.model.TouchListener;
import adrians.game.model.gameObject.PhysicalGameObject;
import adrians.game.model.gameObject.PlayerSquare;
import adrians.game.model.level.Level;
import adrians.game.model.level.LevelGenerator;

/**
 * Created by pierre on 10/02/16.
 */
public class PlayState extends State{
    PushButton pauseButton;
    PlayerSquare player;
    Level currentLevel;
    TouchListener touchListener;
    String levelName;
    private void LoadLevelObjects() {
        worldCamera = new Camera(currentLevel.getCameraPos().x, currentLevel.getCameraPos().y,
                -1.1f, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(currentLevel.getCameraSize().x,
                currentLevel.getCameraSize().x*GameMainActivity.GAME_HEIGHT/GameMainActivity.GAME_WIDTH), 0);
        player = new PlayerSquare(currentLevel.getPlayerPos(), currentLevel.getPlayerSize(),
                currentLevel.getPlayerColor(), touchListener);
        worldCamera.setModeFollowLoosely(player, 9, 10);
    }

    public PlayState(String levelName) {

        Log.i("Level name", levelName);

        this.levelName = levelName;
        fixedObjects.addElement(new PushButton(new PointF(120, -45), new PointF(10, 10),
                Assets.pauseButtonBitmap, Assets.pauseButtonBitmap));
        pauseButton = (PushButton) fixedObjects.elementAt(0);
        pauseButton.moveSmoothly(pauseButton.getPos(), new PointF(85, -45), 0.7f);


        touchListener = new TouchListener(new PointF(0, 0), new PointF(40, 50));
        fixedObjects.add(0, touchListener);

        currentLevel = LevelGenerator.generateLevel(levelName);

        try{
            LoadLevelObjects();
        } catch (Exception e) {
            currentLevel = LevelGenerator.generateLevel(XmlParser.errorLevelFilename);
            LoadLevelObjects();
        }
    }

    @Override
    public void render(Painter g) {
        currentLevel.render(g, worldCamera);
        player.render(g, worldCamera);
//        super.render(g);
        for(PhysicalGameObject o: fixedObjects) {
            o.render(g, fixedCamera);
        }
    }

    @Override
    public void update(float delta) {
        if(player.isHappy()) {
            currentLevel.goal.setRotationAngle(currentLevel.goal.getRotationAngle()+delta*360*3);
            player.setRotationAngle(player.getRotationAngle()+delta*360*3);
//            worldCamera.setRotationAngle(worldCamera.getRotationAngle()+delta*360*0.3f);
        }
        if(pauseButton.gotPushed()) {
            fixedCamera.moveSmoothly(fixedCamera.getPos(), new PointF(pauseButton.getPos().x+pauseButton.getSize().x*0.22f,
                            pauseButton.getPos().y), 0.2f);
            fixedCamera.moveSmoothly(fixedCamera.getSize(), new PointF(0.03f, 0.03f), 0.2f, new Caller() {
                @Override
                public void call() {
                    onPause();
                }
            });
        }

        player.update(delta, currentLevel.rectangles);
//        currentLevel.update(delta);
//        super.update(delta);
        worldCamera.update(delta);
        fixedCamera.update(delta);
        pauseButton.update(delta);
        if(currentLevel.goal.isTouching(player, 0)) {
            if(!player.isHappy()) {
                try {
                    String nextLevel = "" + (new Integer(levelName) + 1);
                    Set<String> unlckd = new HashSet<String>(GameMainActivity.getUnlocked());
                    unlckd.add(nextLevel);
                    GameMainActivity.setUnlocked(unlckd);
                } catch (Exception e) {
                    //Non-regular levels, don't unlock
                }
                pauseButton.moveSmoothly(pauseButton.getPos(), new PointF(120, -45), 0.5f);
                worldCamera.setModeFixed();
                worldCamera.moveSmoothly(worldCamera.getPos(), currentLevel.goal.getPos(), 2f);
                worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(0.1f, 0.1f), 2f, new Caller() {
                    @Override
                    public void call() {
                        StateManager.popState();
                    }
                });
            }
            player.beHappy();
        }

//        Log.d("Time", player.getTimeSinceLastMove()+"");
        if(levelName.equals("5")) {
            if (worldCamera.getMovements().size() == 0) {
                if (player.getTimeSinceLastMove() > 3) {
                    worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(350f, 350f * GameMainActivity.GAME_HEIGHT / GameMainActivity.GAME_WIDTH), 0.3f);
                } else {
                    if (worldCamera.getSize().x > currentLevel.getCameraSize().x + 2) {
                        worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(currentLevel.getCameraSize().x,
                                currentLevel.getCameraSize().x * GameMainActivity.GAME_HEIGHT / GameMainActivity.GAME_WIDTH), 0.3f);
                    }
                }
            }
        }
//        if(player.getPos().y>4500 && pauseButton.getMovements().size()==0) {
//            pauseButton.moveSmoothly(pauseButton.getPos(), new PointF(120, -45), 0.5f);
//        }

        if(player.getPos().y>5000 && worldCamera.getMovements().size()==0) {
            worldCamera.setSize(new PointF(0.5f, 0.5f));
            worldCamera.setModeFixed();
            worldCamera.setPos(new PointF(currentLevel.goal.pos.x, currentLevel.goal.pos.y - 7));
            worldCamera.moveSmoothly(worldCamera.getPos(), new PointF(currentLevel.goal.pos.x,
                    currentLevel.goal.pos.y-5), 0.5f, new Caller() {
                @Override
                public void call() {
                    StateManager.changeState(new PlayState(levelName));
                    PlayState play = (PlayState) StateManager.getCurrentState();
                    play.pauseButton.setPos(new PointF(85, -45));
                }
            });
//            StateManager.changeState(new PlayState(levelName));
//            final PlayState play= (PlayState) StateManager.getCurrentState();
//            play.worldCamera.setModeFixed();
//            play.worldCamera.setPos(new PointF(worldCamera.getPos().x, worldCamera.getPos().y + 20));
//            play.worldCamera.moveSmoothly(worldCamera.getPos(), worldCamera.getPos(), 0.5f, new Caller() {
//                @Override
//                public void call() {
//                    worldCamera.setModeFollowLoosely(play.player, 10, 10);
//                }
//            });
        }

        if(levelName.equals("6") && player.getVel().x<0.3 && player.getVel().y<0.3) {
            currentLevel.goal.pos.x+=0.25*60*delta;
            currentLevel.goal.pos.x=Math.min(780, currentLevel.goal.pos.x);
            currentLevel.goal.updateRectangle();
        }

        else if(levelName.equals("7") && player.getTimeSinceLastMove()>0.5) {
            currentLevel.goal.pos.x-=0.25*60*delta;
            currentLevel.goal.pos.x=Math.max(980, currentLevel.goal.pos.x);
            currentLevel.goal.updateRectangle();
        }

        else if(levelName.equals("9") && player.pos.x>1180) {
            currentLevel.goal.pos.x+=2000;
            currentLevel.goal.updateRectangle();
        }
//        if(levelName.equals("9") && player.pos.x> 1240 && player.getTimeSinceLastMove()>3 &&
//                currentLevel.messages.lastElement().getMovements().size()==0) {
//            currentLevel.messages.lastElement().moveSmoothly(currentLevel.messages.lastElement().getPos(),
//                    new PointF(1350, 280), 1);
//        }
    }

//    @Override
//    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
//
//    }


    @Override
    public void onPause() {
        StateManager.pushState(new PauseState(player.getColor(), currentLevel.getBackgroundColor(), levelName));
    }
     @Override
    public void onResume() {
         fixedCamera.moveSmoothly(fixedCamera.getPos(), new PointF(0, 0), 0.2f);
         fixedCamera.moveSmoothly(fixedCamera.getSize(), new PointF(100, 100 * GameMainActivity.GAME_HEIGHT / GameMainActivity.GAME_WIDTH), 0.2f);
     }

}
