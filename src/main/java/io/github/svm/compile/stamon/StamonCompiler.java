package io.github.svm.compile.stamon;

import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.opcode.OpNode;
import io.github.svm.compile.ir.opcode.PushNode;
import io.github.svm.compile.ir.struct.GroupASTNode;
import io.github.svm.compile.ir.struct.InvokeASTNode;
import io.github.svm.compile.stamon.bc.ByteCode;
import io.github.svm.compile.stamon.bc.CallCode;
import io.github.svm.compile.stamon.bc.OpCode;
import io.github.svm.compile.stamon.bc.PushCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class StamonCompiler {

    public static byte[] version = {0,0,1};

    public static void compile(List<ByteCode> codes, List<ASTNode> nodes, StamonByteCodeFile byteCodeFile){
        for(ASTNode node:nodes){
            if(node instanceof GroupASTNode){
                compile(codes,((GroupASTNode) node).getIR(),byteCodeFile);
                continue;
            }
            codes.add(toByteCode(node,byteCodeFile));
        }
    }

    public static ByteCode toByteCode(ASTNode ir,StamonByteCodeFile byteCodeFile){
        if(ir instanceof PushNode){
            return new PushCode((PushNode) ir,byteCodeFile);
        } else if (ir instanceof InvokeASTNode) {
            return new CallCode((InvokeASTNode) ir, byteCodeFile);
        }else if(ir instanceof OpNode){
            return new OpCode((OpNode) ir);
        }else return null;
    }

    public static void dump(DataOutputStream outputStream,List<ByteCode> byteCodes) throws IOException {
        for(ByteCode byteCode: byteCodes)
            byteCode.dump(outputStream);
    }
}
