package io.github.svm.compile.ir.struct;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMValue;
import io.github.svm.util.VMRuntimeException;

public class MovVarNode extends StructNode {

    String name;
    public MovVarNode(String name){
        this.name = name;
    }


    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMValue ex = null;
        for(SVMValue e:executor.getThread().getCallStackPeek().getValues()){
            if(e.getData().equals(name)){
                ex = e;
            }
        }
        if(ex == null)throw new VMRuntimeException("找不到指定变量:"+name,executor.getThread(), VMRuntimeException.EnumVMException.NULL_PRINT_EXCEPTION);

        executor.push(ex);
    }
}
