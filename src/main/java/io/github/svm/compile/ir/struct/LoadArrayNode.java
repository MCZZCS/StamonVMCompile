package io.github.svm.compile.ir.struct;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.exe.core.Executor;
import io.github.svm.exe.obj.SVMArray;
import io.github.svm.exe.obj.SVMObject;
import io.github.svm.util.VMRuntimeException;

import java.util.LinkedList;
import java.util.List;

public class LoadArrayNode extends StructNode {
    String name;
    List<GroupASTNode> g;
    int type;
    int size;

    public LoadArrayNode(String name, List<GroupASTNode> g, int type, int size){
        this.name = name;
        this.g = g;
        this.type = type;
        this.size = size;
    }

    @Override
    public void executor(Executor executor) throws VMRuntimeException {
        List<SVMObject> objs = new LinkedList<>();
        for(GroupASTNode gg:g){
            for(ASTNode bb:gg.bc)bb.executor(executor);
            objs.add(executor.pop());
        }
        SVMArray array;
        if(objs.size()==0){
            if(size > -1)array = new SVMArray(name,size);
            else array = new SVMArray(name,0);
        } else array = new SVMArray(name,objs);


        executor.getThread().getCallStackPeek().getValues().add(array);
    }
}
