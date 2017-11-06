package logrecorder;

import independentVariable.DRT;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.io.File.separator;

public class DRTLog {
    public static final int COLUMN = 7 ;
    public DRTLog(String filename) {
        createFile(filename);
    }
    /**
     * 记录RT再测试过程中的信息
     * @param fileName 文件名字
     * @param seed 种子
     * @param TCId 测试用例id
     * @param killedmutants 杀死的变异体的名字
     * @param numOfUnkilledMutants 剩下变异体的名字
     */
    public void recordProcessInfo(String fileName, String seed, String TCId,
                                  List<String> killedmutants, String numOfUnkilledMutants,String partition,String tc){
        String path = System.getProperty("user.dir") + separator + "logs"+separator+fileName;
        File file = new File(path);
        if (!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String mutantskilled = "";
        for (int i = 0; i < killedmutants.size(); i++) {
            mutantskilled += killedmutants.get(i);
        }
        String cotent = seed + "\t\t\t\t" + TCId + "\t\t\t\t" +
                "\t\t\t" + partition + "\t\t\t" + tc + "\t\t\t" + numOfUnkilledMutants + "\t\t\t\t\t\t"+ mutantskilled;
        try{
            FileWriter fileWriter = new FileWriter(path,true);
            fileWriter.write("\n"+cotent);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 向xls文件中记录测试结果
     * @param filename 文件的名字
     * @param fmeasure f值
     * @param tmeasure t值
     * @param sdrfmeasure f的标准差
     * @param sdrtmeasure t的标准差
     * @param numOfpartitions 分区的数目
     * @param epsilon 参数
     */
    public void recordResult(String filename, String fmeasure, String tmeasure, String sdrfmeasure,
                             String sdrtmeasure, int numOfpartitions, double epsilon,double time){
        String path = System.getProperty("user.dir") + separator + "result" + separator + filename;
        File file = new File(path);
        try{
            //设置字体的格式
            WritableFont font = new WritableFont(WritableFont.ARIAL,14);
            WritableCellFormat wcf = new WritableCellFormat(font);//设置字体的大小
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);//设置线条
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//设置垂直对齐
            wcf.setAlignment(Alignment.CENTRE);//设置水平对其
            wcf.setWrap(false);//文字是否换行

            if (!file.exists())
                createXLS(filename,wcf);

            //开始向文件中写入内容
            Workbook originalWorkbook = Workbook.getWorkbook(file);//获得原始文件中的内容
            //在原来的基础上写入内容
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file,originalWorkbook);
            //获得sheet
            WritableSheet sheet = writableWorkbook.getSheet(0);
            //获得之前sheet写入的位置
            int temp = sheet.getRows();
            String[] elements = {String.valueOf(numOfpartitions),String.valueOf(epsilon),fmeasure,sdrfmeasure,tmeasure,sdrtmeasure,String.valueOf(time)};
            //追加数据
            for (int i = 0; i < COLUMN; i++) {
                sheet.addCell(new Label(i,temp,elements[i],wcf));
            }
            //写入并关闭工作簿
            originalWorkbook.close();
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件并且初始话抬头
     * @param fileName 文件的名字
     */
    public void createFile(String fileName){
        String path = System.getProperty("user.dir")+ separator + "logs"+ separator + fileName;
        File file = new File(path);
        if (file.exists()){
            try{
                file.delete();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //向文件中写入抬头
        try{
            FileWriter fileWriter = new FileWriter(file);
            String cotent = "seed"+"\t\t\t" + "TC_ID"+"\t\t\t"+"partirion" + "\t\t\t" + "tc" + "\t\t\t"+"numOfUnkilledMutants"+"\t\t\t"+"killedMutants";
            fileWriter.write(cotent);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void createXLS(String filename,WritableCellFormat wcf){
        String path = System.getProperty("user.dir") + separator + "result" + separator + filename;
        File file = new File(path);
        try{
            file.createNewFile();
            //向新创建的文件中添加表头
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
            WritableSheet sheet = writableWorkbook.createSheet("sheet",0);
            String[] elements = {"partitions","epsilon","Fmeasure","sdrFmeasure","Tmeasure","sdrTmeasure","time"};
            for (int i = 0; i < COLUMN; i++) {
                sheet.addCell(new Label(i,0,elements[i],wcf));
                sheet.setColumnView(i,20);
            }
            //写入并关闭工作簿
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DRTLog drtLog = new DRTLog("test.txt");
        drtLog.createFile("test.txt");
    }


}
