package io.github.svm.compile.parser;

import io.github.svm.compile.Compiler;
import io.github.svm.compile.code.ASTNode;
import io.github.svm.compile.code.struct.loop.ContinueNode;
import io.github.svm.util.CompileException;

import java.util.Set;

public class ContinueParser implements BaseParser{
    public ContinueParser(){
    }

    @Override
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) throws CompileException {
        return new ContinueNode();
    }
}
