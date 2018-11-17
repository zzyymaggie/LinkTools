package xyz.zzyymaggie;

import xyz.zzyymaggie.model.BaseReadModel;
import xyz.zzyymaggie.util.ExcelUtil;
import xyz.zzyymaggie.util.FileUtil;
import xyz.zzyymaggie.util.RandomUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void main(String[] args) {
        String dir = FileUtil.USER_DIR;
        List<List<Object>> datas = new ArrayList<>();
        List<List<Object>> originData = new ArrayList<>();
        //step1: read base numbers from excel
        List<BaseReadModel> rows = ExcelUtil.simpleReadJavaModel(dir + "input.xlsx");
        ExcelUtil.print(rows);
        //step2: generate random numbers by base numbers
        for(BaseReadModel row: rows) {
            //收集表一数据
            List<Object> data0 = new ArrayList<>();
            data0.add(row.getAvgVal());
            data0.add(row.getDiffVal());
            originData.add(data0);
            //收集表二数据
            List<Object> data = new ArrayList<>();
            List<Double> list = RandomUtil.random(row);
            for(Double item: list){
                data.add(item);
            }
            datas.add(data);
        }
        //step3: write to new excel
        try {
            ExcelUtil.writeData(dir + "结果.xlsx", datas, originData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
