package io.github.svm.util;

import io.github.svm.ConsoleModel;
import io.github.svm.Main;
import io.github.svm.compile.Token;
import io.github.svm.compile.parser.Parser;

import java.io.PrintStream;

public class CompileException extends RuntimeException{
    Token token;
    Parser parser;
    String filename;
    String message;
    int status;

    StackTraceElement[] trace;

    public CompileException(String message,String filename){
        this.message = TextFormer.format(message);
        this.filename = filename;
        this.status = 2;
        this.parser = null;
        if(ConsoleModel.debug) this.trace = Thread.currentThread().getStackTrace();
    }
    public CompileException(String message, Token token, String filename,Parser parser){
        this.token = token;
        this.filename = filename;
        this.status = 1;
        this.message = TextFormer.format(message);
        this.parser = parser;
        if(ConsoleModel.debug) this.trace = Thread.currentThread().getStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        if (status == 1) {
            s.println("Compile Error: " + message +
                    "\n\tToken: " + token.getData() +
                    "\n\tEdition: " + Main.name+
                    "\n\tVersion: " + Main.version);
        } else if (status == 2) {
            s.println("Compile Error: " + message +
                    "\n\tEdition: " + Main.name +
                    "\n\tVersion: " + Main.version);
        }
        if(parser == null){
            s.println("Location: ("+filename+": unknown)");
            s.println("\t<no_such_statement>");
            if(ConsoleModel.debug) {
                for (StackTraceElement traceElement : trace)
                    s.println("\tat " + traceElement);
            }
            return;
        }

        int line = token.getLine();
        s.println("Location: ("+filename+": "+line+")");

        int index = 0,buf = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("    ").append(line).append("| ");

        for(Token token1:parser.getTds()){
            if(token1.getLine() == line){
                sb.append(token1.getData()).append(' ');
                index += token1.getData().length() + 1;
                if(token.equals(token1)){
                    buf = index;
                }

            }
        }
        s.println(sb);


        StringBuilder sbb = new StringBuilder();
        sbb.append("    ");

        sbb.append(" ".repeat(Math.max(0,buf)));

        if(!((buf = token.getData().length()) < 3)){
            sbb.append(" ".repeat(buf));
        }

        sbb.append('^');
        s.println(sbb);

        if(ConsoleModel.debug) {
            for (StackTraceElement traceElement : trace)
                s.println("\tat " + traceElement);
        }
    }
}
