package mutantSet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

public class MutantSet {
    private static final String MUTANT_PACKAGE = "cn.edu.ustb.www.aviationconsignment.mutant";
    private static final String MUTANT_PATH = "cn" + separator+"edu"+separator+"ustb"+separator+"www"+
            separator+"aviationconsignment"+separator+"mutant";
    private static final String CLASS_NAME = "BaggageController";
    private List<Mutant> mutantList;
    public MutantSet() {
        StringBuffer stringBuffer = new StringBuffer(0);//存放变异算子的名字
        StringBuffer mutantFullName = new StringBuffer(0);
        String mutantsFile = System.getProperty("user.dir")+separator+"src"+separator+MUTANT_PATH;
        File file = new File(mutantsFile);
        mutantList = new ArrayList<Mutant>();
        String[] mutantNames = file.list();//得到所有的变异体的名字但是不是完整的名字

        for (int i = 0; i < mutantNames.length; i++) {
            stringBuffer.delete(0,stringBuffer.length());
            stringBuffer.append(mutantNames[i]);//将变异体的名字写入stringBuffer中
            mutantFullName.delete(0,mutantFullName.length());
            mutantFullName.append(MUTANT_PACKAGE);
            mutantFullName.append(".");
            mutantFullName.append(stringBuffer);
            mutantFullName.append(".");
            mutantFullName.append(CLASS_NAME);
            mutantList.add(new Mutant(mutantFullName.toString()));
        }
    }

    public int size(){
        return mutantList.size();
    }

    public String getMutantName(int i){
        return mutantList.get(i).getFullClassName();
    }

    public void remove(int id){
        mutantList.remove(id);
    }

    public static void main(String[] args) {
        MutantSet ms = new MutantSet();
        System.out.println(ms.getMutantName(0));
    }
}
