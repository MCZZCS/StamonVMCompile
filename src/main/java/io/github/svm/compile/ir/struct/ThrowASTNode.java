package io.github.svm.compile.ir.struct;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

public class ThrowASTNode extends StructNode{
    ASTNode message;
    VMRuntimeException.EnumVMException type;

    public ThrowASTNode(ASTNode message,VMRuntimeException.EnumVMException type){
        this.message = message;
        this.type = type;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        message.executor(executor);
        SVMObject obj = executor.pop();

        if(obj.getType()== SVMObject.ARRAY) {
            throw new VMRuntimeException("The 'message' parameter type does not match, it should be of a not [ARRAY] type.",executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);
        }

        throw new VMRuntimeException(obj.getData(), executor.getThread(),type);
    }
}
