package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.util.VMRuntimeException;
import io.github.svm.exe.obj.SVMDouble;
import io.github.svm.exe.obj.SVMInt;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMString;

public class AddNode extends OpNode {

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        SVMObject t1 = executor.pop();
        SVMObject t2 = executor.pop();

        t1 = ObjectSize.getValue(t1);
        t2 = ObjectSize.getValue(t2);

        if(t1.getType()== SVMObject.STRING||t2.getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN||t2.getType()== SVMObject.BOOLEAN){
            executor.push(new SVMString(t2.getData()+t1.getData()));
        }
        else if(t1.getType()== SVMObject.DOUBLE||t2.getType()== SVMObject.DOUBLE){
            executor.push(new SVMDouble(Double.parseDouble(t2.getData())+Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||t2.getType()== SVMObject.NULL)throw new VMRuntimeException("加法运算时发生空指针异常",executor.getThread());
        else{
            executor.push(new SVMInt(Integer.parseInt(t2.getData())+Integer.parseInt(t1.getData())));
        }
    }
}
