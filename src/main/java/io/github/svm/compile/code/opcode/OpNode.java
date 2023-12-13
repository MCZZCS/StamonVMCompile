package io.github.svm.compile.code.opcode;

import io.github.svm.compile.code.ASTNode;

public abstract class OpNode implements ASTNode {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
