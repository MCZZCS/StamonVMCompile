package io.github.svm.compile.stamon.ir;

import io.github.svm.compile.code.opcode.PushNode;
import io.github.svm.compile.stamon.StamonByteCodeFile;

import java.io.DataOutputStream;
import java.io.IOException;

public class PushCode implements ByteCode{
    int opcode;

    public PushCode(PushNode node, StamonByteCodeFile byteCodeFile){
        this.opcode = byteCodeFile.getConstTable().poolObject(node.getObj());
    }

    @Override
    public void dump(DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(PUSH_CODE);
        outputStream.writeInt(opcode);
    }
}
