package io.github.svm.exe.obj;

import io.github.svm.util.VMRuntimeException;

import java.util.List;

public class SVMArray extends SVMValue {
    String name;
    SVMValue[] objs;

    public SVMArray(String name, int size){
        this.name = name;
        this.objs = new SVMValue[size];
    }
    public SVMArray(String name, List<SVMObject> objs){
        this.name = name;
        this.objs = new SVMValue[objs.size()];
        for (int i = 0; i < this.objs.length; i++) this.objs[i] = new SVMValue(objs.get(i));
    }
    public SVMArray(String name, SVMObject[] objs){
        this.name = name;
        for (int i = 0; i < this.objs.length; i++) this.objs[i] = new SVMValue(objs[i]);
    }

    public void setObj(int index, SVMObject obj){
        objs[index] = new SVMValue( obj);
    }

    public SVMObject[] getObjs() {
        return objs;
    }

    public int length(){
        return objs.length;
    }

    public SVMObject getObj(int index) throws VMRuntimeException {
        SVMValue obj = objs[index];
        return obj;
    }

    @Override
    public SVMObject getVar() {
        return this;
    }

    @Override
    public String toString(){
        int iMax = objs.length - 1;
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(objs[i].var);
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    @Override
    public String getData() {
        return name;
    }

    @Override
    public int getType() {
        return SVMObject.ARRAY;
    }
}
