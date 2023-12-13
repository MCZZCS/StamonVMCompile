package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMValue;
import io.github.svm.util.VMRuntimeException;

public class MovNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {

        SVMObject o = executor.pop();
        SVMObject o1 = executor.pop();

        o = ObjectSize.getValue(o);

        if(o1.getType()!= SVMObject.VALUE)throw new VMRuntimeException("The operation type is incorrect",executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);

        SVMValue value = (SVMValue) o1;

        if(o.getType()== SVMObject.VALUE){
            value.setVar(((SVMValue)o).getVar());
            return;
        }

        value.setVar(o);
    }
}
