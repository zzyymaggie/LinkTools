/*
 * @(#)FileUtil.java
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {
    public static String readFile(String filepath){
        String body = null;
        File file = new File(filepath);
        try {
            body = FileUtils.readFileToString(file);
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
}
