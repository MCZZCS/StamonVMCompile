package io.github.svm.compile.parser;

import io.github.svm.compile.Compiler;
import io.github.svm.compile.ir.ASTNode;
import io.github.svm.util.CompileException;

import java.util.Set;

public class BufParser implements BaseParser{
    ASTNode nodes;
    public BufParser(ASTNode nodes){
        this.nodes = nodes;
    }

    @Override
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) throws CompileException {
        return nodes;
    }
}
