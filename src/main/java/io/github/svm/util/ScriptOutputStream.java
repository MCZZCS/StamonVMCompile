package io.github.svm.util;

import io.github.svm.ConsoleModel;

public class ScriptOutputStream {
    public void info(String info){
        System.out.println(info);
    }
    public void warn(String info){
        System.out.println("[WARN]"+info);
    }
    public void error(String error){
        System.err.println(error);
    }
    public void debug(String debug){
        if(ConsoleModel.debug) System.out.println(debug);
    }
}
