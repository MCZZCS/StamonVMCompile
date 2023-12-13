package io.github.svm.compile.code.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.ExBool;
import io.github.svm.exe.obj.ExObject;
import io.github.svm.util.VMRuntimeException;

public class EquNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        ExObject obj = ObjectSize.getValue(executor.pop());
        ExObject obj1 = ObjectSize.getValue(executor.pop());

        if(obj.getType()==obj1.getType()){
            executor.push(new ExBool(obj.getData().equals(obj1.getData())));
        }else executor.push(new ExBool(false));
    }
}
