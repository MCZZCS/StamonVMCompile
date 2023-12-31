package io.github.svm.exe.thread;

import io.github.svm.exe.core.StackFrame;

import java.util.Deque;
import java.util.LinkedList;

public class RuntimeInitThread extends ThreadTask{

    public RuntimeInitThread() {
        super("<init>");
    }

    @Override
    public String getFilename() {
        return "<loader_init>";
    }

    @Override
    public Deque<StackFrame> getCallStack() {
        return new LinkedList<>();
    }
}
