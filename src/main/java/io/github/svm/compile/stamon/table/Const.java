package io.github.svm.compile.stamon.table;

import io.github.svm.exe.obj.SVMObject;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Const {

    public abstract void dump(DataOutputStream outputStream,ConstTable table) throws IOException;
    public abstract SVMObject getObject();

    public abstract int getID();

    public abstract int getSize();
}
