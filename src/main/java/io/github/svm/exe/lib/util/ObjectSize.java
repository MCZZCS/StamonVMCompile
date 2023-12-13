package io.github.svm.exe.lib.util;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMArray;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMValue;
import io.github.svm.exe.obj.SVMVarName;
import io.github.svm.exe.thread.ThreadManager;
import io.github.svm.util.VMRuntimeException;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class ObjectSize {
    public static String getSize(SVMObject obj, Executor executor) throws VMRuntimeException {
        return getSize(sizeof(obj,executor));
    }
    private static long sizeof(SVMObject object, Executor executor) throws VMRuntimeException {
        long size = 0;
        if(object instanceof SVMVarName){
            SVMValue buf = null;
            for(SVMValue v: ThreadManager.getValues()){
                if(v.getData().equals(object.getData())){
                    buf = v;
                    break;
                }
            }
            for(SVMValue v:executor.getThread().getCallStackPeek().getValues()){
                if(v.getData().equals(object.getData())){
                    buf = v;
                    break;
                }
            }
            if(buf == null)throw new VMRuntimeException("找不到指定变量:"+object.getData(),executor.getThread());

            if(buf.getType()== SVMObject.ARRAY){
                object = buf;
            }else object = buf.getVar();
        }
        switch (object.getType()){
            case SVMObject.INTEGER, SVMObject.BOOLEAN -> size = 4;
            case SVMObject.DOUBLE -> size = 8;
            case SVMObject.NULL -> size = 0;
            case SVMObject.STRING -> size = object.getData().getBytes(StandardCharsets.UTF_8).length;
            case SVMObject.ARRAY -> {
                for(SVMObject object1:((SVMArray)object).getObjs()){
                    size += sizeof(object1,executor);
                }
            }
        }
        return size;
    }

    private static String getSize(long size) {
        int GB = 1024 * 1024 * 1024;
        int MB = 1024 * 1024;
        int KB = 1024;
        DecimalFormat df = new DecimalFormat("0.00");
        String resultSize = "";
        if (size / GB >= 1) {
            resultSize = df.format(size / (float) GB) + " GB";
        } else if (size / MB >= 1) {
            resultSize = df.format(size / (float) MB) + " MB";
        } else if (size / KB >= 1) {
            resultSize = df.format(size / (float) KB) + " KB";
        } else {
            resultSize = size + " Bit";
        }
        return resultSize;
    }

    public static SVMObject getValue(SVMObject o){
        if(o.getType()== SVMObject.VALUE){
            SVMObject v = ((SVMValue)o).getVar();
            if(v.getType()== SVMObject.VALUE) return getValue(v);
            else return v;
        }else return o;
    }
}
