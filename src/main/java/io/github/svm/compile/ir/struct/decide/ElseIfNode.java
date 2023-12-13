package io.github.svm.compile.ir.struct.decide;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.StructNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

import java.util.List;

public class ElseIfNode extends StructNode {
    List<ASTNode> bool,group;

    public ElseIfNode(List<ASTNode> bool, List<ASTNode> group){
        this.bool = bool;
        this.group = group;
    }

    public boolean ifexecutor(Executor executor) throws VMRuntimeException {
        for(ASTNode b:bool)b.executor(executor);
        SVMObject o = executor.pop();

        if(Boolean.parseBoolean(o.getData())){
            for(ASTNode b:group)b.executor(executor);
            return true;
        }else return false;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
    }
}
