package io.github.svm.exe.core;

import io.github.svm.exe.obj.SVMMethod;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMValue;

import java.util.*;

public class StackFrame {
    SVMMethod SVMMethod;
    ArrayList<SVMValue> pre_value_table;
    Deque<SVMObject> op_stack;

    public StackFrame(SVMMethod SVMMethod){
        this.SVMMethod = SVMMethod;
        this.pre_value_table = new ArrayList<>();
        this.op_stack = new LinkedList<>();
    }

    public void push(SVMObject object){
        this.op_stack.push(object);
    }

    public SVMObject pop(){
        return this.op_stack.pop();
    }

    public Deque<SVMObject> getOpStack() {
        return op_stack;
    }

    public List<SVMValue> getValues(){
        return pre_value_table;
    }

    public SVMMethod getFunction() {
        return SVMMethod;
    }
}
