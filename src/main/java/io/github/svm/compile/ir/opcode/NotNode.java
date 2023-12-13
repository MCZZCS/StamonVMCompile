package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMBool;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

public class NotNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject obj = executor.pop();
        obj = ObjectSize.getValue(obj);
        if(obj.getType()== SVMObject.BOOLEAN){
            executor.push(new SVMBool(!Boolean.parseBoolean(obj.getData())));
        }else throw new VMRuntimeException("逻辑运算时提取到未知类型",executor.getThread());
    }
}
