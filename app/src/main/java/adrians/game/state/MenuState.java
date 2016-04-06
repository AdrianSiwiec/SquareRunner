package adrians.game.state;

import android.graphics.Color;
import android.graphics.PointF;
//import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

import adrians.framework.GameMainActivity;
import adrians.framework.util.Caller;
import adrians.framework.util.XmlParser;
import adrians.framework.util.button.MessageButton;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 17/03/16.
 */
public class MenuState extends State {
//    PushButton playButton;
    private MessageButton playButton, aboutButton;
    private PointF cameraPos, levelCameraPos;
    private float cameraWidth;
    private LinkedList<MessageButton> levelButtons = new LinkedList<>();
    private LinkedList<MessageButton> backButtons = new LinkedList<>();
    private Set<String> unlocked = GameMainActivity.getUnlocked();
    private int buttonBackgroundColor;
    public MenuState() {
        super();
        System.gc();
//        fixedObjects.addElement(new PushButton(new PointF(0, 0), new PointF(30, 30),
//                Assets.playButtonBitmap, Assets.playButtonBitmap));
////        fixedObjects.addElement(new MessageButton(new PointF(60, 30), new PointF(50, 30), "Witaj!",
////                Color.RED, Color.BLUE, 20, fixedCamera));
//        playButton = (PushButton) fixedObjects.elementAt(0);
        if(!unlocked.contains("1")) {
            unlocked.add("1");
            GameMainActivity.setUnlocked(unlocked);
        }
        XmlParser.openFile("Layouts/Menu.tmx");
        try {
//            Log.d("Unlocked", unlocked.toString());
            int event = XmlParser.p.getEventType();
            String lastGroup=null, lastGroupColor=null, fontColor="#b2733f";
            while(event != XmlPullParser.END_DOCUMENT) {
                String name = XmlParser.p.getName();
                switch (event) {
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("objectgroup")) {
                            lastGroup = XmlParser.p.getAttributeValue(null, "name");
                            lastGroupColor = XmlParser.p.getAttributeValue(null, "color");
                            if(lastGroup.equals("Levels")) {
                                buttonBackgroundColor = Color.parseColor(lastGroupColor);
                            }
                        } else if(name.equals("object")) {
                            float x, y, width, height;
                            String buttonName;
                            x = Float.valueOf(XmlParser.p.getAttributeValue(null, "x"));
                            y = Float.valueOf(XmlParser.p.getAttributeValue(null, "y"));
                            width = Float.valueOf(XmlParser.p.getAttributeValue(null, "width"));
                            height = Float.valueOf(XmlParser.p.getAttributeValue(null, "height"));
                            buttonName = XmlParser.p.getAttributeValue(null, "name");
                            if(lastGroup.equals("Buttons")) {
                                worldObjects.addElement(new MessageButton(new PointF(x+width/2, y+height/2),
                                        new PointF(width/2, height/2), buttonName, Color.parseColor(lastGroupColor),
                                        Color.parseColor(fontColor), (int) (worldCamera.getScreenDistance(height/2))
                                ));
                                if(buttonName.equals("Play")) {
                                    playButton = (MessageButton) worldObjects.lastElement();
                                } else if(buttonName.equals("About")) {
                                    aboutButton = (MessageButton) worldObjects.lastElement();
                                } else if(buttonName.equals("Back")) {
                                    backButtons.addLast((MessageButton) worldObjects.lastElement());
                                }
                            } else if(lastGroup.equals("Camera")) {
                                cameraPos = new PointF(x+width/2, y+width*GameMainActivity.GAME_HEIGHT/GameMainActivity.GAME_WIDTH/2);
                                cameraWidth = width/2;
                                worldCamera = new Camera(cameraPos.x, cameraPos.y, cameraWidth, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
                            } else if(lastGroup.equals("Levels")) {
                                if(!unlocked.contains(buttonName)) {
                                    lastGroupColor = fontColor;
                                }
                                worldObjects.addElement(new MessageButton(new PointF(x+width/2, y+height/2),
                                        new PointF(width/2, height/2), buttonName, Color.parseColor(lastGroupColor),
                                        Color.parseColor(fontColor), (int) (worldCamera.getScreenDistance(height*0.5f))));
                                levelButtons.addLast((MessageButton) worldObjects.lastElement());
                            } else if(lastGroup.equals("LevelsCamera")) {
                                levelCameraPos = new PointF(x+width/2, y+height/2);
                            } else if(lastGroup.equals("Background")) {
                                backgroundColor = Color.parseColor(lastGroupColor);
                            }
                        }
                        break;

                }
                event = XmlParser.p.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
//        worldCamera.setModeFollow(worldObjects.elementAt(0));
        PointF oldPos = new PointF(worldCamera.getPos().x, worldCamera.getPos().y);
        worldCamera.setPos(new PointF(oldPos.x-200, oldPos.y));
        worldCamera.moveSmoothly(worldCamera.getPos(), oldPos, 0.3f);
    }
    @Override
    public void update(float delta) {
        super.update(delta);
        if(playButton.gotPushed()) {
            worldCamera.moveSmoothly(worldCamera.getPos(), levelCameraPos, 0.5f);
        }
        for(final MessageButton messageButton: levelButtons) {
            if(messageButton.gotPushed() && unlocked.contains(messageButton.getMessage())) {
                worldCamera.moveSmoothly(worldCamera.getPos(),
                        new PointF(messageButton.getPos().x, messageButton.getPos().y+messageButton.getSize().y*0.9f), 0.5f);
                worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(0.01f, 0.01f), 0.5f, new Caller() {
                    @Override
                    public void call() {
                        StateManager.pushState(new PlayState(messageButton.getMessage()));
                    }
                });
            }
        }
        for(MessageButton messageButton: backButtons) {
            if(messageButton.gotPushed()) {
                worldCamera.moveSmoothly(worldCamera.getPos(), cameraPos, 0.5f);
            }
        }
        if(aboutButton.gotPushed()) {
            worldCamera.moveSmoothly(worldCamera.getPos(), new PointF(worldCamera.getPos().x, worldCamera.getPos().y+140), 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.gc();
        unlocked = GameMainActivity.getUnlocked();
        for(MessageButton button : levelButtons) {
            if(unlocked.contains(button.getMessage())) {
                button.setBackgroundColor(buttonBackgroundColor);
//                Log.d("Unlocked", button.getMessage());
            }
        }
        worldCamera.moveSmoothly(worldCamera.getPos(), levelCameraPos, 0.5f);
        worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(cameraWidth, cameraWidth * GameMainActivity.GAME_HEIGHT / GameMainActivity.GAME_WIDTH), 0.5f);
    }
}
