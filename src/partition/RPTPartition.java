package partition;

import testcases.Bean;

import java.io.*;
import java.util.Random;

import static java.io.File.separator;

public class RPTPartition {
    public RPTPartition() {}
    private static final int PARTITION1 = 24;
    private static final int PARTITION2 = 7;

    /**
     * 获得下一个分区的分区号
     * @param numOfpartitions 分区的数量
     * @return 下一个分区的ID
     */
    public int nextPartitionID(int numOfpartitions){
        double[] pd = new double[numOfpartitions];
        for (int i = 0; i < pd.length; i++) {
            pd[i] = 1.0 / numOfpartitions;
        }
        Random random = new Random();
        int npi;
        double rd = random.nextDouble();
        double sum = 0.0;
        npi = -1;
        do {
            ++npi;
            sum += pd[npi];
        } while (rd >= sum && npi < pd.length - 1);
        return npi;
    }

    /**
     * 判断当前测试用例是否在选择的分区之中
     * @param bean 当前的测试用例
     * @param numOfPartitions 分区的方式
     * @param partition 选择的分区
     * @return 是否存在选中的分区之中
     */
    public boolean isBelongToOneOfPartition(Bean bean,int numOfPartitions,int partition){
        int airlineType = Integer.parseInt(bean.getAirlineType());//获得当前航班类型
        int cabinClass = Integer.parseInt(bean.getCabinClass());//获得当前座舱等级
        int passagnerCategory = Integer.parseInt(bean.getPassangerCatagory());//获得当前乘客类型
        String[] p24 = {"000","001","002","003","010","011","012","013","020","021","022","023",
                "100","101","102","103","110","111","112","113","120","121","122","123"};
        String str = String.valueOf(airlineType)+String.valueOf(cabinClass)+String.valueOf(passagnerCategory);
        if (numOfPartitions == 24){
            if (p24[partition].equals(str)){
                return true;
            }else
                return false;
        }else {
            boolean flag = false;
            switch (partition){
                case 0:
                    if(passagnerCategory == 0)
                        flag = true;
                    break;
                case 1:
                    if (airlineType==0 && cabinClass==0 && (passagnerCategory == 1 ||passagnerCategory == 2 ||passagnerCategory == 3))
                        flag = true;
                    break;
                case 2:
                    if (airlineType==0 && cabinClass==1 && (passagnerCategory == 1 ||passagnerCategory == 2 ||passagnerCategory == 3))
                        flag = true;
                    break;
                case 3:
                    if (airlineType==0 && cabinClass==2 && (passagnerCategory == 1 ||passagnerCategory == 2 ||passagnerCategory == 3))
                        flag = true;
                    break;
                case 4:
                    if (airlineType==1 && cabinClass==0 && (passagnerCategory == 1 ||passagnerCategory == 3))
                        flag = true;
                    break;
                case 5:
                    if ((airlineType == 1 && cabinClass == 0 && passagnerCategory ==2) ||(airlineType==0 && cabinClass==2 && (passagnerCategory == 1 ||passagnerCategory == 2 ||passagnerCategory == 3)))
                        flag = true;
                    break;
                case 6:
                    if (airlineType==1 && cabinClass==2 && (passagnerCategory == 1 ||passagnerCategory == 2 ||passagnerCategory == 3))
                        flag = true;
                    break;
            }
            return flag;
        }
    }

    public static void main(String[] args) {
        RPTPartition rptPartition = new RPTPartition();
        for (int i = 0; i < 1; i++) {
            System.out.println(rptPartition.nextPartitionID(24));
        }
    }





}
