package io.github.svm.compile.stamon.table;

import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMString;

import java.io.DataOutputStream;
import java.io.IOException;

public class ConstString extends Const{
    SVMString string;
    int id;
    public ConstString(int id, SVMString string){
        this.id = id;
        this.string = string;
    }
    @Override
    public void dump(DataOutputStream outputStream,ConstTable table) throws IOException {
        outputStream.writeInt(id);
        outputStream.writeByte(0x05);
        outputStream.writeInt(string.getData().length());
        for(char c:string.getData().toCharArray())
            outputStream.writeByte(c);
    }

    @Override
    public SVMObject getObject() {
        return string;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getSize() {
        return 3 + string.getData().length();
    }
}
