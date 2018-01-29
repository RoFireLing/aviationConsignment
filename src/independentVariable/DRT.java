package independentVariable;

import cn.edu.ustb.www.aviationconsignment.model.Passanger;
import dependentVariable.DRTMeasure;
import logrecorder.DRTLog;
import mutantSet.MutantSet;
import mutantSet.TestMethods;
import partition.RPTPartition;
import testcases.Bean;
import testcases.GenerateTestcases;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DRT {
    private double[] pd;
    private Random r = new Random();
    private double epsilon;

    private static final int TESTTIMES = 30 ;
    private static final int SEEDS = 30 ;
    private static final double DIVID = TESTTIMES * SEEDS ;
    private static final int NUMOFTESTCASES = 30000;
    private static final String ORIGINAL_PACKAGE = "cn.edu.ustb.www.aviationconsignment";
    private static final int NUMOFMUTANTS = 3 ;
    public DRT() {}

    /**
     * 获取第一个分区的ID
     * @return 返回第一个分区的ID
     */
    private int nextPartition(){
        int npi;
        double rd = r.nextDouble();
        double sum = 0.0;
        npi = -1;
        do {
            ++npi;
            sum += pd[npi];
        } while (rd >= sum && npi < pd.length);
        return npi;
    }

    /**
     * 调整概率分布
     * @param prePartitionIndex 当前分区的编号
     * @param isKillMutant 当前分区是否杀死了变异体
     */
    private void adjustPartition(int prePartitionIndex,boolean isKillMutant){
        if (isKillMutant) {// 上次测试用例杀死了变异体
            double sum = 0;
            for (int i = 0; i < pd.length; ++i) {// 减小其他分区被选中的概率
                if (i != prePartitionIndex) {
                    pd[i] -= epsilon / (pd.length - 1);
                    if (pd[i] < 0)
                        pd[i] = 0;
                    sum += pd[i];
                }
            }
            pd[prePartitionIndex] = 1 - sum;// 增大上次选中分区被选中的概率
        } else {// 上次测试用例未杀死变异体
            if (pd[prePartitionIndex] < epsilon) {
                for (int i = 0; i < pd.length; ++i) {
                    if (i != prePartitionIndex) {
                        pd[i] += pd[prePartitionIndex] / (pd.length - 1);
                    }
                }
                pd[prePartitionIndex] = 0;
            } else {
                for (int i = 0; i < pd.length; ++i) {
                    if (i != prePartitionIndex) {
                        pd[i] += epsilon / (pd.length - 1);
                    }
                }
                pd[prePartitionIndex] -= epsilon;
            }
        }
    }


    public void dynamicRandomTesting(){
        GenerateTestcases generateTestcases = new GenerateTestcases();//产生测试用例的对象
        DRTLog drtLog = new DRTLog("DRT_log.txt");
        RPTPartition rptPartition = new RPTPartition();
        int[] numOfpartition = {24,7};
//        int[] numOfpartition = {24};
        double[] parameters = {0.00001,0.00005,0.0001,0.0005,0.001,0.005,0.01,0.05,0.1,0.2,0.3,0.4,0.5};
//        double[] parameters = {0.001};
        TestMethods testMethods = new TestMethods();
        List<String> methodsList = testMethods.getMethods();
        for (int i = 0; i < numOfpartition.length; i++) {//分区方式
            for (int j = 0; j < parameters.length; j++) {

                List<Long> falltime = new ArrayList<Long>(); //里面存放杀死第一个变异体的所有时间

                List<Long> f2alltime = new ArrayList<Long>(); //里面存放杀死第二个变异体的所有时间

                List<Long> talltime = new ArrayList<Long>(); //里面存放杀死第二个变异体的所有时间

                epsilon = parameters[j];
                DRTMeasure drtMeasure = new DRTMeasure();


                for (int k = 0; k < SEEDS; k++) {//每一个种子
                    for (int l = 0; l < TESTTIMES; l++) {//测试重复次数
                        int counter = 0 ;//测试用例的计数器
                        int fmeasure = 0 ;//记录每一次的fmeasure的结果
                        List<Bean> beans = new ArrayList<Bean>();
                        List<String> killedMutants = new ArrayList<String>();//记录杀死的变异体
                        MutantSet ms = new MutantSet();//测试用例集对象
                        beans.clear();
                        beans = generateTestcases.generateTestcases(i,NUMOFTESTCASES);//生成测试用例
                        //初始化pd
                        pd = new double[numOfpartition[i]];
                        for (int m = 0; m < pd.length; m++) {
                            pd[m] = 1.0 / numOfpartition[i];
                        }

                        long starttemp = System.currentTimeMillis();
                        long ftime = 0;

                        for (int m = 0; m < beans.size();) {//开始测试
                            int partition = nextPartition();
                            Bean bean;
                            //记录临时某一个测试用例杀死的变异体情况
                            List<String> templist = new ArrayList<String>();
                            templist.clear();
                            //获取属于partition中的tc
                            do {
                                bean = beans.get(m++);
                            }while(!rptPartition.isBelongToOneOfPartition(bean,numOfpartition[i],partition));
                            //将测试用例初始化
                            System.out.println("test begin:");
                            counter++;//测试的测试用例数目自增
                            Passanger p1 = new Passanger();
                            p1.setAirlineType(Integer.parseInt(bean.getAirlineType()));
                            p1.setPassangerCategory(Integer.parseInt(bean.getPassangerCatagory()));
                            p1.setBaggageWeight(Double.parseDouble(bean.getBaggageWeight()));
                            p1.setCabinClass(Integer.parseInt(bean.getCabinClass()));
                            p1.setEconomyClassFare(Double.parseDouble(bean.getEconomyClassFare()));
                            String tc = bean.getAirlineType() + bean.getCabinClass() + bean.getPassangerCatagory();
                            try{
                                for (int n = 0; n < ms.size(); n++) {
                                    //获取原始程序的实例
                                    Class originalClazz = Class.forName(ORIGINAL_PACKAGE+".model."+"BaggageController");
                                    Constructor constructor1 = originalClazz.getConstructor(Passanger.class);
                                    Object originalInstance = constructor1.newInstance(p1);
                                    //获取变异体程序的实例
                                    Class mutantClazz = Class.forName(ms.getMutantName(n));
                                    Constructor constructor2 = mutantClazz.getConstructor(Passanger.class);
                                    Object mutantInstance = constructor2.newInstance(p1);
                                    //对一个变异体的所有方法进行遍历
                                    for (int o = 0; o < methodsList.size(); o++) {
                                        Method originalMethod = originalClazz.getMethod(methodsList.get(o),null);
                                        Object originalResult =  originalMethod.invoke(originalInstance,null);
                                        Method mutantMethod = mutantClazz.getMethod(methodsList.get(o),null);
                                        Object mutantResult = mutantMethod.invoke(mutantInstance,null);
                                        if (!mutantResult.equals(originalResult)){//揭示了故障
                                            String[] str = ms.getMutantName(n).split("\\.");
                                            ms.remove(n);
                                            n--;
                                            String temp = str[6];
                                            killedMutants.add(temp);//只获得变异体的名字去掉路径
                                            templist.add(temp);
                                            if (killedMutants.size() == 1){
                                                long ftimeTemp = System.currentTimeMillis();
                                                ftime = ftimeTemp;
                                                falltime.add(ftimeTemp - starttemp);
                                                fmeasure = counter;
                                                drtMeasure.addFmeasure(counter);
                                            }else if(killedMutants.size() == NUMOFMUTANTS){
                                                long ttimeTemp = System.currentTimeMillis();
                                                talltime.add(ttimeTemp - starttemp);
                                                drtMeasure.addTmeasure(counter);
                                            }else{
                                                long f2timeTemp = System.currentTimeMillis();
                                                f2alltime.add(f2timeTemp - ftime);
                                                drtMeasure.addNFmeasure(counter - fmeasure);
                                            }
                                            break;
                                        }
                                    }

                                }//mutants
                                if (templist.size() != 0){//表示改测试用例揭示软件中的故障调整概率分布
                                    adjustPartition(partition,true);
                                }else {//表示改测试用例没有揭示软件中的故障调整概率分布
                                    adjustPartition(partition,false);
                                }
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
//                            drtLog.recordProcessInfo("DRT_log.txt",String.valueOf(k),bean.getId(),
//                                    templist,String.valueOf(NUMOFMUTANTS - killedMutants.size()),String.valueOf(partition),String.valueOf(tc));
                            if (killedMutants.size() >= NUMOFMUTANTS){
                                break;
                            }
                        }//tc
                    }//circle
                }//seed
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                //计算平均时间
                long ftotaltime = 0;
                for (int k = 0; k < falltime.size(); k++) {
                    ftotaltime += falltime.get(k);
                }
                double meanfTime = Double.parseDouble(decimalFormat.format(ftotaltime / DIVID)) ;

                long f2totaltime = 0;
                for (int k = 0; k < f2alltime.size(); k++) {
                    f2totaltime += f2alltime.get(k);
                }
                double meanf2Time = Double.parseDouble(decimalFormat.format(f2totaltime / DIVID)) ;

                long ttotaltime = 0;

                for (int k = 0; k < talltime.size(); k++) {
                    ttotaltime += talltime.get(k);
                }
                double meantime = Double.parseDouble(decimalFormat.format(ttotaltime / DIVID)) ;

                drtLog.recordResult("DRTResult.xls",drtMeasure.getMeanFmeasure(),drtMeasure.getMeanNFmeasure(),
                        drtMeasure.getMeanTmeasure(),drtMeasure.getStandardDevOfFmeasure(),drtMeasure.getStandardDevOfNFmeasure(),
                        drtMeasure.getStandardDevOfTmeasure(),numOfpartition[i],parameters[j], meanfTime,meanf2Time,meantime);
            }//parameters
        }//numofpartitions
    }

    public static void main(String[] args) {
        DRT drt = new DRT();
        drt.dynamicRandomTesting();
    }


}
