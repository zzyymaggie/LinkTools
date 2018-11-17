package xyz.zzyymaggie.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataUtil {
    public static List<List<Object>> createTestListObject() {
        List<List<Object>> object = new ArrayList<List<Object>>();
        for (int i = 0; i < 100; i++) {
            List<Object> da = new ArrayList<Object>();
            da.add("字符串"+i);
            da.add(Long.valueOf(187837834l+i));
            da.add(Integer.valueOf(2233+i));
            da.add(Double.valueOf(2233.00+i));
            da.add(Float.valueOf(2233.0f+i));
            da.add(new Date());
            da.add(new BigDecimal("3434343433554545"+i));
            da.add(Short.valueOf((short)i));
            object.add(da);
        }
        return object;
    }

    public static List<List<String>> createTestListStringHead(){
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headCoulumn1 = new ArrayList<String>();
        List<String> headCoulumn2 = new ArrayList<String>();
        List<String> headCoulumn3 = new ArrayList<String>();
        List<String> headCoulumn4 = new ArrayList<String>();
        List<String> headCoulumn5 = new ArrayList<String>();

        headCoulumn1.add("第一列");
        headCoulumn2.add("第二列");

        headCoulumn3.add("第三列");
        headCoulumn4.add("第四列");
        headCoulumn5.add("第五列");

        head.add(headCoulumn1);
        head.add(headCoulumn2);
        head.add(headCoulumn3);
        head.add(headCoulumn4);
        head.add(headCoulumn5);
        return head;
    }
    public static List<List<String>> createHead0(){
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headCoulumn1 = new ArrayList<String>();
        List<String> headCoulumn2 = new ArrayList<String>();
        headCoulumn1.add("平均值");
        headCoulumn2.add("浮动范围");

        head.add(headCoulumn1);
        head.add(headCoulumn2);
        return head;
    }
}
