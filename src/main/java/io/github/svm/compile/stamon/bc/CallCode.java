package io.github.svm.compile.stamon.bc;

import io.github.svm.compile.ir.struct.InvokeASTNode;
import io.github.svm.compile.stamon.StamonByteCodeFile;
import io.github.svm.compile.stamon.StamonCompiler;
import io.github.svm.exe.obj.SVMString;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CallCode implements ByteCode{
    int opcode;
    List<ByteCode> codes;
    public CallCode(InvokeASTNode node, StamonByteCodeFile file){
        this.codes = new LinkedList<>();
        StamonCompiler.compile(codes,node.getVar(),file);
        this.opcode = file.getConstTable().poolString(new SVMString(node.getFunction()));
    }

    @Override
    public void dump(DataOutputStream outputStream) throws IOException {

        StamonCompiler.dump(outputStream,codes);

        outputStream.writeByte(CALL_CODE);
        outputStream.writeInt(opcode);
    }
}
