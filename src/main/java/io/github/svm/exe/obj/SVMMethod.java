package io.github.svm.exe.obj;

import io.github.svm.compile.ir.ASTNode;

import java.util.ArrayList;

public class SVMMethod extends SVMValue {
    String lib;
    String name;
    String filename;
    ArrayList<ASTNode> bcs;

    public SVMMethod(String lib, String name, ArrayList<ASTNode> bcs, String filename){
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
