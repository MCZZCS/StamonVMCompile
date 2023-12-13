package io.github.svm.util;

import io.github.svm.exe.obj.SVMObject;

public class ReturnException extends RuntimeException{
    SVMObject obj;
    public ReturnException(SVMObject obj){
        this.obj = obj;
    }

    public SVMObject getObj() {
        return obj;
    }
}
