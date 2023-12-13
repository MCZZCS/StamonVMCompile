package io.github.svm.exe.lib;

import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMBool;
import io.github.svm.exe.obj.SVMNull;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.exe.obj.SVMString;
import io.github.svm.util.VMRuntimeException;

import java.io.*;
import java.util.ArrayList;

public class File implements RuntimeLibrary{
    ArrayList<RuntimeFunction> functions;
    public File(){
        functions = new ArrayList<>();
        functions.add(new ReadFile());
        functions.add(new WriteFile());
        functions.add(new ExistsFile());
    }

    @Override
    public ArrayList<RuntimeFunction> functions() {
        return functions;
    }

    @Override
    public java.lang.String getName() {
        return "file";
    }

    private static class ExistsFile implements RuntimeFunction{

        @Override
        public int getVarNum() {
            return 1;
        }

        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            SVMObject name = vars.get(0);
            return new SVMBool(new java.io.File(name.getData()).exists());
        }

        @Override
        public java.lang.String getName() {
            return "exists";
        }
    }

    private static class WriteFile implements RuntimeFunction{
        @Override
        public int getVarNum() {
            return 2;
        }

        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            SVMObject name = vars.get(0);
            SVMObject data = vars.get(1);
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(name.getData()))){
                writer.write(data.getData());
                return new SVMBool(true);
            }catch (IOException e){
                return new SVMBool(false);
            }
        }

        @Override
        public java.lang.String getName() {
            return "write";
        }
    }

    private static class ReadFile implements RuntimeFunction{
        @Override
        public int getVarNum() {
            return 1;
        }

        @Override
        public SVMObject invoke(ArrayList<SVMObject> vars, Executor executor) throws VMRuntimeException {
            SVMObject obj = vars.get(0);
            StringBuilder sb = new StringBuilder();
            String line;
            try(BufferedReader reader = new BufferedReader(new FileReader(obj.getData()))){
                while ((line = reader.readLine())!=null)sb.append(line).append("\n");
                return new SVMString(sb.toString());
            }catch (FileNotFoundException e){
                return new SVMNull();
            }catch (IOException e){
                throw new VMRuntimeException("[FILE]:读取文件时发生错误",executor.getThread(), VMRuntimeException.EnumVMException.FILE_IO_EXCEPTION);
            }
        }

        @Override
        public java.lang.String getName() {
            return "read";
        }
    }
}
