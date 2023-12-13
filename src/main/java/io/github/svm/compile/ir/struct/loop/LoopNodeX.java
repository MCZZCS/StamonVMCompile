package io.github.svm.compile.ir.struct.loop;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.StructNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

import java.util.List;

public class LoopNodeX extends StructNode {
    List<ASTNode> bool;
    List<ASTNode> expression;
    ASTNode statement;
    List<ASTNode> codes;
    public LoopNodeX(ASTNode statement,List<ASTNode> bool,List<ASTNode> codes,List<ASTNode> expression){
        this.statement = statement;
        this.bool = bool;
        this.codes = codes;
        this.expression = expression;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        statement.executor(executor);
        loop(executor);
    }

    private void loop(Executor executor){
        while (true) {
            for (ASTNode b : bool) {
                b.executor(executor);
            }
            SVMObject obj = executor.pop();

            if (!Boolean.parseBoolean(obj.getData())) return;

            for(ASTNode b:expression) b.executor(executor);

            for (ASTNode b : codes) {
                if (b instanceof BackNode) return;
                if (b instanceof ContinueNode){
                    loop(executor);
                    break;
                }
                b.executor(executor);
            }
        }
    }

    @Override
    public String toString() {
        return "[LX]:Bool:"+bool+"|Code:"+codes;
    }
}
