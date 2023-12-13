package io.github.svm.compile.parser;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.loop.BackNode;
import io.github.svm.compile.Compiler;
import io.github.svm.util.CompileException;

import java.util.Set;


public class BackParser implements BaseParser{
    public BackParser(){
    }

    @Override
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) throws CompileException {
        return new BackNode();
    }
}
