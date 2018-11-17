package xyz.zzyymaggie.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileUtil {
    public static String USER_DIR = System.getProperty("user.dir");
    static {
        if(!USER_DIR.endsWith("/")) {
            USER_DIR += "/";
        }
    }

    public static InputStream getResourcesFileInputStream(String fileName) {
        InputStream in = null;
        try {
            in = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static int getPointsLength(String str){
        int len = 0;
        int index = str.lastIndexOf(".");
        if(index > -1) {
            len = str.substring(index + 1).length();
        }
        return len;
    }
}
