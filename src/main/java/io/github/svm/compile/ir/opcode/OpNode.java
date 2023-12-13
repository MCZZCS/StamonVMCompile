package io.github.svm.compile.ir.opcode;

import io.github.svm.compile.ir.ASTNode;

public abstract class OpNode implements ASTNode {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
