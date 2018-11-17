package xyz.zzyymaggie.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import xyz.zzyymaggie.model.BaseReadModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            i++;
            System.out.println(i + ":" + ob);
        }
    }

    public static void writeV2007() throws IOException {
        OutputStream out = new FileOutputStream("/Users/sophia/Documents/2007.xlsx");
        ExcelWriter writer = EasyExcelFactory.getWriter(out);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("第一个sheet");

        //设置列宽 设置每列的宽度
//        Map columnWidth = new HashMap();
//        columnWidth.put(0,10000);columnWidth.put(1,40000);columnWidth.put(2,10000);columnWidth.put(3,10000);
//        sheet1.setColumnWidthMap(columnWidth);
        //设置表头
//        sheet1.setHead(DataUtil.createTestListStringHead());
        //or 设置自适应宽度
        sheet1.setAutoWidth(Boolean.TRUE);
        writer.write1(DataUtil.createTestListObject(), sheet1);

        writer.finish();
        out.close();
    }

}
