package test.instrumentation;

/**
 * java -javaagent:instrumentation-0.1. jar -cp instrumentation-0.1.jar
 * test.instrumentation.App
 * 
 * @author zhangshuai
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println(AgentMain.getObjectSize(new App()));
    }
}
