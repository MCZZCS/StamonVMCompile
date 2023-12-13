package io.github.svm.compile.code.struct;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.ExValue;
import io.github.svm.util.VMRuntimeException;

public class MovVarNode extends StructNode {

    String name;
    public MovVarNode(String name){
        this.name = name;
    }


    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        ExValue ex = null;
        for(ExValue e:executor.getThread().getCallStackPeek().getValues()){
            if(e.getData().equals(name)){
                ex = e;
            }
        }
        if(ex == null)throw new VMRuntimeException("找不到指定变量:"+name,executor.getThread(), VMRuntimeException.EnumVMException.NULL_PRINT_EXCEPTION);

        executor.push(ex);
    }
}
