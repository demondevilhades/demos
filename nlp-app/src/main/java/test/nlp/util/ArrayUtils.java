package test.nlp.util;

/**
 * 数组操作工具类
 */
public class ArrayUtils {

    /**
     * 在数组中的每个元素拼接一个字符串
     * 
     * @param array
     * @param appendStr
     * @return
     */
    public static String[] append(String[] array, String appendStr) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] + appendStr;
        }
        return array;
    }
}
