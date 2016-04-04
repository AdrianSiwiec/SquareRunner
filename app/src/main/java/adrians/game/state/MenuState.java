package adrians.game.state;

import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import adrians.framework.GameMainActivity;
import adrians.framework.util.XmlParser;
import adrians.framework.util.button.MessageButton;
import adrians.game.camera.Camera;

/**
 * Created by pierre on 17/03/16.
 */
public class MenuState extends State {
//    PushButton playButton;
    MessageButton playButton, aboutButton;
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
            String lastGroup=null, lastGroupColor=null;
            while(event != XmlPullParser.END_DOCUMENT) {
                String name = XmlParser.p.getName();
                switch (event) {
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("objectgroup")) {
                            lastGroup = XmlParser.p.getAttributeValue(null, "name");
                            lastGroupColor = XmlParser.p.getAttributeValue(null, "color");
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
                                        Color.BLACK, (int) (worldCamera.getScreenDistance(height/2)), worldCamera));
                                if(buttonName.equals("Play")) {
                                    playButton = (MessageButton) worldObjects.lastElement();
                                } else if(buttonName.equals("About")) {
                                    aboutButton = (MessageButton) worldObjects.lastElement();
                                }
                            } else if(lastGroup.equals("Camera")) {
                                worldCamera = new Camera(x+width/2, y+width*GameMainActivity.GAME_HEIGHT/GameMainActivity.GAME_WIDTH/2,
                                        width/2, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
                            } else if(lastGroup.equals("Levels")) {
                                worldObjects.addElement(new MessageButton(new PointF(x+width/2, y+height/2),
                                        new PointF(width/2, height/2), buttonName, Color.parseColor(lastGroupColor),
                                        Color.BLACK, (int) (worldCamera.getScreenDistance(height*0.5f)), worldCamera));
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
    }
    @Override
    public void update(float delta) {
        super.update(delta);
//        if(playButton.gotPushed()) {
//            StateManager.changeState(new PlayState());
//        }
        if(playButton.gotPushed()) {
            Log.d("PUSH", "PUSDLK");
            worldCamera.moveSmoothly(worldCamera.getPos(), new PointF(80, 60), 3);
        }
    }
}
