package com.ancientmc.mason.paramscanner;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParamScanner {
    public static void run(File sources, File csv) throws IOException {
        FileWriter writer = new FileWriter(csv);
        writer.write("param,method,method descriptor,class" + System.lineSeparator());
        Collection<File> srcDir = FileUtils.listFiles(sources, new RegexFileFilter("[A-Za-z0-9]+\\.java"), DirectoryFileFilter.DIRECTORY);
        JavaProjectBuilder builder = new JavaProjectBuilder();
        assert srcDir != null;
        for(File source : srcDir) {
            JavaSource javaSrc = builder.addSource(source);
            JavaClass _class = javaSrc.getClasses().get(0);
            String className = _class.getName();
            int initNum = 0;
            for(JavaConstructor init : _class.getConstructors()) {
                initNum++;
                String initName = init.getName();
                String descriptor = getDescriptor(init);
                for(JavaParameter param : init.getParameters()) {
                    String paramName = param.getName();
                    writer.write(paramName + "," + "<init>$" + initNum + "," + descriptor + "," + className + System.lineSeparator());
                }
            }
            for(JavaMethod method : _class.getMethods()) {
                String methodName = method.getName();
                String descriptor = getDescriptor(method);
                for(JavaParameter param : method.getParameters()) {
                    String paramName = param.getName();
                    writer.write(paramName + "," + methodName + "," + descriptor + "," + className + System.lineSeparator());
                }
            }
            writer.flush();
        }
    }

    /**
     * Creates a brief descriptor of a method, similar to a bytecode signature. Shows the types of each parameter as well as
     * the return type for the method.
     */
    public static String getDescriptor(JavaMethod method) {
        String returnType = " ret " + method.getReturnType().getCanonicalName();
        List<String> paramTypes = new ArrayList<>();
        for(JavaParameter param : method.getParameters()) {
            String typeName = param.getType().getCanonicalName();
            paramTypes.add(typeName);
        }

        StringBuilder paramBuilder = new StringBuilder();
        for(String type : paramTypes) {
            paramBuilder.append(type);
            paramBuilder.append(";");
        }
        String params = paramBuilder.toString();
        return String.format("(" + params + ")" + returnType);
    }

    /**
     * Same as the other method with the same name, but for constructors.
     */
    public static String getDescriptor(JavaConstructor init) {
        List<String> paramTypes = new ArrayList<>();
        for(JavaParameter param : init.getParameters()) {
            String typeName = param.getType().getCanonicalName();
            paramTypes.add(typeName);
        }
        StringBuilder paramBuilder = new StringBuilder();
        for(String type : paramTypes) {
            paramBuilder.append(type);
            paramBuilder.append(";");
        }
        String params = paramBuilder.toString();
        return String.format("(" + params + ")");
    }
}
