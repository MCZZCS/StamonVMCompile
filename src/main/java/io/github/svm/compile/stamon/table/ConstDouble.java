package io.github.svm.compile.stamon.table;

import io.github.svm.exe.obj.SVMDouble;
import io.github.svm.exe.obj.SVMObject;

import java.io.DataOutputStream;
import java.io.IOException;

public class ConstDouble extends Const{
    SVMDouble SVMDouble;
    int id;
    public ConstDouble(int id, SVMDouble SVMDouble){
        this.SVMDouble = SVMDouble;
        this.id = id;
    }
    @Override
    public void dump(DataOutputStream outputStream,ConstTable table) throws IOException {
        outputStream.writeInt(id);
        outputStream.writeByte(0x04);
        outputStream.writeInt(0x04);
        outputStream.writeDouble(Double.parseDouble(SVMDouble.getData()));
    }

    @Override
    public SVMObject getObject() {
        return SVMDouble;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getSize() {
        return 6;
    }
}
