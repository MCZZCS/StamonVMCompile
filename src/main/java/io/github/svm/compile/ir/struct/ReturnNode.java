package io.github.svm.compile.ir.struct;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMValue;
import io.github.svm.exe.obj.SVMVarName;
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

        SVMObject o = executor.pop();

        if(o instanceof SVMVarName){
            SVMValue buf = null;
            for(SVMValue v: ThreadManager.getValues()){
                if(v.getData().equals(o.getData())){
                    buf = v;
                    break;
                }
            }
            for(SVMValue v:executor.getThread().getCallStackPeek().getValues()){
                if(v.getData().equals(o.getData())){
                    buf = v;
                    break;
                }
            }
            if(buf == null)throw new VMRuntimeException("找不到指定变量:"+o.getData(),executor.getThread(), VMRuntimeException.EnumVMException.NULL_PRINT_EXCEPTION);

            if(buf.getType()== SVMObject.ARRAY){
                o = buf;
            }else o = buf.getVar();
        }

        throw new ReturnException(o);
    }
}
