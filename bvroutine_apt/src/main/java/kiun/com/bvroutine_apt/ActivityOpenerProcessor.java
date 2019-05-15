package kiun.com.bvroutine_apt;

import com.google.auto.common.MoreElements;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"kiun.com.bvroutine_apt.ActivityOpen","kiun.com.bvroutine_apt.IntentValue"})
public class ActivityOpenerProcessor extends AbstractProcessor {

    static final String CONTENT = "android.content";
    static final String ACTIVITY_TYPE = "android.app.Activity";

    static final ClassName Intent = ClassName.get(CONTENT,"Intent");
    static final ClassName Context = ClassName.get(CONTENT,"Context");
    static final ClassName Activity = ClassName.get("android.app", "Activity");
    static final ClassName ActivityCallback = ClassName.get("kiun.com.bvroutine","ActivityCallback");

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {

        Map<ClassName,TypeSpec.Builder> typeSpecMap = new HashMap<>();
        Map<ClassName,MethodSpec.Builder> methodMap = new HashMap<>();
        Map<ClassName,CodeBlock.Builder> codeCreateMap = new HashMap<>();
        Map<ClassName,StringBuilder> callMap = new HashMap<>();

        for(ExecutableElement element : ElementFilter.methodsIn(roundEnv.getElementsAnnotatedWith(ActivityOpen.class))){

            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            boolean isActivity = isSubtypeOfType(enclosingElement.asType(), ACTIVITY_TYPE);

            //只为Activity子类增加此功能.
            if (isActivity){
                String packageName = MoreElements.getPackage(enclosingElement).getQualifiedName().toString();
                String className = enclosingElement.getQualifiedName().toString().substring(packageName.length() + 1).replace('.', '$');
                ClassName sourceClass = ClassName.get(packageName, className);
                ClassName openClassName = ClassName.get(packageName, className + "Handler");

                TypeSpec.Builder typeSpec;
                if ((typeSpec = typeSpecMap.get(openClassName)) == null){
                    typeSpecMap.put(openClassName, typeSpec = createType(openClassName));
                }

                String intentMethodName = element.getSimpleName().toString()+"Intent";

                //映射的开启Activity方法.
                MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(element.getSimpleName().toString()).addModifiers(Modifier.PUBLIC,Modifier.STATIC);
                String createFunName = element.getSimpleName().toString()+"_create_" + UUID.randomUUID().toString().replace("-","");

                MethodSpec.Builder intentBuilder = MethodSpec.methodBuilder(intentMethodName).addModifiers(Modifier.PUBLIC,Modifier.STATIC);
                intentBuilder.returns(Intent);

                //创建回调函数.
                MethodSpec.Builder createMethod = MethodSpec.methodBuilder(createFunName).addModifiers(Modifier.PRIVATE,Modifier.STATIC);
                createMethod.addParameter(Activity, "activity");

                CodeBlock.Builder createCode = CodeBlock.builder();
                createCode.indent();
                createCode.add("if(activity instanceof $T){\n", sourceClass);
                createCode.indent();
                createCode.addStatement("$T sActivity = ($T)activity", sourceClass,sourceClass);
                createCode.addStatement("$T intent = sActivity.getIntent()", Intent);

                CodeBlock.Builder codeBlock = CodeBlock.builder();
                codeBlock.addStatement("$T guid = getGuid()", String.class);
                codeBlock.addStatement("$T intent = new $T(context,$T.class)",Intent,Intent,sourceClass);
                codeBlock.addStatement("intent.putExtra(ACTIVITY_GUID,guid)");

                methodBuilder.addParameter(Context, "context");
                intentBuilder.addParameter(Context,"context");

                StringBuilder intentCode = new StringBuilder("sActivity.");
                intentCode.append(element.getSimpleName().toString()).append("(");

                StringBuilder callIntentCode = new StringBuilder("Intent intent = " + intentMethodName +"(context");

                for (int i = 0; i < element.getParameters().size(); i++) {
                    VariableElement variable = element.getParameters().get(i);
                    String variableName = variable.getSimpleName().toString();
                    methodBuilder.addParameter(TypeName.get(variable.asType()), variableName);
                    intentBuilder.addParameter(TypeName.get(variable.asType()), variableName);

                    codeBlock.addStatement("intent.putExtra(\"$L\",$L)", variableName,variableName);
                    String intentValue = getIntentValue(variable.asType(), "intent", variableName, "0");
                    if (intentValue != null){
                        if (i > 0){
                            intentCode.append(",");
                        }
                        callIntentCode.append(",");
                        callIntentCode.append(variableName);
                        intentCode.append(intentValue);
                    }
                }

                methodBuilder.addCode(CodeBlock.builder().addStatement(callIntentCode.append(")").toString()).build());
                createCode.add(intentCode.append(");\n").toString());
                createCode.unindent().add("}\n").unindent();

                codeBlock.addStatement("addCallBack(guid,$T::$L)",openClassName,createFunName);
                codeBlock.addStatement("return intent");

                intentBuilder.addCode(codeBlock.build());
                methodBuilder.addCode(CodeBlock.builder().addStatement("context.startActivity(intent)").build());
                createMethod.addCode(createCode.build());

                typeSpec.addMethod(methodBuilder.build());
                typeSpec.addMethod(createMethod.build());
                typeSpec.addMethod(intentBuilder.build());
            }
        }

        for (VariableElement variable : ElementFilter.fieldsIn(roundEnv.getElementsAnnotatedWith(IntentValue.class))){
            String variableName = variable.getAnnotation(IntentValue.class).value().isEmpty() ? variable.getSimpleName().toString() : variable.getAnnotation(IntentValue.class).value();
            TypeElement enclosingElement = (TypeElement) variable.getEnclosingElement();
            boolean isActivity = isSubtypeOfType(enclosingElement.asType(), ACTIVITY_TYPE);

            if (isActivity){
                String packageName = MoreElements.getPackage(enclosingElement).getQualifiedName().toString();
                String className = enclosingElement.getQualifiedName().toString().substring(packageName.length() + 1).replace('.', '$');
                ClassName sourceClass = ClassName.get(packageName, className);
                ClassName openClassName = ClassName.get(packageName, className + "Handler");

                TypeSpec.Builder typeSpec;
                MethodSpec.Builder methodSpec;
                CodeBlock.Builder createCode;
                StringBuilder callString;
                if ((methodSpec = methodMap.get(openClassName)) == null && (createCode = codeCreateMap.get(openClassName)) == null){
                    if ((typeSpec = typeSpecMap.get(openClassName)) == null){
                        typeSpecMap.put(openClassName, typeSpec = createType(openClassName));
                    }
                    methodMap.put(openClassName, methodSpec = MethodSpec.methodBuilder("openActivityIntent").addModifiers(Modifier.PUBLIC, Modifier.STATIC));
                    codeCreateMap.put(openClassName, createCode = CodeBlock.builder());

                    createCode.add("if(activity instanceof $T){\n", sourceClass);
                    createCode.indent();
                    createCode.addStatement("$T sActivity = ($T)activity", sourceClass,sourceClass);
                    createCode.addStatement("$T intent = sActivity.getIntent()", Intent);

                    if ((callString = callMap.get(openClassName)) == null){
                        callMap.put(openClassName, callString = new StringBuilder("Intent intent = openActivityIntent(context"));
                    }

                    methodSpec.returns(Intent);
                    methodSpec.addParameter(Context, "context");
                    methodSpec.addCode(CodeBlock.builder().addStatement("String guid = getGuid()")
                            .addStatement("$T intent = new $T(context,$T.class)",Intent,Intent,sourceClass)
                            .addStatement("intent.putExtra(ACTIVITY_GUID,guid)").build());
                }

                if ((createCode = codeCreateMap.get(openClassName)) != null){
                    createCode.addStatement("sActivity.$L = $L",variableName,getIntentValue(variable.asType(),"intent",variableName,"0"));
                }

                if ((callString = callMap.get(openClassName)) != null){
                    callString.append(",").append(variableName);
                }

                methodSpec.addParameter(TypeName.get(variable.asType()), variableName);
                methodSpec.addCode(CodeBlock.builder().addStatement("intent.putExtra(\"$L\",$L)", variableName,variableName).build());
            }
        }

        for (ClassName key : typeSpecMap.keySet()){
            TypeSpec.Builder typeSpecBuild = typeSpecMap.get(key);
            MethodSpec.Builder methodSpec = methodMap.get(key);
            CodeBlock.Builder codeBuilder = codeCreateMap.get(key);
            StringBuilder callBuilder = callMap.get(key);

            if (methodSpec != null && codeBuilder != null){

                codeBuilder.add("}\n");
                codeBuilder.unindent();
                callBuilder.append(")");

                methodSpec.addCode(CodeBlock.builder().addStatement("addCallBack(guid,$T::openActivityNormal_create)", key).build());
                methodSpec.addCode(CodeBlock.builder().addStatement("return intent").build());
                MethodSpec intentMethod = methodSpec.build();
                typeSpecBuild.addMethod(intentMethod);

                MethodSpec.Builder createMethod = MethodSpec.methodBuilder("openActivityNormal_create").addModifiers(Modifier.PRIVATE, Modifier.STATIC).addCode(codeBuilder.build());
                createMethod.addParameter(Activity,"activity");

                MethodSpec.Builder startMethod = MethodSpec.methodBuilder("openActivityNormal").addModifiers(Modifier.PUBLIC,Modifier.STATIC);
                startMethod.addParameters(intentMethod.parameters);
                startMethod.addCode(CodeBlock.builder().addStatement(callBuilder.toString()).build());
                startMethod.addCode(CodeBlock.builder().addStatement("context.startActivity(intent)").build());

                typeSpecBuild.addMethod(startMethod.build());
                typeSpecBuild.addMethod(createMethod.build());
            }
            TypeSpec typeSpec = typeSpecBuild.build();
            try {
                brewJava(typeSpec, key).writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private TypeSpec.Builder createType(ClassName className) {

        TypeSpec.Builder result = TypeSpec.classBuilder(className.simpleName()).addModifiers(PUBLIC);
        result.superclass(ActivityCallback);
        result.addModifiers(FINAL);
        return result;
    }

    JavaFile brewJava(TypeSpec openTypeSpec, ClassName className) {

        return JavaFile.builder(className.packageName(), openTypeSpec).addFileComment("Generated code from BV Routine. Do not modify!").build();
    }

    static boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {
        if (isTypeEqual(typeMirror, otherType)) {
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTypeEqual(TypeMirror typeMirror, String otherType) {
        return otherType.equals(typeMirror.toString());
    }

    private static String getIntentValue(TypeMirror typeMirror, String handler, String varName, String defValue){

        if (isTypeEqual(typeMirror, "boolean") || isTypeEqual(typeMirror, "java.lang.Boolean")){
            return String.format("%s.getBooleanExtra(\"%s\", %s)", handler, varName, "false");
        }else if (isTypeEqual(typeMirror, "short") || isTypeEqual(typeMirror, "java.lang.Short")){
            return String.format("%s.getShortExtra(\"%s\", %s)", handler, varName, defValue);
        }else if (isTypeEqual(typeMirror, "char") || isTypeEqual(typeMirror, "java.lang.Char")){
            return String.format("%s.getCharExtra(\"%s\", %s)", handler, varName, defValue);
        }else if (isTypeEqual(typeMirror, "int") || isTypeEqual(typeMirror, "java.lang.Integer")){
            return String.format("%s.getIntExtra(\"%s\", %s)", handler, varName, defValue);
        }else if (isTypeEqual(typeMirror, "long") || isTypeEqual(typeMirror, "java.lang.Long")){
            return String.format("%s.getLongExtra(\"%s\", %s)", handler, varName, defValue);
        }else if (isTypeEqual(typeMirror, "float") || isTypeEqual(typeMirror, "java.lang.Float")){
            return String.format("%s.getFloatExtra(\"%s\", %s)", handler, varName, defValue);
        }else if (isTypeEqual(typeMirror, "double") || isTypeEqual(typeMirror, "java.lang.Double")){
            return String.format("%s.getDoubleExtra(\"%s\", %s)", handler, varName, defValue);
        }else if (isTypeEqual(typeMirror, "java.lang.String")){
            return String.format("%s.getStringExtra(\"%s\")", handler, varName);
        }else if (isSubtypeOfType(typeMirror, "android.os.Parcelable")){
            return String.format("%s.getParcelableExtra(\"%s\")", handler, varName);
        }
        return null;
    }

    private static TypeName bestGuess(String type) {
        switch (type) {
            case "void": return TypeName.VOID;
            case "boolean": return TypeName.BOOLEAN;
            case "byte": return TypeName.BYTE;
            case "char": return TypeName.CHAR;
            case "double": return TypeName.DOUBLE;
            case "float": return TypeName.FLOAT;
            case "int": return TypeName.INT;
            case "long": return TypeName.LONG;
            case "short": return TypeName.SHORT;
            default:
                int left = type.indexOf('<');
                if (left != -1) {
                    ClassName typeClassName = ClassName.bestGuess(type.substring(0, left));
                    List<TypeName> typeArguments = new ArrayList<>();
                    do {
                        typeArguments.add(WildcardTypeName.subtypeOf(Object.class));
                        left = type.indexOf('<', left + 1);
                    } while (left != -1);
                    return ParameterizedTypeName.get(typeClassName,
                            typeArguments.toArray(new TypeName[typeArguments.size()]));
                }
                return ClassName.bestGuess(type);
        }
    }
}
