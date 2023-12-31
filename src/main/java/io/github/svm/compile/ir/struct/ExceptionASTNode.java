package io.github.svm.compile.ir.struct;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.core.StackFrame;
import io.github.svm.exe.obj.SVMArray;
import io.github.svm.exe.obj.SVMString;
import io.github.svm.util.VMRuntimeException;

import java.util.LinkedList;

public class ExceptionASTNode extends StructNode{
    GroupASTNode try_codes,catch_codes;
    VMRuntimeException.EnumVMException exception;

    public ExceptionASTNode(GroupASTNode try_codes, GroupASTNode catch_codes, VMRuntimeException.EnumVMException exception){
        this.try_codes = try_codes;
        this.catch_codes = catch_codes;
        this.exception = exception;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        try{
            try_codes.executor(executor);
        }catch (VMRuntimeException e){
            if(e.getException().canCatch()){
                if(e.getException().equals(exception)){
                    executor.push(new SVMArray("<stack_info>",new LinkedList<>(){
                        {
                            for(StackFrame frame:executor.getThread().getCallStack()){
                                add(new SVMString(frame.getFunction().getName()));
                            }
                        }
                    }));
                    executor.push(new SVMString(e.getMessage()));
                    catch_codes.executor(executor);
                    return;
                }
            }
            throw e;
        }
    }
}
