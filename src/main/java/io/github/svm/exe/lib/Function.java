package io.github.svm.exe.lib;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.exe.obj.SVMValue;

import java.util.ArrayList;

public class Function extends SVMValue {
    String lib;
    String name;
    String filename;
    ArrayList<ASTNode> bcs;

    public Function(String lib, String name, ArrayList<ASTNode> bcs,String filename){
        this.lib = lib;
        this.name = name;
        this.bcs = bcs;
        this.filename = filename;
    }

    public ArrayList<ASTNode> getBcs() {
        return bcs;
    }

    public String getName() {
        return name;
    }

    public String getLib() {
        return lib;
    }

    public String getFilename() {
        return filename;
    }
}
