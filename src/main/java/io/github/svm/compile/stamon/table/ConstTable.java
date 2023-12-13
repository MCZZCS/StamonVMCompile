package io.github.svm.compile.stamon.table;

import io.github.svm.compile.stamon.StamonByteCodeFile;
import io.github.svm.exe.obj.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ConstTable {
    /*
    null 0x01
    integer 0x02
    double 0x04
    string 0x05
     */
    StamonByteCodeFile byteCodeFile;
    List<Const> consts;
    int size;
    int index;


    public ConstTable(StamonByteCodeFile byteCodeFile){
        this.byteCodeFile = byteCodeFile;
        this.consts = new LinkedList<>();
        this.size = 0;
    }

    public int checkConst(SVMObject exInt, int type) {
        for (Const c : consts) {
            if (c.getObject().getType() == type) {
                if (c.getObject().getData().equals(exInt.getData())) {
                    return c.getID();
                }
            }
        }
        return -1;
    }

    public int poolObject(SVMObject object){
        int type = object.getType();
        return switch (type){
            case SVMObject.INTEGER -> poolInt((SVMInt) object);
            case SVMObject.BOOLEAN -> poolBoolean((SVMBool) object);
            case SVMObject.DOUBLE -> poolDouble((SVMDouble) object);
            case SVMObject.STRING -> poolString((SVMString) object);
            case SVMObject.NULL -> poolNull((SVMNull) object);
            default -> -1;
        };
    }

    public int poolBoolean(SVMBool bool){
        int ret;
        if((ret = checkConst(bool, SVMObject.INTEGER))!=-1){
            return ret;
        }else {
            ret = index;
            consts.add(new ConstInteger(index, new SVMInt(Boolean.parseBoolean(bool.getData()) ? 1 : 0)));
            index++;
        }
        return ret;
    }

    public int poolInt(SVMInt SVMInt){
        int ret;
        if((ret = checkConst(SVMInt, SVMObject.INTEGER))!=-1){
            return ret;
        }else {
            ret = index;
            consts.add(new ConstInteger(index, SVMInt));
            index++;
        }
        return ret;
    }

    public int poolNull(SVMNull SVMNull){
        int ret;
        if((ret = checkConst(SVMNull, SVMObject.NULL))!=-1){
            return ret;
        }else {
            ret = index;
            consts.add(new ConstNull(index));
            index++;
        }
        return ret;
    }

    public int poolDouble(SVMDouble SVMDouble){
        int ret;
        if((ret = checkConst(SVMDouble, SVMObject.DOUBLE))!=-1){
            return ret;
        }else {
            ret = index;
            consts.add(new ConstDouble(index, SVMDouble));
            index++;
        }
        return ret;
    }

    public int poolString(SVMString string){
        int ret;
        if((ret = checkConst(string, SVMObject.STRING))!=-1){
            return ret;
        }else {
            ret = index;
            consts.add(new ConstString(index, string));
            index++;
        }
        return ret;
    }

    public void dump(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(consts.size());
        for(Const c:consts) c.dump(outputStream,this);
    }
}
