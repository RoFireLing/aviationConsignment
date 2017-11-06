package testcases;

import java.util.List;

public class ListBean {
    private List<Bean> TestCases;
    public List<Bean> getList() {
        return TestCases;
    }
    public void setList(List<Bean> TestCases) {
        this.TestCases = TestCases;
    }
}
