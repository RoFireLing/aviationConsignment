package dependentVariable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RTMeasure {
    private static final String PATH = "RTResult.txt";
    private List<Integer> Fmeasure;
    private List<Integer> Tmeasure;
    private List<Integer> NFmeasure;
    public RTMeasure(){
        Fmeasure = new ArrayList<Integer>();
        Tmeasure = new ArrayList<Integer>();
        NFmeasure = new ArrayList<Integer>();
    }

    /**
     * 添加fmeasure的值
     * @param item
     */
    public void addFmeasure(int item){
        Fmeasure.add(item);
    }

    /**
     * 添加Tmeasure的值
     * @param item
     */
    public void addTmeasure(int item){
        Tmeasure.add(item);
    }

    /**
     * 添加NFmeasure的值
     * @param item
     */
    public void addNFmeasure(int item) {NFmeasure.add(item);}

    /**
     * 获得fmeasure列表中元素的个数
     * @return
     */
    public int sizeFmeasure(){
        return Fmeasure.size();
    }

    /**
     * 获得Tmeasure列表中元素的个数
     * @return
     */
    public int sizeTmeasure(){
        return Tmeasure.size();
    }

    /**
     * 获得NFmeasure列表中元素的个数
     * @return
     */
    public int sizeNFmeasure(){return NFmeasure.size();}

    /**
     * 获得fmeasure的均值
     * @return
     */
    public String getMeanFmeasure(){
        double sum = 0.0 ;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int i = 0; i < this.Fmeasure.size(); i++) {
            sum = sum + Fmeasure.get(i);
        }
        return decimalFormat.format(sum / Fmeasure.size());
    }

    /**
     * 获得tmeasure的均值
     * @return
     */
    public String getMeanTmeasure(){
        double sum = 0.0 ;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int i = 0; i < this.Tmeasure.size(); i++) {
            sum = sum + Tmeasure.get(i);
        }
        return decimalFormat.format(sum / Tmeasure.size());
    }

    /**
     * 获得NFmeasure的值
     * @return
     */
    public String getMeanNFmeasure(){
        double sum = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        for (int i = 0; i < this.NFmeasure.size(); i++) {
            sum += NFmeasure.get(i);
        }
        return decimalFormat.format(sum / NFmeasure.size());
    }

    /**
     * 返回Fmeasure的标准差
     * @return 标准差
     */
    public String getStandardDevOfFmeasure(){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(Math.sqrt(varianceOfArray(0,this.Fmeasure)));
    }
    /**
     * 返回Tmeasure的标准差
     * @return 标准差
     */
    public String getStandardDevOfTmeasure(){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(Math.sqrt(varianceOfArray(1,this.Tmeasure)));
    }

    public String getStandardDevOfNFmeasure(){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(Math.sqrt(varianceOfArray(2,this.NFmeasure)));
    }


    private double varianceOfArray(int temp,List<Integer> dataArray) {
        double result = 0.0;
        double mean = 0.0;
        if (temp == 0)
            mean = Double.parseDouble(getMeanFmeasure());
        else if (temp == 1)
            mean = Double.parseDouble(getMeanTmeasure());
        else
            mean = Double.parseDouble(getMeanNFmeasure());
        for (int i = 0; i < dataArray.size(); i++) {
            result += Math.pow((dataArray.get(i) - mean),2);
        }
        return result / (dataArray.size() - 1);
    }

    @Override
    public String toString() {
        return "RTMeasure{" +
                "Fmeasure=" + Fmeasure +
                ", Tmeasure=" + Tmeasure +
                ", NFmeasure=" + NFmeasure +
                '}';
    }
}
