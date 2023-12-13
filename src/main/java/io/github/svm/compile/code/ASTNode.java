package io.github.svm.compile.code;

import io.github.svm.exe.core.Executor;
import io.github.svm.util.VMRuntimeException;

public interface ASTNode {
    public void executor(Executor executor)throws VMRuntimeException;
}
