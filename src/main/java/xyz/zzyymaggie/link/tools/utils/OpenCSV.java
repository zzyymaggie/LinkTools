/*
 * @(#)FileUtil.java
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter; 

public class OpenCSV {
    public static String dir = System.getProperty("user.dir") + "/output";

    public void CSVReadAll(String csvFile) throws Exception {
        File csv = new File(dir, csvFile);

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csv), "GBK"), ',');
        String [] header = reader.readNext(); 
        for (String s : header) {
            System.out.print(s + ",");
        }
        System.out.println("");

        List<String[]> list = reader.readAll();
        System.out.println(list.get(0)[0]);
    }
    
    public static void CSVWrite(String[] header, List<String[]> allElements, String csvFile) {
        File file = new File(dir, csvFile);  
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
             file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Writer writer = new FileWriter(file);
            CSVWriter csvWriter = new CSVWriter(writer, ',');
            csvWriter.writeNext(header);
            csvWriter.writeAll(allElements);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
