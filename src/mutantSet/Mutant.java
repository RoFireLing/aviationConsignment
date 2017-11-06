package mutantSet;

public class Mutant {

    private String fullClassName;//变异体完整类名
    public Mutant(String fullClassName){
        setFullClassName(fullClassName);
    }
    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }
}
