package io.github.svm.compile.stamon.table;

import io.github.svm.exe.obj.SVMInt;
import io.github.svm.exe.obj.SVMObject;

import java.io.DataOutputStream;
import java.io.IOException;

public class ConstInteger extends Const{
    SVMInt SVMInt;
    int id;
    public ConstInteger(int id, SVMInt SVMInt){
        this.id = id;
        this.SVMInt = SVMInt;
    }
    @Override
    public void dump(DataOutputStream outputStream,ConstTable table) throws IOException {
        outputStream.writeInt(id);
        outputStream.writeByte(0x02);
        outputStream.writeInt(0x02);
        outputStream.writeShort(Integer.parseInt(SVMInt.getData()));
    }

    @Override
    public SVMObject getObject() {
        return SVMInt;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getSize() {
        return 4;
    }
}
