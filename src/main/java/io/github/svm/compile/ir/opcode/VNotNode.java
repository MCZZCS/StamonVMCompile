package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMDouble;
import io.github.svm.exe.obj.SVMInt;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

public class VNotNode extends OpNode{
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject object = executor.pop();
        object = ObjectSize.getValue(object);

        if(object.getType()== SVMObject.STRING||object.getType()== SVMObject.BOOLEAN||object.getType()== SVMObject.ARRAY)
            throw new VMRuntimeException("The operation type is incorrect.",executor.getThread(),VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);
        else if(object.getType()== SVMObject.NULL)
            throw new VMRuntimeException("取反运算发生空指针异常",executor.getThread(), VMRuntimeException.EnumVMException.NULL_PRINT_EXCEPTION);

        if(object.getType()== SVMObject.DOUBLE){
            executor.push(new SVMDouble(-Double.parseDouble(object.getData())));
        }else if(object.getType()== SVMObject.INTEGER){
            executor.push(new SVMInt(-Integer.parseInt(object.getData())));
        }

    }
}
