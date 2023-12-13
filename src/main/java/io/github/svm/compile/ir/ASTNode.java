package io.github.svm.compile.ir;

import io.github.svm.exe.core.Executor;
import io.github.svm.util.VMRuntimeException;

public interface ASTNode {
    public void executor(Executor executor)throws VMRuntimeException;
}
