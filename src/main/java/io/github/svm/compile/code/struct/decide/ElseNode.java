package io.github.svm.compile.code.struct.decide;

import io.github.svm.compile.code.ASTNode;
import io.github.svm.compile.code.struct.GroupASTNode;

import java.util.List;

public class ElseNode extends GroupASTNode {
    public ElseNode(List<ASTNode> bc) {
        super(bc);
    }
}
