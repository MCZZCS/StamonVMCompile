package io.github.svm.exe.obj;

public class SVMInt extends SVMObject {
    int data;
    public SVMInt(int data){
        this.data = data;
    }

    @Override
    public String getData() {
        return data+"";
    }

    @Override
    public int getType() {
        return INTEGER;
    }

    @Override
    public String toString() {
        return "[INT]:"+data;
    }
}
