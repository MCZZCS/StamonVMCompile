package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMDouble;
import io.github.svm.exe.obj.SVMInt;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMValue;
import io.github.svm.util.VMRuntimeException;

public class DivXMovNode extends OpNode{
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject t1 = executor.pop();
        SVMObject t2 = executor.pop();

        t1 = ObjectSize.getValue(t1);

        if(t2.getType()!= SVMObject.VALUE)throw new VMRuntimeException("The operation type is incorrect",executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);
        SVMValue value = (SVMValue) t2;
        if(t1.getType()== SVMObject.STRING||t2.getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN||t2.getType()== SVMObject.BOOLEAN)throw new VMRuntimeException("字符串或布尔值类型不能参加减法运算",executor.getThread(), VMRuntimeException.EnumVMException.ILLEGAL_STATE_EXCEPTION);
        else if(t1.getType()== SVMObject.DOUBLE||t2.getType()== SVMObject.DOUBLE){
            value.setVar(new SVMDouble(Double.parseDouble(value.getVar().getData())%Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||t2.getType()== SVMObject.NULL)throw new VMRuntimeException("取余运算时发生空指针异常",executor.getThread(), VMRuntimeException.EnumVMException.NULL_PRINT_EXCEPTION);
        else{
            value.setVar(new SVMInt(Integer.parseInt(value.getVar().getData())%Integer.parseInt(t1.getData())));
        }
    }
}
