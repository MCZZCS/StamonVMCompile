package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMBool;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

public class EquNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject obj = ObjectSize.getValue(executor.pop());
        SVMObject obj1 = ObjectSize.getValue(executor.pop());

        if(obj.getType()==obj1.getType()){
            executor.push(new SVMBool(obj.getData().equals(obj1.getData())));
        }else executor.push(new SVMBool(false));
    }
}
