package io.github.svm.compile.ir.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

public class PushNode extends OpNode {
    SVMObject obj;
    public PushNode(SVMObject obj){
        this.obj = obj;
    }

    public SVMObject getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "push "+obj;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        executor.push(obj);
    }
}
