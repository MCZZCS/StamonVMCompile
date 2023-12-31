package io.github.svm.compile;

import io.github.svm.compile.ir.struct.GroupASTNode;

public class TokenX extends Token {
    GroupASTNode bc;
    public TokenX(GroupASTNode bc){
        this.bc = bc;
    }

    public GroupASTNode getBc() {
        return bc;
    }

    @Override
    public int getType() {
        return EXP;
    }

    @Override
    public String toString() {
        return bc.toString();
    }
}
