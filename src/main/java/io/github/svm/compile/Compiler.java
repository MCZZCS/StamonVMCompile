package io.github.svm.compile;

import io.github.svm.CompileManager;
import io.github.svm.ConsoleModel;
import io.github.svm.compile.parser.BaseParser;
import io.github.svm.compile.parser.Parser;
import io.github.svm.compile.ir.ASTNode;
import io.github.svm.compile.ir.struct.NulASTNode;
import io.github.svm.compile.stamon.StamonByteCodeFile;
import io.github.svm.exe.core.Script;
import io.github.svm.exe.thread.ThreadTask;
import io.github.svm.util.CompileException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Compiler {
    String filename;
    List<ASTNode> bcs;
    Set<String> libname;
    public Set<String> value_names;
    public Set<String> array_names;

    public Set<String> function_names;

    public Set<String> getLibnames() {
        return libname;
    }

    public Compiler(String filename) {
        this.filename = filename;
        this.libname = new HashSet<>();
        this.value_names = new HashSet<>();
        this.array_names = new HashSet<>();
        this.function_names = new HashSet<>();
        libname.add(filename.split("\\.")[0]);
        this.bcs = new LinkedList<>();
    }

    public Set<String> getValueNames() {
        return value_names;
    }

    public void compile(ThreadTask task) {
        try {
            LexicalAnalysis al = new LexicalAnalysis(CompileManager.getFileData(filename), filename);
            List<Token> t = new LinkedList<>();
            for (Token b : al.getTokens()) {
                if (b.type == Token.LINE || b.type == Token.TEXT) continue;
                t.add(b);
            }
            ConsoleModel.getOutput().debug("Parsing file '" + filename + "'");
            Parser parser = new Parser(t, al.file_name);

            try {
                while (true) {
                    BaseParser bp = parser.getParser(this);
                    if (bp == null) break;
                    bcs.add(bp.eval(parser, this, value_names));
                }
            } catch (NullPointerException e) {
                throw new CompileException("';' expected.", filename);
            }


            List<ASTNode> bb = new LinkedList<>();
            for (ASTNode node : bcs) {
                if (node instanceof NulASTNode) continue;
                bb.add(node);
            }
            bcs = bb;

            if(ConsoleModel.isStamonVM){
                new StamonByteCodeFile(this).createFile();
            }

            task.addScripts(new Script(filename.split("\\.")[0], filename, bcs));
        } catch (CompileException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public List<ASTNode> getBcs() {
        return bcs;
    }

    public String getFilename() {
        return filename;
    }
}
