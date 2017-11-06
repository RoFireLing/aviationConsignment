package mutantSet;

import java.util.ArrayList;
import java.util.List;

public class TestMethods {
    private List<String> methods;//原始待测程序中所有方法名
    public TestMethods() {
        methods = new ArrayList<String>();
        methods.add("takeAlongNum");
        methods.add("takeAlongWeight");
        methods.add("freeConsignWeight");
        methods.add("consignmentFare");
    }

    public List<String> getMethods() {
        return methods;
    }
}
