package xyz.zzyymaggie.util;

import org.apache.commons.math3.random.RandomDataGenerator;
import xyz.zzyymaggie.model.BaseReadModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomUtil {

    public static final int NUMBER_COUNT = 5;

    public static List<Double> random(BaseReadModel row){
        int pointsLen = row.getPointsLen();
        List<Double> numbers = new ArrayList<>();
        double min = row.getAvg() - row.getDiff();
        double max = row.getAvg() + row.getDiff();
        //generate 2 numbers which are between [min, avg]
        numbers.add(rand(min, row.getAvg(), pointsLen));
        numbers.add(rand(min, row.getAvg(), pointsLen));
        //generate 2 numbers which are between [avg, max]
        numbers.add(rand(row.getAvg(), max, pointsLen));
        numbers.add(rand(row.getAvg(), max, pointsLen));
        // n5 = avg * 5 - (n1 + n2 + n3 + n4)
        // if(n5 is between [min, max], then return
        // else generate again.
        double last = row.getAvg() * NUMBER_COUNT - sum(numbers);
        if(last >= min && last <= max) {
            numbers.add(format(last, pointsLen));
        }else{
            return random(row);
        }
        Collections.shuffle(numbers);
        return numbers;
    }

    public static double rand(double min, double max, int pointsLen) {
        double boundedDouble = new RandomDataGenerator().getRandomGenerator().nextDouble();
        double generatorDouble = min + boundedDouble * (max - min);
        return format(generatorDouble, pointsLen);
    }

    public static double sum(List<Double> numbers) {
        double sum = 0;
        for(int i=0;i<numbers.size();i++){
            sum += numbers.get(i);
        }
        return sum;
    }

    public static double format(double f, int pointsLen) {
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(pointsLen, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

}
