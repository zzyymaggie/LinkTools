package xyz.zzyymaggie.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import xyz.zzyymaggie.util.FileUtil;

public class BaseReadModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    protected String avg;

    @ExcelProperty(index = 1)
    protected String diff;

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public float getAvgVal() {
        return Float.valueOf(this.avg);
    }

    public float getDiffVal() {
        return Float.valueOf(this.diff);
    }

    public String toString(){
        return "avg:" + this.avg + ", diff=" + this.diff;
    }

    public int getPointsLen() {
        int len1 = FileUtil.getPointsLength(this.avg);
        int len2 = FileUtil.getPointsLength(this.diff);
        return len1 < len2 ? len2 : len1;
    }
}