package io.github.svm.exe.core;

import io.github.svm.exe.obj.SVMMethod;

public class RuntimeStackFrame extends StackFrame{

    public RuntimeStackFrame(SVMMethod SVMMethod) {
        super(SVMMethod);
    }
}
