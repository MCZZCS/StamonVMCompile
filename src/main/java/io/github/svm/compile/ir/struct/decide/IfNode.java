package io.github.svm.compile.ir.struct.decide;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.StructNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

import java.util.List;

public class IfNode extends StructNode {
    List<ASTNode> bool, group;
    ElseNode else_group;
    List<ElseIfNode> elseIfNodes;

    public IfNode(List<ASTNode> bool, List<ASTNode> group, List<ElseIfNode> elseIfNodes, ElseNode else_group) {
        this.bool = bool;
        this.group = group;
        this.else_group = else_group;
        this.elseIfNodes = elseIfNodes;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        for (ASTNode b : bool) b.executor(executor);
        SVMObject o = executor.pop();

        if (Boolean.parseBoolean(o.getData())) {
            for (ASTNode b : group) b.executor(executor);
        } else {
            boolean isok = true;
            if (elseIfNodes != null) {
                for (ElseIfNode node : elseIfNodes) {
                    if (node.ifexecutor(executor)) {
                        isok = false;
                        break;
                    }
                }
            }
            if (isok) if (else_group != null) else_group.executor(executor);
        }
    }
}
