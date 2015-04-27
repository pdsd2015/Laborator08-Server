package ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Utilities {
    public static String removeSpaces(String text) {
        String result = new String();
        StringTokenizer stringTokenizer = new StringTokenizer(text," ");
        while (stringTokenizer.hasMoreTokens())
            result += stringTokenizer.nextToken();
        return result;
    }
    
    public static String compress(ArrayList<String> record) {
        String result = record.get(0);
        for (int position = 1; position < record.size(); position++)
            result += " / " + record.get(position);
        return result;
    }

    public static ArrayList<String> decompress(String record) {
        ArrayList<String> result = new ArrayList<>(Arrays.asList(record.split("/")));
        for (int position = 0; position < result.size(); position++)
            result.set(position, result.get(position).trim().replace("'", "\\'"));
        return result;
    }
}
