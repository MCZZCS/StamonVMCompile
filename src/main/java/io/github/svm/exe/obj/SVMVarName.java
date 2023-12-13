package io.github.svm.exe.obj;

public class SVMVarName extends SVMObject {
    String name;
    public SVMVarName(String name) {
        this.name = name;
    }

    @Override
    public String getData() {
        return name;
    }

    @Override
    public String toString() {
        return "[VALUE]:"+name;
    }

    @Override
    public int getType() {
        return VALUE;
    }
}
