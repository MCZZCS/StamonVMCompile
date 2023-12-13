package io.github.svm.compile.code.struct;

import io.github.svm.compile.code.ASTNode;

public abstract class StructNode implements ASTNode {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
