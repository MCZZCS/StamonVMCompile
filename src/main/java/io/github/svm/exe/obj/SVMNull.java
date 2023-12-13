package io.github.svm.exe.obj;

public class SVMNull extends SVMObject {
    @Override
    public String getData() {
        return "null";
    }

    @Override
    public int getType() {
        return NULL;
    }

    @Override
    public String toString() {
        return "[NULL]";
    }
}
