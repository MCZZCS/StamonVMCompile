package io.github.svm.compile.code.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.util.VMRuntimeException;
import io.github.svm.exe.obj.ExDouble;
import io.github.svm.exe.obj.ExInt;
import io.github.svm.exe.obj.ExObject;

public class DivNode extends OpNode {
    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        ExObject t1 = executor.pop();
        ExObject t2 = executor.pop();

        t1 = ObjectSize.getValue(t1);
        t2 = ObjectSize.getValue(t2);

        if(t1.getType()==ExObject.STRING||t2.getType()==ExObject.STRING||t1.getType()==ExObject.BOOLEAN||t2.getType()==ExObject.BOOLEAN)throw new VMRuntimeException("字符串或布尔值类型不能参加除法运算",executor.getThread());
        else if(t1.getType()==ExObject.DOUBLE||t2.getType()==ExObject.DOUBLE){
            executor.push(new ExDouble(Double.parseDouble(t2.getData())-Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()==ExObject.NULL||t2.getType()==ExObject.NULL)throw new VMRuntimeException("除法运算时发生空指针异常",executor.getThread());
        else{
            executor.push(new ExInt(Integer.parseInt(t2.getData())-Integer.parseInt(t1.getData())));
        }
    }
}