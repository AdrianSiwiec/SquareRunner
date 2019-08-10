package adrians.framework.util;

import android.net.Uri;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import adrians.framework.GameMainActivity;
import adrians.game.state.StateManager;

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

    public static String errorLevelFilename = "Levels/LevelError.tmx";

    public static void openFile(String filename) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
        try {
            stream = GameMainActivity.assets.open(filename);
            p.setInput(stream, null);
        } catch (IOException | XmlPullParserException e) {
            try {
                stream = StateManager.getActivity().getContentResolver().openInputStream(
                        Uri.parse(filename));
                p.setInput(stream, null);

            } catch (FileNotFoundException | XmlPullParserException e1) {
                Log.e("XmlParserError", "Could not load level");
                e.printStackTrace();
                e1.printStackTrace();

                if (filename != errorLevelFilename)
                    openFile(errorLevelFilename);
            }
        }

    }
}
