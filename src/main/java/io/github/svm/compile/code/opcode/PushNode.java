package io.github.svm.compile.code.opcode;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.ExObject;
import io.github.svm.util.VMRuntimeException;

public class PushNode extends OpNode {
    ExObject obj;
    public PushNode(ExObject obj){
        this.obj = obj;
    }

    public ExObject getObj() {
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
