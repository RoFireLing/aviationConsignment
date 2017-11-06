package executor;

import independentVariable.DRT;
import independentVariable.RPT;
import independentVariable.RT;

/**
 * 测试的主类
 */
public class Main {
    public static void main(String[] args) {
        DRT drt = new DRT();
        RPT rpt = new RPT();
        RT rt = new RT();
        rt.randomTesting();
        rpt.randomPartitionTesting();
        drt.dynamicRandomTesting();
    }
}
