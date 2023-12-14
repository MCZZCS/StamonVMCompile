package io.github.svm;

import io.github.svm.exe.core.RuntimeShutdownHook;

import java.util.HashSet;

public class Main {
    public static final String version = "v0.0.1"; //版本号
    public static final String name = "StamonVMCompiler"; //版本号名

    static HashSet<String> s = new HashSet<>();
    static {
        s.add("function");
        s.add("value");
        s.add("class");
        s.add("if");
        s.add("else");
        s.add("while");
        s.add("return");
        s.add("false");
        s.add("true");
        s.add("include");
        s.add("this");
        s.add("null");
        s.add("elif");
        s.add("break");
        s.add("for");
        s.add("continue");
        s.add("try");
        s.add("catch");
        s.add("throw");
    }
    public static boolean isKey(String ss){
        return s.contains(ss);
    }

    public static void main(String[] args){
        try {
            Runtime.getRuntime().addShutdownHook(new RuntimeShutdownHook());
            ConsoleModel.command(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
