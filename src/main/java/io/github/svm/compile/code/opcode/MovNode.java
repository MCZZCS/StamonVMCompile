package io.github.svm.compile.code.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.ExObject;
import io.github.svm.exe.obj.ExValue;
import io.github.svm.util.VMRuntimeException;

public class MovNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {

        ExObject o = executor.pop();
        ExObject o1 = executor.pop();

        o = ObjectSize.getValue(o);

        if(o1.getType()!=ExObject.VALUE)throw new VMRuntimeException("The operation type is incorrect",executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);

        ExValue value = (ExValue) o1;

        if(o.getType()==ExObject.VALUE){
            value.setVar(((ExValue)o).getVar());
            return;
        }

        value.setVar(o);
    }
}
