package io.github.svm.compile;

import io.github.svm.compile.parser.Parser;
import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.opcode.PushNode;
import io.github.svm.compile.ir.struct.NulASTNode;
import io.github.svm.exe.lib.util.ObjectSize;
import io.github.svm.exe.obj.*;
import io.github.svm.util.CompileException;

import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;

public class OptimizationExecutor {
    List<Token> suffix;
    Deque<SVMObject> op_stack;

    Parser parser;
    public OptimizationExecutor(List<Token> suffix,Parser parser){

        this.suffix = suffix;
        this.op_stack = new LinkedList<>();
        this.parser = parser;
    }

    public ASTNode eval(){
        if(suffix.isEmpty()) return new NulASTNode();

        try {
            for (Token token : suffix) {
                if (token.getType() != Token.SEM) {
                    switch (token.getType()) {
                        case Token.INTEGER -> op_stack.push(new SVMInt(Integer.parseInt(token.data)));
                        case Token.STRING -> op_stack.push(new SVMString(token.data));
                        case Token.DOUBLE -> op_stack.push(new SVMDouble(Double.parseDouble(token.data)));
                        case Token.KEY -> {
                            switch (token.data) {
                                case "true" -> op_stack.add(new SVMBool(true));
                                case "false" -> op_stack.add(new SVMBool(false));
                                case "null" -> {
                                    return null;
                                }
                                default -> throw new CompileException("Illegal keywords.", token, parser.getFilename(), parser);
                            }
                        }
                        case Token.EXP -> {

                        }
                    }

                } else switch (token.getData()) {
                    case "+" -> add(token);
                    case "-" ->{
                        if(token instanceof UToken){
                            vnot(token);
                        }else sub(token);
                    }
                    case "*" -> mul(token);
                    case "/" -> div(token);
                    case "==" -> equ(token);
                    case ">=" -> bigequ(token);
                    case "<=" -> lessequ(token);
                    case ">" -> big(token);
                    case "<" -> less(token);
                    case "!" -> not(token);
                    case "&&" -> and(token);
                    case "||" -> or(token);
                    case "+=", "-=", "%=", "=" ->
                            throw new CompileException("Illegal combination of expressions.", token, parser.getFilename(), parser);
                    case "%" -> divx(token);
                }
            }

            return new PushNode(op_stack.pop());
        }catch (EmptyStackException e){
            return null;
        }
    }

    private void vnot(Token token){
        SVMObject object = op_stack.pop();


        if(object.getType()== SVMObject.STRING||object.getType()== SVMObject.BOOLEAN||object.getType()== SVMObject.ARRAY)
            throw new CompileException("The operation type is incorrect.",token, parser.getFilename(), parser);
        else if(object.getType()== SVMObject.NULL)
            throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);

