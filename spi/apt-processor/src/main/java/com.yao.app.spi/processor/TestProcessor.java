package com.yao.app.spi.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.yao.app.spi.annotation.Custom;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 此处不是运行时的处理注解的机制(反射)，而是编译时处理注解。
 * <p>
 * processingEnv在父类中被传入，其
 * 提供了一系列工具类，比如Elements,Types,Filer等.
 * Created by yaolei02 on 2018/11/19.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.yao.app.spi.annotation.Custom")
@SupportedOptions({TestProcessor.SKIP_PRIMITIVE_TYPE_PRESENCE_CHECK})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TestProcessor extends AbstractProcessor {

    protected static final String SKIP_PRIMITIVE_TYPE_PRESENCE_CHECK = "skipPrimitiveTypePresenceCheck";

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 等同增加注解 @SupportedAnnotationTypes("com.yao.app.java.annotation.Custom")
        // return Sets.newHashSet(Custom.class.getCanonicalName());
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 等同增加注解 @SupportedSourceVersion(SourceVersion.RELEASE_11)
        // return SourceVersion.RELEASE_11
        return super.getSupportedSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Custom.class);
        LoggerFileTool tool = new LoggerFileTool(processingEnv);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "hello world");
        tool.writeLog("start handle");
        // 输出 [com.yao.app.java.annotation.Custom]
        tool.writeLog(annotations.toString());
        for (Element element : elements) {
            Custom anno = element.getAnnotation(Custom.class);
            String prefix = element.getSimpleName() + "/" + anno.value();
            if (element instanceof PackageElement) {
                PackageElement packageElement = (PackageElement) element;
                tool.writeLog("[" + prefix + "]PackageElement:" + packageElement.getQualifiedName());
            } else if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                tool.writeLog("[" + prefix + "]TypeElement:" + typeElement.getQualifiedName());
            } else if (element instanceof ExecutableElement) {
                ExecutableElement executableElement = (ExecutableElement) element;
                tool.writeLog("[" + prefix + "]ExecutableElement:return " + executableElement.getReturnType() + ",receiver " + executableElement.getReceiverType());
            } else if (element instanceof VariableElement) {
                VariableElement variableElement = (VariableElement) element;
                tool.writeLog("[" + prefix + "]VariableElement:" + variableElement.getSimpleName());
            } else {
                tool.writeLog("[" + prefix +"]Other Element" + element);
            }

            /*element.accept(new ElementVisitor<Void, Void>() {

                @Override
                public Void visit(Element e, Void aVoid) {
                    return null;
                }

                @Override
                public Void visitPackage(PackageElement e, Void aVoid) {
                    return null;
                }

                @Override
                public Void visitType(TypeElement e, Void aVoid) {
                    return null;
                }

                @Override
                public Void visitVariable(VariableElement e, Void aVoid) {
                    return null;
                }

                @Override
                public Void visitExecutable(ExecutableElement e, Void aVoid) {
                    return null;
                }

                @Override
                public Void visitTypeParameter(TypeParameterElement e, Void aVoid) {
                    return null;
                }

                @Override
                public Void visitUnknown(Element e, Void aVoid) {
                    return null;
                }
            }, null);*/
        }

        tool.flush();

        return true;
    }

    private static class LoggerFileTool implements Closeable {
        private final ProcessingEnvironment processingEnvironment;

        private List<String> messages;

        private boolean frozen;

        public LoggerFileTool(ProcessingEnvironment processingEnvironment) {
            this.processingEnvironment = processingEnvironment;
            this.messages = new ArrayList<>();
            this.frozen = false;
        }

        public void writeLog(String message) {
            if (!frozen) {
                this.messages.add(message);
            }
        }

        public void flush() {
            this.frozen = true;

            MethodSpec.Builder methodBuilder =
                    MethodSpec.methodBuilder("allMessages").addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).returns(void.class);
            for (String message : messages) {
                methodBuilder.addStatement("$T.out.println($S)", System.class, message);
            }

            TypeSpec type = TypeSpec.classBuilder("LoggerFile").addModifiers(Modifier.PUBLIC).addMethod(methodBuilder.build()).build();

            JavaFile javaFile = JavaFile.builder("com.yao.app.java.annotation", type).build();
            try {
                javaFile.writeTo(processingEnvironment.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close() throws IOException {
            flush();
        }
    }
}
