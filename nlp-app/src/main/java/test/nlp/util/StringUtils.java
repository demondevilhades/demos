package test.nlp.util;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;

import test.nlp.Constant;
import test.nlp.common.Term;
import test.nlp.dict.pos.POS;
import test.nlp.dict.synonym.SynonymDictionary;
import test.nlp.segment.IWordSegmenter;
import test.nlp.util.CharacterUtil.CharType;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringUtils {
    private static final int FIRST_DAY_OF_MONATH = 1;

    private static final int LAST_DAY_OF_MONATH = 31;

    public static final Joiner TAB_JOINER = Joiner.on('\t').skipNulls();

    public static final Joiner EMPTY_JOINER = Joiner.on("").skipNulls();

    public static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    /**
     * 替换文本中的同义词
     * 
     * @param domain
     *            领域
     * @param text
     *            要替换的文本
     * @param segmenter
     *            分词器
     * @return
     */
    public static String replaceSynonym(String domain, String text, IWordSegmenter segmenter) {
        LinkedList<Term> terms = segmenter.segment(text);
        SynonymDictionary.getInstance().expand(domain, terms);
        return replaceSynonym(terms);
    }

    public static String replaceSynonym(LinkedList<Term> terms) {
        StringBuilder result = new StringBuilder();
        for (Term term : terms) {
            String[] synonyms = term.getSynonyms();
            if (synonyms == null) {
                result.append(term.getWord());
            } else {
                result.append(synonyms[0]);
            }
        }
        return result.toString();
    }

    public static String reverse(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    /**
     * 判断指定字符串是否是数字
     */
    public static boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否都是阿拉伯数字
     * 
     * @param input
     * @return
     */
    public static boolean isAllArbicNumber(String input) {
        for (int i = 0, len = input.length(); i < len; i++) {
            if (!CharacterUtil.isArbicNumber(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static char[] regularize(char[] input, int offset, int len) {
        for (int i = offset, size = Math.min(len + offset, input.length); i < size; i++) {
            input[i] = CharacterUtil.regularize(input[i]);
        }
        return input;
    }

    public static String regularize(String input) {
        if (input == null) {
            return null;
        }
        return new String(regularize(input.toCharArray(), 0, input.length()));
    }

    public static String[] getDescartesResult(String[] preArray, String[] array) {
        if (preArray == null) {
            return Arrays.copyOf(array, array.length);
        }

        if (array.length == 1) {
            for (int i = 0; i < preArray.length; i++) {
                preArray[i] = preArray[i] + array[0];
            }
            return preArray;
        }

        int m = preArray.length;
        int n = array.length;
        String[] result = new String[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i * n + j] = preArray[i] + array[j];
            }
        }
        return result;
    }

    /**
     * 判断字符串是否全是中文数字
     * 
     * @param input
     * @return
     */
    public static boolean isAllChineseNumber(String input) {
        if (org.apache.commons.lang3.StringUtils.isBlank(input)) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (CharacterUtil.getCharType(input.charAt(i)) != CharType.CT_CHINESE_NUMBER) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否包含空白字符
     * 
     * @param input
     * @return
     */
    public static boolean containsWhitespace(String input) {
        if (org.apache.commons.lang3.StringUtils.isBlank(input)) {
            return true;
        }

        for (int i = 0; i < input.length(); i++) {
            if (CharacterUtil.isWhitespace(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含CJK字符
     * 
     * @param input
     * @return
     */
    public static boolean containsCJK(String input) {
        if (org.apache.commons.lang3.StringUtils.isBlank(input)) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (CharacterUtil.isCJK(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否全是CJk字符
     * 
     * @param input
     * @return
     */
    public static boolean isCJK(String input) {
        if (org.apache.commons.lang3.StringUtils.isBlank(input)) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (!CharacterUtil.isCJK(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否全是26个英文字母
     * 
     * @param input
     * @return
     */
    public static boolean isAllAlphaChar(String input) {
        if (org.apache.commons.lang3.StringUtils.isBlank(input)) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (!CharacterUtil.isLetter(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否包含英文字符
     * 
     * @param input
     * @return
     */
    public static boolean containsAlphaChar(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (CharacterUtil.isLetter(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取不含后缀的文件名
     * 
     * @param fileName
     * @return
     */
    public static String getFileNameWithoutSuffix(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastIndex);
    }

    /**
     * 获取绝对路径
     * 
     * @param parentPath
     *            父目录的路径
     * @param path
     *            当前路径
     * @return
     */
    public static String getAbsolutePath(String parentPath, String path) {
        // 如果是绝对路径则直接返回
        if (path.startsWith("/") || path.indexOf(":") != -1 || path.startsWith("../")) {
            return path;
        }
        return parentPath + File.separator + path;
    }

    /**
     * 统计文本中的中文个数
     * 
     * @param text
     * @return
     */
    public static int countCJK(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (CharacterUtil.getCharType(text.charAt(i)) == CharType.CT_CJK) {
                count++;
            }
        }
        return count;
    }

    public static String getUnknownWord(POS pos) {
        if (pos == null) {
            return null;
        }
        String unknownWord = null;
        switch (pos) {
        case m:
            unknownWord = Constant.UNKNOWN_NUM;
            break;
        case nr:
            unknownWord = Constant.UNKNOWN_PERSON;
            break;
        case ns:
            unknownWord = Constant.UNKNOWN_SPACE;
            break;
        case nt:
            unknownWord = Constant.UNKNOWN_GROUP;
            break;
        case t:
            unknownWord = Constant.UNKNOWN_TIME;
            break;
        case w:
            unknownWord = Constant.UNKNOWN_PUNC;
            break;
        case x:
        case nx:
            unknownWord = Constant.UNKNOWN_LETTER;
            break;
        default:
            unknownWord = getUnknownWord(pos.getParent());
        }
        return unknownWord;
    }

    /**
     * 判断字符串是否是日期
     * 
     * @param text
     * @return
     */
    public static boolean isDayTime(String text) {
        Long val = NumberUtil.chn2digit(text);
        if (val == null) {
            return false;
        }
        return isDayTime(val.intValue());
    }

    public static boolean isDayTime(int day) {
        return day >= FIRST_DAY_OF_MONATH && day <= LAST_DAY_OF_MONATH;
    }

    /**
     * 判断字符串是否是小时
     * 
     * @param text
     * @return
     */
    public static boolean isHourTime(String text) {
        Long val = NumberUtil.chn2digit(text);
        if (val == null) {
            return false;
        }
        return isHourTime(val.intValue());
    }

    public static boolean isHourTime(int hour) {
        return hour >= 0 && hour <= 24;
    }

    /**
     * 判断字符串是否是月份
     * 
     * @param text
     * @return
     */
    public static boolean isMonthTime(String text) {
        Long val = NumberUtil.chn2digit(text);
        if (val == null) {
            return false;
        }
        return isMonthTime(val.intValue());

    }

    public static boolean isMonthTime(int month) {
        return month > Calendar.JANUARY && month <= Calendar.DECEMBER + 1;
    }

    /**
     * 判断字符串是否是年份
     * 
     * @param text
     * @return
     */
    public static boolean isYearTime(String text) {
        // 1998年，98年，17年
        int len = text.length();
        if ((len == 4 || len == 2) && StringUtils.isAllArbicNumber(text)) {
            return true;
        }
        if (len >= 2 && StringUtils.isAllChineseNumber(text)) {
            return true;
        }
        return false;
    }

    /**
     * 去除字符串前后所有不可见字符
     * 
     * @param input
     * @return
     */
    public static String trim(String input) {
        if (input == null) {
            return null;
        }
        char[] charArray = input.toCharArray();
        int len = charArray.length;
        int start = 0;
        while (start < len
                && (charArray[start] <= ' ' || charArray[start] == '　' || charArray[start] == ' ' || charArray[start] == (char) 65279)) {
            start++;
        }
        while (start < len && (charArray[len - 1] <= ' ' || charArray[len - 1] == '　')) {
            len--;
        }

        return (start > 0 || len < charArray.length) ? new String(charArray, start, len - start) : input;
    }

    public static String filterOffUtf8Mb4(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int i = 0;
        while (i < bytes.length) {
            short b = bytes[i];
            if (b > 0) {
                buffer.put(bytes[i++]);
                continue;
            }
            b += 256;
            if ((b ^ 0xC0) >> 4 == 0) {
                buffer.put(bytes, i, 2);
                i += 2;
            } else if ((b ^ 0xE0) >> 4 == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            } else if ((b ^ 0xF0) >> 4 == 0) {
                i += 4;
            } else {
                buffer.put(bytes[i++]);
            }
        }
        buffer.flip();
        return new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        System.out.println(trim("\r\n" + "<p></p>"));
    }
}
