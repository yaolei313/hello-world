package com.yao.app.java.annotation;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

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
 * Created by yaolei02 on 2018/11/19.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.yao.app.java.annotation.Custom")
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
        tool.writeLog(annotations.toString());
        for (Element element : elements) {
            Custom anno = element.getAnnotation(Custom.class);
            tool.writeLog("Element:" + element + "," + element.getSimpleName() + ",Custom value:" + anno.value());
            if (element instanceof PackageElement) {
                PackageElement packageElement = (PackageElement) element;
                tool.writeLog("PackageElement:" + packageElement);
            } else if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                tool.writeLog("TypeElement:" + typeElement.getQualifiedName());
            } else if (element instanceof ExecutableElement) {
                ExecutableElement executableElement = (ExecutableElement) element;
                tool.writeLog("ExecutableElement" + executableElement.getReturnType() + "," + executableElement.getReceiverType());
            } else if (element instanceof VariableElement) {
                VariableElement variableElement = (VariableElement) element;
                tool.writeLog("VariableElement" + variableElement.getConstantValue());
            } else {
                tool.writeLog("Other Element" + element);
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
