package test.nlp.util;

import java.text.DecimalFormat;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 数字操作工具类
 */
public class NumberUtil {

    private static Map<Character, Integer> bigUnitMap;

    private static Map<Character, Integer> unitMap;

    private static Map<Character, Integer> numMap;

    /**
     * 只处理千亿以内的阿拉伯数字转换
     */
    private static final DecimalFormat FORMAT = new DecimalFormat("000000000000");

    private static final String DIGIT_REGX = "\\d{1,12}";

    /**
     * 单位数组
     */
    private static final String[] UNITS = new String[] { "", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

    private static final String[] NUMERICS = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

    static {
        unitMap = Maps.newHashMap();
        unitMap.put('十', 10);
        unitMap.put('拾', 10);
        unitMap.put('百', 100);
        unitMap.put('佰', 100);
        unitMap.put('千', 1000);
        unitMap.put('仟', 1000);

        bigUnitMap = Maps.newHashMap();
        bigUnitMap.put('万', 10000);
        bigUnitMap.put('亿', 100000000);

        numMap = Maps.newHashMap();
        numMap.put('零', 0);
        numMap.put('一', 1);
        numMap.put('壹', 1);
        numMap.put('二', 2);
        numMap.put('贰', 2);
        numMap.put('三', 3);
        numMap.put('叁', 3);
        numMap.put('四', 4);
        numMap.put('肆', 4);
        numMap.put('五', 5);
        numMap.put('伍', 5);
        numMap.put('六', 6);
        numMap.put('陆', 6);
        numMap.put('七', 7);
        numMap.put('柒', 7);
        numMap.put('八', 8);
        numMap.put('捌', 8);
        numMap.put('九', 9);
        numMap.put('玖', 9);
    }

    /**
     * 中文数字转阿拉伯数字
     * 
     * @param chnStr
     */
    public static Long chn2digit(String chnStr) {
        long result = 0;
        int temp = 0;
        for (int i = 0, length = chnStr.length(); i < length; i++) {
            char ch = chnStr.charAt(i);
            // 数字
            if (numMap.containsKey(ch)) {
                temp = temp * 10 + numMap.get(ch);
            }
            // 单位
            else if (unitMap.containsKey(ch)) {
                result += Math.max(temp, 1) * unitMap.get(ch);
                temp = 0;
            } else if (bigUnitMap.containsKey(ch)) {
                result = Math.max(1, result + temp) * bigUnitMap.get(ch);
                temp = 0;
            } else if (ch >= '0' && ch <= '9') {
                temp = temp * 10 + (ch - '0');
            } else {
                return null;
            }
        }
        if (temp != 0) {
            result += temp;
        }
        return result;
    }

    public static String digit2chn(String arbicNumber) {
        if (!arbicNumber.matches(DIGIT_REGX)) {
            return null;
        }
        String formatNumber = FORMAT.format(Long.parseLong(arbicNumber));
        StringBuilder builder = new StringBuilder();
        for (int i = 0, length = formatNumber.length(); i < length; i++) {
            int numeric = formatNumber.charAt(i) - 48;
            if (numeric == 0) {
                // 最前面不能有零
                if (builder.length() == 0) {
                    continue;
                }
            }
            builder.append(NUMERICS[numeric]);
            builder.append(UNITS[length - i - 1]);

        }
        String result = builder.toString();
        // 过滤掉零千零百这样的
        if (result.contains("零")) {
            result = result.replace("零千零百零十", "零");
            result = result.replace("零千零百", "零");
            result = result.replace("零千", "零");
            result = result.replace("零百零十", "零");
            result = result.replace("零百", "零");
            result = result.replace("零十", "零");
            result = result.replaceAll("十零", "十");
            result = result.replaceAll("百零零", "百");
            result = result.replaceAll("千零零", "千");
            result = result.replaceAll("万零零", "万");
            result = result.replaceAll("亿零零万", "亿");
        }
        if (result.startsWith("一十")) {
            result = result.replaceFirst("一十", "十");
        }
        return result;

    }

    public static void main(String[] args) {
        System.out.println(chn2digit("1十一"));
        System.out.println(digit2chn("100100000001"));
    }
}
