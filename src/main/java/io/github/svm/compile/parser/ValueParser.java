package io.github.svm.compile.parser;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.opcode.PushNode;
import io.github.svm.compile.ir.struct.GroupASTNode;
import io.github.svm.compile.ir.struct.LoadArrayNode;
import io.github.svm.compile.ir.struct.LoadVarNode;
import io.github.svm.exe.obj.SVMBool;
import io.github.svm.exe.obj.SVMNull;
import io.github.svm.compile.Token;
import io.github.svm.compile.ExpressionParsing;
import io.github.svm.util.CompileException;
import io.github.svm.compile.Compiler;

import java.util.*;

public class ValueParser implements BaseParser {

    List<Token> tds;
    private int index = 0;

    private Token getTokens() {
        if (index > tds.size()) return null;
        Token td = tds.get(index);
        index += 1;
        return td;
    }


    public ValueParser(List<Token> tds) {
        this.tds = tds;
    }



    public ASTNode evalPreVar(Parser parser, Compiler compiler, Set<String> tos) {
        Token name;
        Token td = getTokens();

        if (!(td.getType() == Token.NAME))
            throw new CompileException("Unable to resolve symbols.", td, parser.filename,parser);
        name = td;


        if (tos.contains(name.getData())
                || compiler.value_names.contains(name.getData()))
            throw new CompileException("The type " + name.getData() + " is already defined.", td, parser.filename,parser);

        td = getTokens();
        List<ASTNode> var_bc = new LinkedList<>();

        if (td.getType() == Token.END) {
            var_bc.add(new PushNode(new SVMNull()));
        } else {
            if (!(td.getType() == Token.SEM))
                throw new CompileException("Unable to resolve symbols.", name, parser.filename,parser);
            td = getTokens();

            if (td.getType() == Token.KEY) {
                switch (td.getData()) {
                    case "true" -> var_bc.add(new PushNode(new SVMBool(true)));
                    case "false" -> var_bc.add(new PushNode(new SVMBool(false)));
                    case "null" -> var_bc.add(new PushNode(new SVMNull()));
                    default -> throw new CompileException("Unable to resolve symbols.", td, parser.filename,parser);
                }
            } else if (td.getType() == Token.END) {
                var_bc.add(new PushNode(new SVMNull()));
            } else if (td.getType() == Token.LP) {
                boolean isend = true, isf = true;
                List<GroupASTNode> b = new LinkedList<>();
                do {
                    List<Token> tds = new LinkedList<>();
                    do {
                        td = getTokens();
                        if (td.getType() == Token.LR && td.getData().equals("]")) {
                            isend = false;
                            if (tds.size() == 0 && isf) {
                                tos.add(name.getData());
                                return new LoadArrayNode(name.getData(), new ArrayList<>(), 0, 0);
                            }
                            break;
                        }
                        if (td.getType() == Token.SEM && td.getData().equals(",")) break;
                        tds.add(td);
                    } while (true);
                    ExpressionParsing p = new ExpressionParsing(tds, parser, compiler);
                    b.add(new GroupASTNode(p.calculate(p.transitSuffix(tos))));
                    isf = false;
                } while (isend);
                tos.add(name.getData());
                return new LoadArrayNode(name.getData(), b, 0, -1);
            } else {
                List<Token> t = new LinkedList<>();
                t.add(td);
                do {
                    td = getTokens();
                    if (td.getType() == Token.END) break;
                    t.add(td);
                } while (true);
                ExpressionParsing p = new ExpressionParsing(t, parser, compiler);
                var_bc.addAll(p.calculate(p.transitSuffix(tos)));
            }
        }

        tos.add(name.getData());

        return new LoadVarNode(name.getData(), 0, var_bc);
    }

    @Override
    public ASTNode eval(Parser parser, Compiler compiler, Set<String> tos) {

        Token name;
        Token td = getTokens();

        if (!(td.getType() == Token.NAME))
            throw new CompileException("Unable to resolve symbols.", td, parser.filename,parser);
        name = td;

        if (compiler.value_names.contains(name.getData()))
            throw new CompileException("The type " + name.getData() + " is already defined.", td, parser.filename,parser);


        td = getTokens();
        List<ASTNode> var_bc = new LinkedList<>();

        if (td.getType() == Token.END) {
            var_bc.add(new PushNode(new SVMNull()));
        } else {
            if (!(td.getType() == Token.SEM))
                throw new CompileException("Unable to resolve symbols.", name, parser.filename,parser);
            td = getTokens();

            if (td.getType() == Token.KEY) {
                switch (td.getData()) {
                    case "true" -> var_bc.add(new PushNode(new SVMBool(true)));
                    case "false" -> var_bc.add(new PushNode(new SVMBool(false)));
                    case "null" -> var_bc.add(new PushNode(new SVMNull()));
                    default -> throw new CompileException("Unable to resolve symbols.", td, parser.filename,parser);
                }
            } else if (td.getType() == Token.END) {
                var_bc.add(new PushNode(new SVMNull()));
            } else if (td.getType() == Token.LP) {
                boolean isend = true, isf = true;
                List<GroupASTNode> b = new LinkedList<>();
                do {
                    List<Token> tds = new LinkedList<>();
                    do {
                        td = getTokens();
                        if (td.getType() == Token.LR && td.getData().equals("]")) {
                            isend = false;
                            if (tds.size() == 0 && isf) {
                                compiler.array_names.add(name.getData());
                                return new LoadArrayNode(name.getData(), new ArrayList<>(), 0, 0);
                            }
                            break;
                        }
                        if (td.getType() == Token.SEM && td.getData().equals(",")) break;
                        tds.add(td);
                    } while (true);
                    ExpressionParsing p = new ExpressionParsing(tds, parser, compiler);
                    b.add(new GroupASTNode(p.calculate(p.transitSuffix(null))));
                    isf = false;
                } while (isend);
                compiler.array_names.add(name.getData());
                return new LoadArrayNode(name.getData(), b, 0, -1);
            } else {
                List<Token> t = new LinkedList<>();
                t.add(td);
                do {
                    td = getTokens();
                    if(td == null)break;
                    if (td.getType() == Token.END) break;
                    t.add(td);
                } while (true);
                ExpressionParsing p = new ExpressionParsing(t, parser, compiler);
                var_bc.addAll(p.calculate(p.transitSuffix(null)));
            }
        }

        compiler.value_names.add(name.getData());

        return new LoadVarNode(name.getData(), 0, var_bc);
    }
}
