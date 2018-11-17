package xyz.zzyymaggie.util;

import xyz.zzyymaggie.model.BaseReadModel;

import java.util.List;

import static org.junit.Assert.*;

public class RandomUtilTest {

    @org.junit.Test
    public void random() {
        BaseReadModel model = new BaseReadModel();
        model.setAvg("12.5");
        model.setDiff("5");
        List<Double> doubleList = RandomUtil.random(model);
        System.out.println(doubleList);
        System.out.println(RandomUtil.sum(doubleList));
    }

    @org.junit.Test
    public void rand() {
        double number = RandomUtil.rand(7.5, 12.5, 1);
        System.out.println(number);
    }
}