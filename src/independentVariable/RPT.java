package independentVariable;

import cn.edu.ustb.www.aviationconsignment.model.Passanger;
import dependentVariable.RPTMeasure;
import logrecorder.RPTLog;
import logrecorder.RTLog;
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

public class RPT {
    private static final int TESTTIMES = 30 ;
    private static final int SEEDS = 30 ;
    private static final double DIVID = TESTTIMES * SEEDS ;
    private static final int NUMOFTESTCASES = 30000;
    private static final String ORIGINAL_PACKAGE = "cn.edu.ustb.www.aviationconsignment";
    private static final int NUMOFMUTANTS = 3 ;

    public void randomPartitionTesting(){
        GenerateTestcases generateTestcases = new GenerateTestcases();//产生测试用例的对象
        
        RPTPartition rptPartition = new RPTPartition();//产生分区的对象
        int[] numOfpartition = {24,7};//记录分区的方式
        RPTLog rptLog = new RPTLog("RPT_log.txt");//日志记录的对象
        TestMethods testMethods = new TestMethods();
        List<String> methodsList = testMethods.getMethods();
        for (int n = 0; n < numOfpartition.length; n++) {//两种分区方式
            long totaltime = 0;
			RPTMeasure rptMeasure = new RPTMeasure();//记录每一个结果的对象
            long start = System.currentTimeMillis();
            for (int i = 0; i < SEEDS; i++) {//种子

                for (int j = 0; j < TESTTIMES; j++) {//每一个序列重复30次
                    int counter = 0 ;
                    int fmeasure = 0 ;
                    List<Bean> beans = new ArrayList<Bean>();
                    beans.clear();
                    List<String> killedMutants = new ArrayList<String>();//记录杀死的变异体
                    killedMutants.clear();
                    MutantSet ms = new MutantSet();//测试用例集对象
                    beans = generateTestcases.generateTestcases(i,NUMOFTESTCASES);//生成测试用例

                    for (int k = 0; k < beans.size();) {
                        int partition = rptPartition.nextPartitionID(numOfpartition[n]);//获取下一个分区的ID
                        Bean bean;
                        //记录临时某一个测试用例杀死的变异体情况
                        List<String> templist = new ArrayList<String>();
                        templist.clear();
                        //获取属于partition中的tc
                        do {
                            bean = beans.get(k++);
                        }while(!rptPartition.isBelongToOneOfPartition(bean,numOfpartition[n],partition));
                        //将测试用例初始化
                        System.out.println("test begin:");
                        counter++;//测试用例的计数器
                        Passanger p1 = new Passanger();
                        p1.setAirlineType(Integer.parseInt(bean.getAirlineType()));
                        p1.setPassangerCategory(Integer.parseInt(bean.getPassangerCatagory()));
                        p1.setBaggageWeight(Double.parseDouble(bean.getBaggageWeight()));
                        p1.setCabinClass(Integer.parseInt(bean.getCabinClass()));
                        p1.setEconomyClassFare(Double.parseDouble(bean.getEconomyClassFare()));
                        try{
                            //开始逐个遍历变异体
                            for (int l = 0; l < ms.size(); l++) {
                                //获取原始程序的实例
                                Class originalClazz = Class.forName(ORIGINAL_PACKAGE+".model."+"BaggageController");
                                Constructor constructor1 = originalClazz.getConstructor(Passanger.class);
                                Object originalInstance = constructor1.newInstance(p1);
                                //获取变异体程序的实例
                                Class mutantClazz = Class.forName(ms.getMutantName(l));
                                Constructor constructor2 = mutantClazz.getConstructor(Passanger.class);
                                Object mutantInstance = constructor2.newInstance(p1);
                                //对一个变异体的所有方法进行遍历
                                for (int m = 0; m < methodsList.size(); m++) {
                                    //获取源程序的方法
                                    Method originalMethod = originalClazz.getMethod(methodsList.get(m),null);
                                    Object originalResult =  originalMethod.invoke(originalInstance,null);
                                    Method mutantMethod = mutantClazz.getMethod(methodsList.get(m),null);
                                    Object mutantResult = mutantMethod.invoke(mutantInstance,null);
                                    if(!originalResult.equals(mutantResult)){//揭示了故障
                                        String[] str = ms.getMutantName(l).split("\\.");
                                        ms.remove(l);
                                        l--;
                                        String temp = str[6];
                                        killedMutants.add(temp);
                                        templist.add(temp);
                                        if (killedMutants.size() == 1){
                                            fmeasure = counter;
                                            rptMeasure.addFmeasure((double)counter);
                                        }else if(killedMutants.size() == NUMOFMUTANTS){
                                            rptMeasure.addTmeasure((double)counter);
                                        }else
                                            rptMeasure.addNFmeasure((double)(counter - fmeasure));
                                        break;
                                    }
                                }

                            }
//                            rptLog.recordProcessInfo("RPT_log.txt",String.valueOf(i),bean.getId(),
//                                    templist,String.valueOf(NUMOFMUTANTS - killedMutants.size()));
                            if (killedMutants.size() >= NUMOFMUTANTS){
                                break;
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
                    }
                }
            }
            long end = System.currentTimeMillis();
            totaltime += (end - start);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            double meanTime = Double.parseDouble(decimalFormat.format(totaltime / DIVID)) ;
            rptLog.recordResult("RPTResult.txt",rptMeasure.getMeanFmeasure(),rptMeasure.getMeanNFmeasure(),
                    rptMeasure.getMeanTmeasure(),rptMeasure.getStandardDevOfFmeasure(),rptMeasure.getStandardDevOfNFmeasure(),
                    rptMeasure.getStandardDevOfTmeasure(),numOfpartition[n],meanTime);
        }
    }

    public static void main(String[] args) {
        RPT rpt = new RPT() ;
        rpt.randomPartitionTesting();
    }
}
