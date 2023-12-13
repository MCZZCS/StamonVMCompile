package io.github.svm.compile.code.struct;

import io.github.svm.compile.code.ASTNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.ExObject;
import io.github.svm.exe.obj.ExValue;
import io.github.svm.exe.obj.ExVarName;
import io.github.svm.exe.thread.ThreadManager;
import io.github.svm.util.ReturnException;
import io.github.svm.util.VMRuntimeException;

import java.util.List;

public class ReturnNode extends StructNode {
    List<ASTNode> bcs;
    public ReturnNode(List<ASTNode> bcs){
        this.bcs = bcs;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {

        for(ASTNode b:bcs)b.executor(executor);

        ExObject o = executor.pop();

        if(o instanceof ExVarName){
            ExValue buf = null;
            for(ExValue v: ThreadManager.getValues()){
                if(v.getData().equals(o.getData())){
                    buf = v;
                    break;
                }
            }
            for(ExValue v:executor.getThread().getCallStackPeek().getValues()){
                if(v.getData().equals(o.getData())){
                    buf = v;
                    break;
                }
            }
            if(buf == null)throw new VMRuntimeException("找不到指定变量:"+o.getData(),executor.getThread(), VMRuntimeException.EnumVMException.NULL_PRINT_EXCEPTION);

            if(buf.getType()== ExObject.ARRAY){
                o = buf;
            }else o = buf.getVar();
        }

        throw new ReturnException(o);
    }
}
