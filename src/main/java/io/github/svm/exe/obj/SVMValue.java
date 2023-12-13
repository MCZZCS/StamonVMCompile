package io.github.svm.exe.obj;

public class SVMValue extends SVMObject {
    String name;
    int type; // 1 local| 0 global
    SVMObject var;
    public SVMValue(String name, int type){
        this.name = name;
        this.type = type;
        this.var = new SVMNull();
    }

    public SVMValue(SVMObject object){
        this.var = object;
        this.name = "";
    }

    public SVMValue(){
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setVar(SVMObject var) {
        this.var = var;
    }

    public SVMObject getVar() {
        return var;
    }

    @Override
    public String getData() {
        return name;
    }

    @Override
    public int getType() {
        return VALUE;
    }

    @Override
    public String toString() {
        return "Value:"+name+"|Var:"+var;
    }
}
