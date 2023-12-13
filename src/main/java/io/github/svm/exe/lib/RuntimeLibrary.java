package io.github.svm.exe.lib;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;

public interface RuntimeLibrary {
    interface RuntimeFunction{
        int getVarNum();
        SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException;
        String getName();

        default void exe(Executor executor) throws VMRuntimeException {
            try {
                ArrayList<SVMObject> obj = new ArrayList<>();

                for (int i = 0; i < getVarNum(); i++){
                    SVMObject o = executor.pop();

                    o = ObjectSize.getValue(o);

                    obj.add(o);
                }
                Collections.reverse(obj);

                executor.push(invoke(obj, executor));
            }catch (EmptyStackException e){
                e.printStackTrace();
                throw new VMRuntimeException("获取函数形参时发生错误,可能传入参数个数不匹配,实际参数为("+getVarNum()+"个)",executor.getThread(), VMRuntimeException.EnumVMException.ILLEGAL_ACCESS_EXCEPTION);
            }
        }
    }
    ArrayList<RuntimeFunction> functions();
    String getName();
}
