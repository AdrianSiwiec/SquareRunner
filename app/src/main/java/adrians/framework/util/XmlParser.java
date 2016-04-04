package adrians.framework.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import adrians.framework.GameMainActivity;

/**
 * Created by pierre on 03/04/16.
 */
public class XmlParser {
    private static XmlPullParserFactory xmlPullParserFactory;
    public static XmlPullParser p;
    private static InputStream stream;
    static {
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            p = xmlPullParserFactory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public static void openFile(String filename) {
        try {
            stream = GameMainActivity.assets.open(filename);
            p.setInput(stream, null);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

    }
}
