package xyz.zzyymaggie;

import xyz.zzyymaggie.model.BaseReadModel;
import xyz.zzyymaggie.util.ExcelUtil;
import xyz.zzyymaggie.util.RandomUtil;

import java.util.List;

public class Generator {
    public static void main(String[] args) {
        //step1: read base numbers from excel
        List<BaseReadModel> rows = ExcelUtil.simpleReadJavaModel("2007.xlsx");
        ExcelUtil.print(rows);
        //step2: generate random numbers by base numbers
        for(BaseReadModel row: rows) {
            List<Double> list = RandomUtil.random(row);
            System.out.println(list);
        }
        //TODO: step3: write to new excel


    }

}
