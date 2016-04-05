package adrians.game.state;

import android.graphics.Color;
import android.graphics.PointF;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;

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
    public MenuState() {
        super();
        System.gc();
//        fixedObjects.addElement(new PushButton(new PointF(0, 0), new PointF(30, 30),
//                Assets.playButtonBitmap, Assets.playButtonBitmap));
////        fixedObjects.addElement(new MessageButton(new PointF(60, 30), new PointF(50, 30), "Witaj!",
////                Color.RED, Color.BLUE, 20, fixedCamera));
//        playButton = (PushButton) fixedObjects.elementAt(0);
        XmlParser.openFile("Layouts/Menu.tmx");
        try {
            int event = XmlParser.p.getEventType();
            String lastGroup=null, lastGroupColor=null, fontColor=null;
            while(event != XmlPullParser.END_DOCUMENT) {
                String name = XmlParser.p.getName();
                switch (event) {
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("objectgroup")) {
                            lastGroup = XmlParser.p.getAttributeValue(null, "name");
                            lastGroupColor = XmlParser.p.getAttributeValue(null, "color");
                            fontColor = XmlParser.p.getAttributeValue(null, "fontColor");
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
                                        Color.parseColor(fontColor), (int) (worldCamera.getScreenDistance(height/2)),
                                        worldCamera));
                                if(buttonName.equals("Play")) {
                                    playButton = (MessageButton) worldObjects.lastElement();
                                } else if(buttonName.equals("About")) {
                                    aboutButton = (MessageButton) worldObjects.lastElement();
                                }
                            } else if(lastGroup.equals("Camera")) {
                                cameraPos = new PointF(x+width/2, y+width*GameMainActivity.GAME_HEIGHT/GameMainActivity.GAME_WIDTH/2);
                                cameraWidth = width/2;
                                worldCamera = new Camera(cameraPos.x, cameraPos.y, cameraWidth, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
                            } else if(lastGroup.equals("Levels")) {
                                worldObjects.addElement(new MessageButton(new PointF(x+width/2, y+height/2),
                                        new PointF(width/2, height/2), buttonName, Color.parseColor(lastGroupColor),
                                        Color.parseColor(fontColor), (int) (worldCamera.getScreenDistance(height*0.5f)), worldCamera));
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
            if(messageButton.gotPushed()) {
                worldCamera.moveSmoothly(worldCamera.getPos(),
                        new PointF(messageButton.getPos().x, messageButton.getPos().y+messageButton.getSize().y*0.7f), 0.5f);
                worldCamera.moveSmoothly(worldCamera.getSize(), new PointF(0.1f, 0.1f), 0.5f, new Caller() {
                    @Override
                    public void call() {
                        StateManager.changeState(new PlayState(messageButton.getMessage()));
                    }
                });
            }
        }
    }
}
