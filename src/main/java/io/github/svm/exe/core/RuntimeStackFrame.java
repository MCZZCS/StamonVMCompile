package io.github.svm.exe.core;

import io.github.svm.exe.lib.Function;

public class RuntimeStackFrame extends StackFrame{

    public RuntimeStackFrame(Function function) {
        super(function);
    }
}
