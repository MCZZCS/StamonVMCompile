package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.*;
import io.github.svm.util.VMRuntimeException;

public class AddMovNode extends OpNode{
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject t1 = executor.pop();
        SVMObject t2 = executor.pop();

        t1 = ObjectSize.getValue(t1);

        if(t2.getType()!= SVMObject.VALUE)throw new VMRuntimeException("The operation type is incorrect",executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);

        SVMValue value = (SVMValue) t2;
        if(t1.getType()== SVMObject.STRING||value.getVar().getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN|| value.getVar().getType()== SVMObject.BOOLEAN){

            value.setVar(new SVMString(((SVMValue) t2).getVar()+t1.getData()));
        }
        else if(t1.getType()== SVMObject.DOUBLE||value.getVar().getType()== SVMObject.DOUBLE){
            value.setVar(new SVMDouble(Double.parseDouble(t2.getData())+Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||((SVMValue) t2).getVar().getType()== SVMObject.NULL)throw new VMRuntimeException("加法运算时发生空指针异常",executor.getThread(), VMRuntimeException.EnumVMException.NULL_PRINT_EXCEPTION);
        else{
            value.setVar(new SVMInt(Integer.parseInt(((SVMValue) t2).getVar().getData())+Integer.parseInt(t1.getData())));
        }
    }
}
