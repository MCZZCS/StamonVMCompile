package io.github.svm.compile.ir.struct;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMValue;
import io.github.svm.util.VMRuntimeException;

import java.util.List;

public class LoadVarNode extends StructNode {
    String name;
    int type;
    List<ASTNode> bcs;
    public LoadVarNode(String name, int type, List<ASTNode> bcs){
        this.name = name;
        this.bcs = bcs;
        this.type = type;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        for(ASTNode b:bcs)b.executor(executor);

        SVMValue v = new SVMValue(name,type);
        v.setVar(executor.pop());
        executor.getThread().getCallStackPeek().getValues().add(v);
    }

    @Override
    public String toString() {
        return "LOAD:"+bcs;
    }
}
