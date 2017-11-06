package independentVariable;

import cn.edu.ustb.www.aviationconsignment.model.Passanger;
import dependentVariable.RTMeasure;
import logrecorder.RTLog;
import mutantSet.MutantSet;
import mutantSet.TestMethods;
import testcases.Bean;
import testcases.GenerateTestcases;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RT {
    private static final int SEEDS = 30 ;
    private static final int NUMOFTESTCASES = 500;
    private static final String ORIGINAL_PACKAGE = "cn.edu.ustb.www.aviationconsignment";
    private static final int NUMOFMUTANTS = 12 ;
    public void randomTesting(){
        GenerateTestcases generateTestcases = new GenerateTestcases();
        //记录每一个测试序列的测试结果
        RTMeasure rtMeasure = new RTMeasure();
        RTLog rtLog = new RTLog("RT_log.txt");
        long totaltime = 0;
        TestMethods testMethods = new TestMethods();
        List<String> methodsList = testMethods.getMethods();
        for (int i = 0; i < SEEDS; i++) {
            List<Bean> beans = new ArrayList<Bean>();
            //产生测试用例
            beans.clear();
            beans = generateTestcases.generateTestcases(i,NUMOFTESTCASES);
            //获得变异体集
            MutantSet ms = new MutantSet();
            //记录被杀死的变异体
            List<String> killedMutants = new ArrayList<String>();
            killedMutants.clear();
            int fmeasure = 0 ;
            int tmeasure = 0 ;
            int counter = 0 ;
            long start = System.currentTimeMillis();//开始测试的时间
            for (int j = 0; j < beans.size(); j++) {//每一个测试用例要在所有的变异体上执行
                System.out.println("test begin:");
                Bean bean = beans.get(j);//当前测试用例
                counter++;
                //记录临时某一个测试用例杀死的变异体情况
                List<String> templist = new ArrayList<String>();
                //当前测试用例再源程序上执行得到预期结果
                Passanger p1 = new Passanger();
                p1.setAirlineType(Integer.parseInt(bean.getAirlineType()));
                p1.setPassangerCategory(Integer.parseInt(bean.getPassangerCatagory()));
                p1.setBaggageWeight(Double.parseDouble(bean.getBaggageWeight()));
                p1.setCabinClass(Integer.parseInt(bean.getCabinClass()));
                p1.setEconomyClassFare(Double.parseDouble(bean.getEconomyClassFare()));
                try{
                    //开始逐个遍历变异体
                    for (int k = 0; k < ms.size(); k++) {
                        //获取原始程序的实例
                        Class originalClazz = Class.forName(ORIGINAL_PACKAGE+".model."+"BaggageController");
                        Constructor constructor1 = originalClazz.getConstructor(Passanger.class);
                        Object originalInstance = constructor1.newInstance(p1);
                        //获取变异体程序的实例
                        Class mutantClazz = Class.forName(ms.getMutantName(k));
                        Constructor constructor2 = mutantClazz.getConstructor(Passanger.class);
                        Object mutantInstance = constructor2.newInstance(p1);
                        //对一个变异体的所有方法进行遍历
                        for (int l = 0; l < methodsList.size(); l++) {
                            //获取源程序的方法
                            Method originalMethod = originalClazz.getMethod(methodsList.get(l),null);
                            Object originalResult =  originalMethod.invoke(originalInstance,null);
                            Method mutantMethod = mutantClazz.getMethod(methodsList.get(l),null);
                            Object mutantResult = mutantMethod.invoke(mutantInstance,null);
//                            System.out.println("original:"+originalResult+"\t"+"mutant:"+mutantResult);
                            if (!originalResult.equals(mutantResult)){//揭示故障
                                String[] str = ms.getMutantName(k).split("\\.");
                                //删除杀死的变异体
                                ms.remove(k);
                                k--;
                                String temp = str[6];
                                killedMutants.add(temp);
                                templist.add(temp);
                                if (killedMutants.size() == 1){
                                    fmeasure = counter;
                                    rtMeasure.addFmeasure(fmeasure);
                                }else if (killedMutants.size() == NUMOFMUTANTS){
                                    long end = System.currentTimeMillis();
                                    totaltime += (end - start);
                                    tmeasure = counter;
                                    rtMeasure.addNFmeasure(tmeasure);
                                }
                                break;
                            }
                        }
                    }
                    //记录1个测试用例在所有得变异体上执行之后的结果
                    rtLog.recordProcessInfo("RT_log.txt",String.valueOf(i),bean.getId(),
                            templist,String.valueOf(NUMOFMUTANTS - killedMutants.size()));
                    if (killedMutants.size() == NUMOFMUTANTS){
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        double meanTime = Double.parseDouble(decimalFormat.format(totaltime / 60.0)) ;
        rtLog.recordResult("RTResult.txt",rtMeasure.getMeanFmeasure(),rtMeasure.getMeanNFmeasure(),
                rtMeasure.getStandardDevOfFmeasure(),rtMeasure.getStandardDevOfNFmeasure(),meanTime);
    }
    public static void main(String[] args) {
        RT rt = new RT();
        rt.randomTesting();
    }
}
