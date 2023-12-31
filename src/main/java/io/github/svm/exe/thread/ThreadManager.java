package io.github.svm.exe.thread;

import io.github.svm.exe.obj.SVMMethod;
import io.github.svm.exe.obj.SVMValue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadManager {
    static List<SVMValue> values = new LinkedList<>();
    static List<SVMMethod> SVMMethods = new LinkedList<>();
    static ArrayList<ThreadTask> tasks = new ArrayList<>();

    public static List<SVMMethod> getFunctions() {
        return SVMMethods;
    }

    public static List<SVMValue> getValues() {
        return values;
    }

    public enum Status{
        RUNNING,DEATH,LOADING,WAIT,ERROR
    }
    public static void addThread(ThreadTask task){
        tasks.add(task);
    }
    public static void launch(){
        for(ThreadTask task:tasks){
            if(task.getName().equals("main")){
                task.start();
                return;
            }
        }
    }
}
