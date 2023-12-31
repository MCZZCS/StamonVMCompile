package io.github.svm;

import io.github.svm.exe.thread.ThreadManager;
import io.github.svm.exe.thread.ThreadTask;
import io.github.svm.compile.Compiler;
import io.github.svm.util.CompileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompileManager {
    static ArrayList<Compiler> compilers = new ArrayList<>();

    public static void compile(ArrayList<String> files) {
        if (ConsoleModel.isConsur) {
            if (files.size() < 3) {
                ConsoleModel.isConsur = false;
                ConsoleModel.output.debug("[Warn]: Too few files required to be compiled have been detected and concurrent compilation mode has been automatically turned off.");
            }
        }
        ThreadTask task = new ThreadTask("main");
        for (String s : files) compilers.add(new Compiler(s));
        for (Compiler c : compilers) {
            c.compile(task);
        }
        ThreadManager.addThread(task);
        if(!ConsoleModel.isStamonVM) ThreadManager.launch();

    }

    public static ArrayList<String> getFileData(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) lines.add(line);
            return lines;
        } catch (IOException io) {
            throw new CompileException("IOException:" + io.getLocalizedMessage(), name);
        }
    }
}
