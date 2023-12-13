package io.github.svm.compile.stamon.table;

import io.github.svm.exe.obj.SVMNull;
import io.github.svm.exe.obj.SVMObject;

import java.io.DataOutputStream;
import java.io.IOException;

public class ConstNull extends Const{
    int id;
    public ConstNull(int id){
        this.id = id;
    }
    @Override
    public void dump(DataOutputStream outputStream,ConstTable table) throws IOException {
        outputStream.writeInt(id);
        outputStream.writeByte(0x01);
        outputStream.writeInt(0x00);
        outputStream.writeByte(0x00);
    }

    @Override
    public SVMObject getObject() {
        return new SVMNull();
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getSize() {
        return 3;
    }
}
