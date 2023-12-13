package io.github.svm.exe.lib;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.*;
import io.github.svm.util.VMRuntimeException;

import java.util.ArrayList;

public class StringLib implements RuntimeLibrary{
    ArrayList<RuntimeFunction> functions;

    public StringLib(){
        functions = new ArrayList<>();
        functions.add(new Split());
        functions.add(new IndexOf());
        functions.add(new Contains());
    }

    @Override
    public ArrayList<RuntimeFunction> functions() {
        return functions;
    }

    @Override
    public String getName() {
        return "string";
    }

    private static class Contains implements RuntimeFunction{

        @Override
        public int getVarNum() {
            return 2;
        }

        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            SVMObject string = vars.get(0);
            SVMObject sp = vars.get(1);
            return new SVMBool(string.getData().contains(sp.getData()));
        }

        @Override
        public String getName() {
            return "contains";
        }
    }

    private static class IndexOf implements RuntimeFunction{
        @Override
        public int getVarNum() {
            return 2;
        }
        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            SVMObject string = vars.get(0);
            SVMObject sp = vars.get(1);
            return new SVMInt(string.getData().indexOf(sp.getData()));
        }
        @Override
        public String getName() {
            return "indexof";
        }
    }

    private static class Split implements RuntimeFunction{
        @Override
        public int getVarNum() {
            return 2;
        }
        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            SVMObject string = vars.get(0);
            SVMObject sp = vars.get(1);
            ArrayList<SVMObject> o = new ArrayList<>();
            for(String s:string.getData().split(sp.getData())) o.add(new SVMString(s));
            return new SVMArray("<STRING>",o);
        }
        @Override
        public String getName() {
            return "split";
        }
    }
}
