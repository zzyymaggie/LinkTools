package xyz.zzyymaggie.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.sun.xml.internal.rngom.parse.host.Base;
import xyz.zzyymaggie.model.BaseReadModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static List<BaseReadModel> simpleReadJavaModel(String filename) {
        List<BaseReadModel> result = new ArrayList<>();
        InputStream inputStream = FileUtil.getResourcesFileInputStream(filename);
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 1, BaseReadModel.class));
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Object item : data){
            BaseReadModel model = (BaseReadModel)item;
            if(model.getDiff() > model.getAvg()) {
                throw new RuntimeException("浮动范围必须小于平均值");
            }
            result.add(model);
        }
        return result;
    }

    public static void print(List<BaseReadModel> datas){
        int i=0;
        for (Object ob:datas) {
//            System.out.println(i++);
            System.out.println(ob);
        }
    }
}
