package io.github.svm.compile.parser;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.ReturnNode;
import io.github.svm.compile.Compiler;
import io.github.svm.compile.Token;
import io.github.svm.compile.ExpressionParsing;
import io.github.svm.util.CompileException;

import java.util.List;
import java.util.Set;

public class ReturnParser implements BaseParser{
    List<Token> tds;
    FunctionParser fparser;
    public ReturnParser(List<Token> tds, FunctionParser fparser){
        this.tds = tds;
        this.fparser = fparser;
    }

    @Override
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) throws CompileException {
        ExpressionParsing p = new ExpressionParsing(tds,parser,compiler);
        return new ReturnNode(p.calculate(p.transitSuffix(tos)));
    }
}
