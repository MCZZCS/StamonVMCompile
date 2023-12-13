package io.github.svm.compile.parser;

import io.github.svm.compile.code.ASTNode;
import io.github.svm.compile.Compiler;
import io.github.svm.util.CompileException;

import java.util.Set;

public interface BaseParser {
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) throws CompileException;
}