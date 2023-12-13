package io.github.svm.exe.obj;

public class SVMString extends SVMObject {
    String data;
    public SVMString(String data){
        this.data = data;
    }
    @Override
    public String getData() {
        return data;
    }

    @Override
    public int getType() {
        return STRING;
    }

    @Override
    public String toString() {
        return "[STRING]:"+data;
    }
}
