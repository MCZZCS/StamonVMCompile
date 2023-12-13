package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.SVMBool;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

public class LessNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject obj = ObjectSize.getValue(executor.pop());
        SVMObject obj1 = ObjectSize.getValue(executor.pop());


        if(obj.getType()== SVMObject.STRING||obj1.getType()== SVMObject.STRING) throw new VMRuntimeException("字符串不能参与比较运算",executor.getThread());
        if(obj.getType()== SVMObject.BOOLEAN||obj1.getType()== SVMObject.BOOLEAN)executor.push(new SVMBool(false));

        if(obj.getType()== SVMObject.DOUBLE||obj1.getType()== SVMObject.DOUBLE) {
            executor.push(new SVMBool(Double.parseDouble(obj1.getData()) < Double.parseDouble(obj.getData())));
        }else if(obj.getType()== SVMObject.INTEGER&&obj1.getType()== SVMObject.INTEGER){
            executor.push(new SVMBool(Integer.parseInt(obj1.getData()) < Integer.parseInt(obj.getData())));
        }
    }

}
