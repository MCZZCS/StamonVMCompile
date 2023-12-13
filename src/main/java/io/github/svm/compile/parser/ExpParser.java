package io.github.svm.compile.parser;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.GroupASTNode;
import io.github.svm.compile.Compiler;
import io.github.svm.compile.Token;
import io.github.svm.compile.ExpressionParsing;
import io.github.svm.util.CompileException;

import java.util.List;
import java.util.Set;

public class ExpParser implements BaseParser{
    List<Token> tds;
    FunctionParser fparser;

    public ExpParser(List<Token> tds, FunctionParser fparser){
        this.tds = tds;
        this.fparser = fparser;
    }
    @Override
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) throws CompileException {
        ExpressionParsing ep = new ExpressionParsing(tds,parser,compiler);
        return new GroupASTNode(ep.calculate(ep.transitSuffix(tos)));
    }
}
