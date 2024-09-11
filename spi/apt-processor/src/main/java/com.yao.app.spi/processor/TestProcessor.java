package com.yao.app.spi.processor;

import com.google.auto.service.AutoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

/**
 * 此处不是运行时的处理注解的机制(反射)，而是编译时处理注解。
 * <p>https://docs.oracle.com/en/java/javase/17/docs/specs/man/javac.html#annotation-processing
 * <p>processingEnv在父类中被传入，其 提供了一系列工具类，比如Elements,Types,Filer等.
 *
 * <pre>
 * 1.compiler找到所有的Processor
 * 2.扫码源文件,确认可以处理的annotation
 * 3.尝试寻找可匹配的Processor，若找到，则调用。若是processor声称它已处理，则后续不在尝试匹配其余的Processor
 * 4.如果任何Processor产生了新的文件，则会触发新一轮的处理。即扫描新生成的代码文件，继续步骤2,3。直到没有新的代码文件生成为止。
 * 5.在最后一轮没有新文件产生后，processors会被再次调用一次，让processors完成剩余的收尾工作
 * </pre>
 *
 * <pre>
 * javac 运行时存在（代码）编译环境和（自身）运行环境，传参数给javac需要增加-J
 * annotation processor处理的elements是在compilation environment；annotation processor自身是runtime environment
 * 如果annotation processor依赖其他jar，则需要在--processor-path同样也包含该依赖
 *
 * </pre>
 *
 * @author yaolei02 on 2018/11/19.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes(TestProcessor.CUSTOM_ANNOTATION_NAME)
@SupportedOptions({TestProcessor.SKIP_PRIMITIVE_TYPE_PRESENCE_CHECK})
public class TestProcessor extends AbstractProcessor {

    public static final String CUSTOM_ANNOTATION_NAME = "com.yao.app.spi.annotation.Custom";

    protected static final String SKIP_PRIMITIVE_TYPE_PRESENCE_CHECK = "skipPrimitiveTypePresenceCheck";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

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
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "spi processing over");
            return false;
        }
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "handle annotation " + annotation.getQualifiedName());
            for (Element element : elements) {
                processAnnotation(annotation.getQualifiedName().toString(), element);
            }
        }

        return true;
    }

    private void processAnnotation(String annotationName, Element element) {
        if (CUSTOM_ANNOTATION_NAME.equals(annotationName)) {
            AnnotationMirror mirror = element.getAnnotationMirrors().stream()
                .filter(a -> CUSTOM_ANNOTATION_NAME.contentEquals(((TypeElement) a.getAnnotationType().asElement()).getQualifiedName())).findAny().orElse(null);
            processCustomAnnotation(element, mirror);
        }
    }

    private void processCustomAnnotation(Element element, AnnotationMirror mirror) {
        List<ExecutableElement> enclosedElements = ElementFilter.methodsIn(mirror.getAnnotationType().asElement().getEnclosedElements());

        Map<String, AnnotationValue> defaultValues = new HashMap<>(enclosedElements.size());
        enclosedElements.forEach(e -> defaultValues.put(e.getSimpleName().toString(), e.getDefaultValue()));

        // fetch all explicitly set annotation values in the annotation instance
        Map<String, AnnotationValue> values = new HashMap<>(enclosedElements.size());
        mirror.getElementValues().entrySet().forEach(e -> values.put(e.getKey().getSimpleName().toString(), e.getValue()));

        for (String methodName : defaultValues.keySet()) {
            if ("value".equals(methodName)) {
                AnnotationValue val = values.get(methodName);
                AnnotationValue dval = defaultValues.get(methodName);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "annotation value:" + val.getValue() + ", defaultValue:" + dval.getValue());
                String value = (String) (val.getValue() == null ? dval.getValue() : val.getValue());
                processCustomAnnotation(element, value);
            }
        }
    }

    private void processCustomAnnotation(Element element, String val) {
        String prefix = element.getSimpleName() + "/" + val;
        if (element instanceof PackageElement) {
            PackageElement packageElement = (PackageElement) element;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[" + prefix + "]PackageElement:" + packageElement.getQualifiedName());
        } else if (element instanceof TypeElement) {
            TypeElement typeElement = (TypeElement) element;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[" + prefix + "]TypeElement:" + typeElement.getQualifiedName());
        } else if (element instanceof ExecutableElement) {
            ExecutableElement executableElement = (ExecutableElement) element;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                "[" + prefix + "]ExecutableElement:return " + executableElement.getReturnType() + ",receiver " + executableElement.getReceiverType());
        } else if (element instanceof VariableElement) {
            VariableElement variableElement = (VariableElement) element;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[" + prefix + "]VariableElement:" + variableElement.getSimpleName());
        } else {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "[" + prefix + "]Other Element" + element);
        }
    }

}
