package io.github.svm.compile.ir.struct;

import io.github.svm.compile.ir.ASTNode;

public abstract class StructNode implements ASTNode {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