        if(object.getType()== SVMObject.DOUBLE){
            op_stack.push(new SVMDouble(-Double.parseDouble(object.getData())));
        }else if(object.getType()== SVMObject.INTEGER){
            op_stack.push(new SVMInt(-Integer.parseInt(object.getData())));
        }
    }

    private void or(Token token){
        SVMObject obj = op_stack.pop();
        SVMObject obj1 = op_stack.pop();

        obj = ObjectSize.getValue(obj);
        obj1 = ObjectSize.getValue(obj1);

        if(obj1.getType()== SVMObject.BOOLEAN&&obj.getType()== SVMObject.BOOLEAN){
            op_stack.push(new SVMBool(Boolean.parseBoolean(obj.getData())||Boolean.parseBoolean(obj1.getData())));
        }else throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
    }

    private void and(Token token){
        SVMObject obj = op_stack.pop();
        SVMObject obj1 = op_stack.pop();

        obj = ObjectSize.getValue(obj);
        obj1 = ObjectSize.getValue(obj1);

        if(obj1.getType()== SVMObject.BOOLEAN&&obj.getType()== SVMObject.BOOLEAN){
            op_stack.push(new SVMBool(Boolean.parseBoolean(obj.getData())&&Boolean.parseBoolean(obj1.getData())));
        }else throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
    }

    private void not(Token token){
        SVMObject obj = op_stack.pop();
        obj = ObjectSize.getValue(obj);
        if(obj.getType()== SVMObject.BOOLEAN){
            op_stack.push(new SVMBool(!Boolean.parseBoolean(obj.getData())));
        }else throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
    }

    private void lessequ(Token token){
        SVMObject obj = ObjectSize.getValue(op_stack.pop());
        SVMObject obj1 = ObjectSize.getValue(op_stack.pop());

        if(obj.getType()== SVMObject.STRING||obj1.getType()== SVMObject.STRING) throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        if(obj.getType()== SVMObject.BOOLEAN||obj1.getType()== SVMObject.BOOLEAN)op_stack.push(new SVMBool(false));

        if(obj.getType()== SVMObject.DOUBLE||obj1.getType()== SVMObject.DOUBLE) {
            op_stack.push(new SVMBool(Double.parseDouble(obj1.getData()) <= Double.parseDouble(obj.getData())));
        }else if(obj.getType()== SVMObject.INTEGER&&obj1.getType()== SVMObject.INTEGER){
            op_stack.push(new SVMBool(Integer.parseInt(obj1.getData()) <= Integer.parseInt(obj.getData())));
        }
    }

    private void bigequ(Token token){
        SVMObject obj = ObjectSize.getValue(op_stack.pop());
        SVMObject obj1 = ObjectSize.getValue(op_stack.pop());

        if(obj.getType()== SVMObject.STRING||obj1.getType()== SVMObject.STRING) throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        if(obj.getType()== SVMObject.BOOLEAN||obj1.getType()== SVMObject.BOOLEAN)op_stack.push(new SVMBool(false));

        if(obj.getType()== SVMObject.DOUBLE||obj1.getType()== SVMObject.DOUBLE) {
            op_stack.push(new SVMBool(Double.parseDouble(obj1.getData()) >= Double.parseDouble(obj.getData())));
        }else if(obj.getType()== SVMObject.INTEGER&&obj1.getType()== SVMObject.INTEGER){
            op_stack.push(new SVMBool(Integer.parseInt(obj1.getData()) >= Integer.parseInt(obj.getData())));
        }
    }

    private void less(Token token){
        SVMObject obj = ObjectSize.getValue(op_stack.pop());
        SVMObject obj1 = ObjectSize.getValue(op_stack.pop());

        if(obj.getType()== SVMObject.STRING||obj1.getType()== SVMObject.STRING) throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        if(obj.getType()== SVMObject.BOOLEAN||obj1.getType()== SVMObject.BOOLEAN)op_stack.push(new SVMBool(false));

        if(obj.getType()== SVMObject.DOUBLE||obj1.getType()== SVMObject.DOUBLE) {
            op_stack.push(new SVMBool(Double.parseDouble(obj1.getData()) < Double.parseDouble(obj.getData())));
        }else if(obj.getType()== SVMObject.INTEGER&&obj1.getType()== SVMObject.INTEGER){
            op_stack.push(new SVMBool(Integer.parseInt(obj1.getData()) < Integer.parseInt(obj.getData())));
        }
    }

    private void big(Token token){
        SVMObject obj = ObjectSize.getValue(op_stack.pop());
        SVMObject obj1 = ObjectSize.getValue(op_stack.pop());

        if(obj.getType()== SVMObject.STRING||obj1.getType()== SVMObject.STRING) throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        if(obj.getType()== SVMObject.BOOLEAN||obj1.getType()== SVMObject.BOOLEAN)op_stack.push(new SVMBool(false));

        if(obj.getType()== SVMObject.DOUBLE||obj1.getType()== SVMObject.DOUBLE) {
            op_stack.push(new SVMBool(Double.parseDouble(obj1.getData()) > Double.parseDouble(obj.getData())));
        }else if(obj.getType()== SVMObject.INTEGER&&obj1.getType()== SVMObject.INTEGER){
            op_stack.push(new SVMBool(Integer.parseInt(obj1.getData()) > Integer.parseInt(obj.getData())));
        }
    }

    private void equ(Token token){
        SVMObject obj = ObjectSize.getValue(op_stack.pop());
        SVMObject obj1 = ObjectSize.getValue(op_stack.pop());

        if(obj.getType()==obj1.getType()){
            op_stack.push(new SVMBool(obj.getData().equals(obj1.getData())));
        }else op_stack.push(new SVMBool(false));
    }

    private void add(Token token){
        SVMObject t1 = op_stack.pop();
        SVMObject t2 = op_stack.pop();

        t1 = ObjectSize.getValue(t1);
        t2 = ObjectSize.getValue(t2);

        if(t1.getType()== SVMObject.STRING||t2.getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN||t2.getType()== SVMObject.BOOLEAN){
            op_stack.push(new SVMString(t2.getData()+t1.getData()));
        }
        else if(t1.getType()== SVMObject.DOUBLE||t2.getType()== SVMObject.DOUBLE){
            op_stack.push(new SVMDouble(Double.parseDouble(t2.getData())+Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||t2.getType()== SVMObject.NULL)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else{
            op_stack.push(new SVMInt(Integer.parseInt(t2.getData())+Integer.parseInt(t1.getData())));
        }
    }

    private void sub(Token token){
        SVMObject t1 = op_stack.pop();
        SVMObject t2 = op_stack.pop();

        t1 = ObjectSize.getValue(t1);
        t2 = ObjectSize.getValue(t2);

        if(t1.getType()== SVMObject.STRING||t2.getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN||t2.getType()== SVMObject.BOOLEAN)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else if(t1.getType()== SVMObject.DOUBLE||t2.getType()== SVMObject.DOUBLE){
            op_stack.push(new SVMDouble(Double.parseDouble(t2.getData())-Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||t2.getType()== SVMObject.NULL)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else{
            op_stack.push(new SVMInt(Integer.parseInt(t2.getData())-Integer.parseInt(t1.getData())));
        }
    }

    private void mul(Token token){
        SVMObject t1 = op_stack.pop();
        SVMObject t2 = op_stack.pop();

        t1 = ObjectSize.getValue(t1);
        t2 = ObjectSize.getValue(t2);

        if(t1.getType()== SVMObject.STRING||t2.getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN||t2.getType()== SVMObject.BOOLEAN)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else if(t1.getType()== SVMObject.DOUBLE||t2.getType()== SVMObject.DOUBLE){
            op_stack.push(new SVMDouble(Double.parseDouble(t2.getData())*Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||t2.getType()== SVMObject.NULL)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else{
            op_stack.push(new SVMInt(Integer.parseInt(t2.getData())*Integer.parseInt(t1.getData())));
        }
    }

    private void div(Token token){
        SVMObject t1 = op_stack.pop();
        SVMObject t2 = op_stack.pop();

        t1 = ObjectSize.getValue(t1);
        t2 = ObjectSize.getValue(t2);

        if(t1.getType()== SVMObject.STRING||t2.getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN||t2.getType()== SVMObject.BOOLEAN)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else if(t1.getType()== SVMObject.DOUBLE||t2.getType()== SVMObject.DOUBLE){
            op_stack.push(new SVMDouble(Double.parseDouble(t2.getData())/Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||t2.getType()== SVMObject.NULL)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else{
            op_stack.push(new SVMInt(Integer.parseInt(t2.getData())/Integer.parseInt(t1.getData())));
        }
    }

    private void divx(Token token){
        SVMObject t1 = op_stack.pop();
        SVMObject t2 = op_stack.pop();

        t1 = ObjectSize.getValue(t1);
        t2 = ObjectSize.getValue(t2);

        if(t1.getType()== SVMObject.STRING||t2.getType()== SVMObject.STRING||t1.getType()== SVMObject.BOOLEAN||t2.getType()== SVMObject.BOOLEAN)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else if(t1.getType()== SVMObject.DOUBLE||t2.getType()== SVMObject.DOUBLE){
            op_stack.push(new SVMDouble(Double.parseDouble(t2.getData())%Double.parseDouble(t1.getData())));
        }
        else if(t1.getType()== SVMObject.NULL||t2.getType()== SVMObject.NULL)throw new CompileException("Illegal combination of expressions.",token, parser.getFilename(), parser);
        else{
            op_stack.push(new SVMInt(Integer.parseInt(t2.getData())%Integer.parseInt(t1.getData())));
        }
    }
}
