package testcases;


import java.text.DecimalFormat;
import java.util.*;



/**
 * 产生testcases的类
 * @author phantom
 */
public class GenerateTestcases {
    private String path ;



    /**
     * 该方法是产生测试用例的主要方法
     * @param seed 产生测试用例的种子
     * airlineType
     *            航班类型(0，表示国内航班；1，表示国际航班)
     * cabinClass
     *            座舱等级(0,表示经济舱；1，表示商务舱；2，表示头等舱)
     * passangerCatagory
     *            乘客类型(0，表示婴儿；1，表示儿童；2表示学生；3表示成人)
     * economyClassFare
     *            经济舱票价()
     * baggageWeight
     *            携带行李总重量()
     */
    public List<Bean> generateTestcases(int seed,int numOftestcases){
        Random random = new Random(seed);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        List<Bean> tempbeans = new ArrayList<Bean>();
        for (int i = 0; i < numOftestcases; i++) {
            String airlineType = String.valueOf(random.nextInt(2));
            String cabinClass = String.valueOf(random.nextInt(3));
            String passangerCatagory = String.valueOf(random.nextInt(4));
            String economyClassFare = String.valueOf(decimalFormat.format(random.nextDouble() * 5000 + 500));
            String baggageWeight = String.valueOf(decimalFormat.format(random.nextDouble() * 60));
            String iD = String.valueOf(i+1);
            //以上产生了一个测试用例的所有输入，接下来形成一个测试用例
            Bean bean = new Bean();
            bean.setvariables(iD,airlineType,cabinClass,passangerCatagory,economyClassFare,baggageWeight);
            tempbeans.add(bean);
        }
        return tempbeans;

    }
}
