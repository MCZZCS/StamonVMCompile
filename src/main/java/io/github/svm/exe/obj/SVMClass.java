package io.github.svm.exe.obj;

public class SVMClass extends SVMObject{
    @Override
    public String getData() {
        return "";
    }

    @Override
    public int getType() {
        return CLASS;
    }
    //TODO 2023/12/14 需要实现Stamon语言的Class类型
}
