package io.github.svm.exe.obj;

public class SVMDouble extends SVMObject {
    double data;
    public SVMDouble(double data){
        this.data = data;
    }
    @Override
    public String getData() {
        return data+"";
    }

    @Override
    public int getType() {
        return DOUBLE;
    }

    @Override
    public String toString() {
        return "[DOUBLE]"+data;
    }
}
