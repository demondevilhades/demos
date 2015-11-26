package test.classencrypt;

/**
 * 
 * 
 * @author zs
 */
public class Example {

    private int testInt = 5;

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    @Override
    public String toString() {
        return "Example [testInt=" + testInt + "]";
    }

    public static void main(String[] args) {
        // -Djava.lang.Integer.IntegerCache.high=128
        Integer i0 = 128;
        Integer i1 = 128;
        System.out.println(i0 == i1);
    }
}
