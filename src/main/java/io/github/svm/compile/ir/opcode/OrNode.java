package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMBool;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

public class OrNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject obj = ObjectSize.getValue(executor.pop());
        SVMObject obj1 = ObjectSize.getValue(executor.pop());
        if(obj1.getType()== SVMObject.BOOLEAN&&obj.getType()== SVMObject.BOOLEAN){
            executor.push(new SVMBool(Boolean.parseBoolean(obj.getData())||Boolean.parseBoolean(obj1.getData())));
        }else throw new VMRuntimeException("逻辑运算时提取到未知类型",executor.getThread());
    }
}
