package io.github.svm.compile.parser;

import io.github.svm.compile.Compiler;
import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.NulASTNode;
import io.github.svm.util.CompileException;

import java.util.List;
import java.util.Set;

public class ElseIfParser implements BaseParser{
    List<ASTNode> bool;
    List<BaseParser> group;

    public ElseIfParser(List<ASTNode> bool, List<BaseParser> group){
        this.bool = bool;
        this.group = group;
    }

    @Override
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) throws CompileException {
        return new NulASTNode();
    }
}
