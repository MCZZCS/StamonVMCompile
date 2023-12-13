package io.github.svm.exe.lib;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.thread.ThreadManager;
import io.github.svm.exe.obj.*;
import io.github.svm.util.VMRuntimeException;

import java.util.ArrayList;

public class Type implements RuntimeLibrary{

    ArrayList<RuntimeFunction> functions;
    public Type(){
        functions = new ArrayList<>();
        functions.add(new Typeof());
        functions.add(new ToString());
        functions.add(new ToDouble());
        functions.add(new ToBoolean());
        functions.add(new ToInteger());
    }
    @Override
    public ArrayList<RuntimeFunction> functions() {
        return functions;
    }

    @Override
    public java.lang.String getName() {
        return "type";
    }

    private static class Typeof implements RuntimeFunction{

        @Override
        public int getVarNum() {
            return 1;
        }

        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            SVMObject object = vars.get(0);

            String type = "unknown";
            if(object instanceof SVMVarName){
                SVMValue buf = null;
                for(SVMValue v: ThreadManager.getValues()){
                    if(v.getData().equals(object.getData())){
                        buf = v;
                        break;
                    }
                }
                for(SVMValue v:executor.getThread().getCallStackPeek().getValues()){
                    if(v.getData().equals(object.getData())){
                        buf = v;
                        break;
                    }
                }
                if(buf == null)throw new VMRuntimeException("找不到指定变量:"+object.getData(),executor.getThread(), VMRuntimeException.EnumVMException.NO_SUCH_VALUE_ERROR);

                if(buf.getType()== SVMObject.ARRAY){
                    object = buf;
                }else object = buf.getVar();
            }

            switch (object.getType()){
                case SVMObject.BOOLEAN -> type = "BOOL";
                case SVMObject.STRING -> type = "STRING";
                case SVMObject.INTEGER -> type = "INT";
                case SVMObject.DOUBLE -> type = "DOUBLE";
                case SVMObject.ARRAY -> type = "ARRAY";
                case SVMObject.NULL -> type = "NULL";
            }

            return new SVMString(type);
        }

        @Override
        public java.lang.String getName() {
            return "typeof";
        }
    }

    private static class ToString implements RuntimeFunction{
        @Override
        public int getVarNum() {
            return 1;
        }
        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            return new SVMString(vars.get(0).getData());
        }
        @Override
        public String getName() {
            return "to_string";
        }
    }
    private static class ToDouble implements RuntimeFunction{
        @Override
        public int getVarNum() {
            return 1;
        }
        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            try {
                SVMObject o = vars.get(0);
                if (o.getType() == SVMObject.ARRAY)
                    throw new VMRuntimeException("Cannot convert an array type to a double type.", executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);
                if (o.getType() == SVMObject.BOOLEAN) {
                    if (o.getData().equals("true")) return new SVMDouble(1.0);
                    else return new SVMDouble(0.0);
                }
                return new SVMDouble(Double.parseDouble(o.getData()));
            }catch (Exception e){
                throw new VMRuntimeException(e.getLocalizedMessage(),executor.getThread(), VMRuntimeException.EnumVMException.VM_ERROR);
            }
        }
        @Override
        public String getName() {
            return "to_double";
        }
    }
    private static class ToInteger implements RuntimeFunction{
        @Override
        public int getVarNum() {
            return 1;
        }
        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            try {
                SVMObject o = vars.get(0);
                if (o.getType() == SVMObject.ARRAY)
                    throw new VMRuntimeException("Cannot convert an array type to a integer type.", executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);
                if (o.getType() == SVMObject.BOOLEAN) {
                    if (o.getData().equals("true")) return new SVMInt(1);
                    else return new SVMInt(0);
                }
                return new SVMInt(Integer.parseInt(o.getData()));
            }catch (Exception e){
                throw new VMRuntimeException(e.getLocalizedMessage(),executor.getThread(), VMRuntimeException.EnumVMException.VM_ERROR);
            }
        }
        @Override
        public String getName() {
            return "to_integer";
        }
    }
    private static class ToBoolean implements RuntimeFunction {
        @Override
        public int getVarNum() {
            return 1;
        }

        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            try {
                SVMObject o = vars.get(0);
                if (o.getType() == SVMObject.ARRAY)
                    throw new VMRuntimeException("Cannot convert an array type to a double type.", executor.getThread(), VMRuntimeException.EnumVMException.TYPE_CAST_EXCEPTION);

                if (o.getType() == SVMObject.DOUBLE) {
                    double d = Double.parseDouble(o.getData());
                    if (d > 0) return new SVMBool(true);
                    else return new SVMBool(false);
                } else if (o.getType() == SVMObject.INTEGER) {
                    int i = Integer.parseInt(o.getData());
                    if (i > 0) return new SVMBool(true);
                    else return new SVMBool(false);
                }
                return new SVMBool(Boolean.parseBoolean(o.getData()));
            } catch (Exception e) {
                throw new VMRuntimeException(e.getLocalizedMessage(), executor.getThread(), VMRuntimeException.EnumVMException.VM_ERROR);
            }
        }

        @Override
        public String getName() {
            return "to_bool";
        }
    }
}
