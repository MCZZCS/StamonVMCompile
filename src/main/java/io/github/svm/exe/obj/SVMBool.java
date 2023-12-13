package io.github.svm.exe.obj;

public class SVMBool extends SVMObject {
    boolean data;
    public SVMBool(boolean data){
        this.data = data;
    }

    @Override
    public String getData() {
        return data+"";
    }

    @Override
    public int getType() {
        return BOOLEAN;
    }

    @Override
    public String toString() {
        return "[BOOL]:"+data;
    }
}
