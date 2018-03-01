package hades.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class App {

    private ClassWriter cw;
    private final String className;

    public App(String className) {
        this.className = className;
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className.replace('.', '/'), null, "java/lang/Object", null);
    }

    public void createDefConstructor() {
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();
    }

    public void createVoidMethod(String methodName, String message) {
        MethodVisitor runMethod = cw.visitMethod(Opcodes.ACC_PUBLIC, methodName, "()V", null, null);
        runMethod.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        runMethod.visitLdcInsn(message);
        runMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
                false);
        runMethod.visitInsn(Opcodes.RETURN);
        runMethod.visitMaxs(1, 1);
        runMethod.visitEnd();
    }

    public String getClassName() {
        return className;
    }

    public byte[] getByteCode() {
        return cw.toByteArray();
    }

    public static void main(String[] args) throws Throwable {
        String className = "hades.asm.Test";
        String methodName = "run";
        App app = new App(className);
        app.createDefConstructor();
        app.createVoidMethod("run", "testRun");
        Class<?> testClass = ClassLoaderUtils.defineClass(className, app.getByteCode());
        System.out.println(testClass);

        // handle & reflect

        Object testObj0 = HandleUtils.newInstance(testClass);
        System.out.println(testObj0);
        Object testObj1 = ReflectionUtils.newInstance(testClass);
        System.out.println(testObj1);

        HandleUtils.invockMethod(testClass, methodName, void.class, null, testObj0);
        ReflectionUtils.invockMethod(testClass, methodName, null, testObj1, null);
    }
}
