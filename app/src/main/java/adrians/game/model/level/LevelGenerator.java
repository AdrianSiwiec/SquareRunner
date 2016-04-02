package adrians.game.model.level;

import android.graphics.Color;
import android.graphics.PointF;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import adrians.framework.GameMainActivity;
import adrians.game.model.gameObject.PhysicalRectangle;

/**
 * Created by pierre on 09/03/16.
 */
public class LevelGenerator {
    private static XmlPullParserFactory xmlPullParserFactory;
    private static XmlPullParser parser;
    private static InputStream stream;
    static {
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            parser = xmlPullParserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public static Level generateLevel(String levelName) {
        Level level = new Level();
        try {
            stream = GameMainActivity.assets.open("Levels/Level"+levelName+".tmx");
        } catch (IOException e) {
            e.printStackTrace();
            return level;
        }
        try {
            parser.setInput(stream, null);
            int event = parser.getEventType();
            String lastGroup=null, lastGroupColor = null;
            while(event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (event) {
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("objectgroup")) {
                            lastGroup = parser.getAttributeValue(null, "name");
                            lastGroupColor = parser.getAttributeValue(null, "color");
                        } else if(name.equals("object")) {
                            float x, y, width, height;
                            x = Float.valueOf(parser.getAttributeValue(null, "x"));
                            y = Float.valueOf(parser.getAttributeValue(null, "y"));
                            width = Float.valueOf(parser.getAttributeValue(null, "width"));
                            height = Float.valueOf(parser.getAttributeValue(null, "height"));
                            if(lastGroup.equals("Walls")) {
                                level.addRectangle(new PhysicalRectangle(new PointF(x + width / 2, y + height / 2),
                                        new PointF(width / 2, height / 2), Color.parseColor(lastGroupColor)));
                            } else if(lastGroup.equals("Player")) {
                                level.setPlayerPos(new PointF(x+width/2, y+height/2));
                                level.setPlayerSize(new PointF(width / 2, height / 2));
                                level.setPlayerColor(Color.parseColor(lastGroupColor));
                            } else if(lastGroup.equals("Camera")) {
                                level.setCameraPos(new PointF(x+width/2, y+height/2));
                                level.setCameraSize(new PointF(width/2, height/2));
                            }
                        }
                        break;

                }
                event = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return level;
    }
}
