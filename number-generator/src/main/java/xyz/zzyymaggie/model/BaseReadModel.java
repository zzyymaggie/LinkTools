package xyz.zzyymaggie.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class BaseReadModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    protected Float avg;

    @ExcelProperty(index = 1)
    protected Integer diff;

    public Float getAvg() {
        return avg;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
    }

    public Integer getDiff() {
        return diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }

    public String toString(){
        return "avg:" + this.avg + ", diff=" + this.diff;
    }

    public int getPointsLen() {
        int len = 1;
        String str = String.valueOf(this.avg);
        int index = str.lastIndexOf(".");
        if(index > -1) {
            len = str.substring(index + 1).length();
        }
        return len;
    }
}