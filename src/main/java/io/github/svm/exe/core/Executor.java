package io.github.svm.exe.core;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.exe.lib.RuntimeLibrary;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.thread.ThreadTask;
import io.github.svm.util.VMRuntimeException;

import java.util.ArrayList;

public class Executor {

    ArrayList<RuntimeLibrary> rls;
    ThreadTask thread;
    Script executing;

    public Script getExecuting() {
        return executing;
    }

    public ThreadTask getThread() {
        return thread;
    }

    public void push(SVMObject obj){
        getThread().getCallStackPeek().push(obj);
    }
    public SVMObject pop(){
        return getThread().getCallStackPeek().pop();
    }
    public SVMObject peek(){
        return getThread().getCallStackPeek().op_stack.peek();
    }

    public ArrayList<RuntimeLibrary> getLibrary() {
        return rls;
    }

    public Executor(Script script, ThreadTask thread){
        this.executing = script;
        this.thread = thread;
        this.rls = LibraryLoader.getLibs();
    }

    public void launch() throws VMRuntimeException {
        for(ASTNode bc: executing.bcs){
            bc.executor(this);
        }
    }
}
