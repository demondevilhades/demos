package test.learn.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class MultiBPTest {

    private MultiBP multiBP;
    private Map<double[], double[]> dataMap = new HashMap<double[], double[]>();
    private Random random = new Random(18980000);

    @Before
    public void before() {
        int value;
        for (int i = 0; i < 500; i++) {
            value = random.nextInt();
            double[] resultData = { 0, 0, 0, 0 };
            if (value > 0) {
                if ((i & 1) == 1) {
                    resultData[0] = 1;
                } else {
                    resultData[1] = 1;
                }
            } else {
                if ((i & 1) == 1) {
                    resultData[2] = 1;
                } else {
                    resultData[3] = 1;
                }
            }
            double[] binary = int2binary(value);
            dataMap.put(binary, resultData);
        }
    }

    private double[] int2binary(int value) {
        double[] binary = new double[32];
        int index = 31;
        do {
            binary[index--] = value & 1;
            value >>>= 1;
        } while (value != 0);
        return binary;
    }

    @Test
    public void test() {
        multiBP = new MultiBP();
        multiBP.initBrain(new int[] { 32, 15, 15, 4 }, new double[] { 0.2, 0.2, 0.2 });
        multiBP.initData(dataMap);
        double train = multiBP.train(500);
        System.out.println(train);
        System.out.println(Arrays.toString(multiBP.getResult(int2binary(3451))));
        System.out.println(Arrays.toString(multiBP.getResult(int2binary(500))));
        System.out.println(Arrays.toString(multiBP.getResult(int2binary(-32767))));
        System.out.println(Arrays.toString(multiBP.getResult(int2binary(-9876))));
    }
}
