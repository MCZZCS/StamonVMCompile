package io.github.svm.compile.ir.struct.decide;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.GroupASTNode;

import java.util.List;

public class ElseNode extends GroupASTNode {
    public ElseNode(List<ASTNode> bc) {
        super(bc);
    }
}
