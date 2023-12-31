package io.github.svm.exe.thread;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.core.LoaderStackFrame;
import io.github.svm.exe.core.Script;
import io.github.svm.exe.core.StackFrame;
import io.github.svm.exe.obj.SVMMethod;
import io.github.svm.util.VMRuntimeException;

import java.util.*;

public class ThreadTask {
    Executor executor;
    String name;
    Thread thread;
    ThreadManager.Status status;
    ArrayList<Script> scripts;
    SVMMethod SVMMethod;
    Deque<StackFrame> stackFrames;

    public ThreadTask(String name){
        this.name = name;
        this.scripts = new ArrayList<>();
        this.stackFrames = new LinkedList<>();
        this.thread = new Thread(() ->{
            try{
                stackFrames.push(new LoaderStackFrame(executor));
                executor.launch();
            }catch (VMRuntimeException e){
                status = ThreadManager.Status.ERROR;
                e.printStackTrace();
            }catch (NoSuchElementException e){
                status = ThreadManager.Status.ERROR;
                throw new VMRuntimeException("操作栈出栈异常", executor.getThread(), VMRuntimeException.EnumVMException.VM_OP_STACK_ERROR);
            }
        });
        this.thread.setName("OpenEX-Executor-"+name);
        this.status = ThreadManager.Status.LOADING;
    }

    public String getFilename(){
        return executor.getExecuting().getFilename();
    }

    public void setFunction(SVMMethod SVMMethod) {
        this.SVMMethod = SVMMethod;
    }

    public void setStatus(ThreadManager.Status status) {
        this.status = status;
    }

    public void addScripts(Script scripts) {
        this.scripts.add(scripts);
    }

    public StackFrame getCallStackPeek(){
        return stackFrames.peek();
    }

    public Deque<StackFrame> getCallStack() {
        return stackFrames;
    }

    public void pushCallStackFrame(StackFrame stackFrame){
        if(this.stackFrames.size() > 510) throw new VMRuntimeException("Call stack out of push.", executor.getThread(), VMRuntimeException.EnumVMException.STACK_OVER_FLOW_ERROR);
        stackFrame.getValues().addAll(this.stackFrames.peek().getValues());
        this.stackFrames.push(stackFrame);
    }

    public void popCallStackFrame(){
        this.stackFrames.pop();
    }

    public String getName() {
        return name;
    }

    public void start(){
        if(SVMMethod ==null) {
            this.executor = new Executor(scripts.get(0), this);
            this.status = ThreadManager.Status.RUNNING;
            this.thread.start();
        }else {
            this.status = ThreadManager.Status.RUNNING;
            this.executor = new Executor(new Script(SVMMethod.getLib(), SVMMethod.getLib()+".exf", SVMMethod.getBcs()),this);
            this.thread.start();
        }
    }
}
