package hades.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ASMClass {

    private ClassWriter cw;
    private final String className;

    public ASMClass(String className, Class<?> abClass, Class<?>[] iClasses) {
        this.className = className;
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        String superName = abClass == null ? "java/lang/Object" : abClass.getName().replace('.', '/');
        String[] interfaces = null;
        if (iClasses != null && iClasses.length > 0) {
            interfaces = new String[iClasses.length];
            for (int i = 0; i < iClasses.length; i++) {
                interfaces[i] = iClasses[i].getName().replace('.', '/');
            }
        }
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className.replace('.', '/'), null, superName, interfaces);
    }

    public void createDefConstructor() {
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();
    }

    public void createConstructor(Class<?>[] expClasses) {
        String[] exceptions = null;
        if (expClasses != null && expClasses.length > 0) {
            exceptions = new String[expClasses.length];
            for (int i = 0; i < expClasses.length; i++) {
                exceptions[i] = expClasses[i].getName().replace('.', '/');
            }
        }
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, exceptions);
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
}
