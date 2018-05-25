package test.nlp.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * 字符集操作工具类
 */
public class CharacterUtil {

    /**
     * /** 全角起始字符
     */
    private static final char MIN_FULL_WIDTH = '\uFF00';

    /**
     * 全角终止字符
     */
    private static final char MAX_FULL_WIDTH = '\uFF5F';

    /**
     * 全角空格
     */
    private static final char FULL_WIDTH_WHITE = '\u3000';

    /**
     * 全角与半角字符之间的差值
     */
    private static final int GAP = 65248;

    private static final CharType[] CHAR_TYPES = new CharType[Character.MAX_VALUE + 1];

    private static final char[] SYSTEM_CHARS = new char[Character.MAX_VALUE + 1];

    static {
        initSystemChars();
        initCharTypes();
    }

    private static void initSystemChars() {
        for (int i = 0; i < SYSTEM_CHARS.length; i++) {
            SYSTEM_CHARS[i] = (char) Character.toLowerCase(i);
            SYSTEM_CHARS[i] = toHalfWidth(SYSTEM_CHARS[i]);
        }
        // 繁转简
        toSimple();
    }

    private static void initCharTypes() {
        UnicodeBlock ub = null;
        for (int i = 0; i < SYSTEM_CHARS.length; i++) {
            ub = UnicodeBlock.of(SYSTEM_CHARS[i]);
            if (UnicodeBlock.BASIC_LATIN == ub) {
                if (Character.isLetter(SYSTEM_CHARS[i])) {
                    CHAR_TYPES[i] = CharType.CT_LETTER;
                } else if (Character.isDigit(SYSTEM_CHARS[i])) {
                    CHAR_TYPES[i] = CharType.CT_NUMBER;
                } else if ("\n\r".indexOf(SYSTEM_CHARS[i]) != -1) {
                    CHAR_TYPES[i] = CharType.CT_PARAGRAPH_SEG;
                } else if (Character.isWhitespace(SYSTEM_CHARS[i])) {
                    CHAR_TYPES[i] = CharType.CT_WHITESPACE;
                } else if (!Character.isISOControl(SYSTEM_CHARS[i]) && SYSTEM_CHARS[i] <= Byte.MAX_VALUE) {
                    if (".!?;".indexOf(SYSTEM_CHARS[i]) != -1) {
                        CHAR_TYPES[i] = CharType.CT_SENTENCE_SEG;
                    } else if (',' == SYSTEM_CHARS[i]) {
                        CHAR_TYPES[i] = CharType.CT_SUB_SENTENCE_SEG;
                    } else if ("-%/:_".indexOf(SYSTEM_CHARS[i]) != -1) {
                        CHAR_TYPES[i] = CharType.CT_CONNECTOR;
                    } else {
                        CHAR_TYPES[i] = CharType.CT_PUNC;
                    }
                } else {
                    CHAR_TYPES[i] = CharType.CT_OTHER;
                }
            } else if (UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS == ub || UnicodeBlock.CJK_RADICALS_SUPPLEMENT == ub
                    || UnicodeBlock.CJK_STROKES == ub || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS == ub
                    || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub) {
                if ("零○一二三四五六七八九十百千万亿壹贰叁肆伍陆柒捌玖拾佰仟两".indexOf(SYSTEM_CHARS[i]) != -1) {
                    CHAR_TYPES[i] = CharType.CT_CHINESE_NUMBER;
                } else {
                    CHAR_TYPES[i] = CharType.CT_CJK;
                }
            } else if (UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION == ub || UnicodeBlock.CJK_COMPATIBILITY_FORMS == ub) {
                if ("。！？；…".indexOf(i) != -1) {
                    CHAR_TYPES[i] = CharType.CT_SENTENCE_SEG;
                } else if ("、，".indexOf(i) != -1) {
                    CHAR_TYPES[i] = CharType.CT_SUB_SENTENCE_SEG;
                } else {
                    CHAR_TYPES[i] = CharType.CT_PUNC;
                }
            } else {
                CHAR_TYPES[i] = CharType.CT_OTHER;
            }
        }

    }

    /**
     * 获取字符类型
     * 
     * @param input
     * @return
     */
    public static CharType getCharType(char input) {
        return CHAR_TYPES[input];
    }

    /**
     * 全角转半角
     * 
     * @param ch
     */
    public static char toHalfWidth(char ch) {
        if (ch >= MIN_FULL_WIDTH && ch <= MAX_FULL_WIDTH) {
            return (char) (ch - GAP);
        }
        if (FULL_WIDTH_WHITE == ch) {
            return ' ';
        }
        return ch;
    }

    /**
     * 正规化字符：繁转简、大写转小写、全角转半角等
     * 
     * @param ch
     * @return
     */
    public static char regularize(char ch) {
        return SYSTEM_CHARS[ch];
    }

    public static char[] regularize(char[] charArray, int offset, int length) {
        for (int i = offset; i < length; i++) {
            charArray[i] = SYSTEM_CHARS[charArray[i]];
        }
        return charArray;
    }

    public static boolean isWhitespace(char ch) {
        return CharType.CT_WHITESPACE == CHAR_TYPES[ch].getRootType();
    }

    /**
     * 判断字符是否是阿拉伯数字
     * 
     * @param ch
     * @return
     */
    public static boolean isArbicNumber(char ch) {
        return CHAR_TYPES[ch] == CharType.CT_NUMBER;
    }

    /**
     * 判断字符是否是英文字母
     * 
     * @param ch
     * @return
     */
    public static boolean isLetter(char ch) {
        return CHAR_TYPES[ch] == CharType.CT_LETTER;
    }

    /**
     * 判断指定的字符是不是CJK字符
     * 
     * @param ch
     * @return
     */
    public static boolean isCJK(char ch) {
        return CharType.CT_CJK == CHAR_TYPES[ch].getRootType();
    }

    public static enum CharType {

        /**
         * 26个英文字母
         */
        CT_LETTER,

        /**
         * 阿拉伯数字
         */
        CT_NUMBER,

        /**
         * CJK字符
         */
        CT_CJK,

        /**
         * 标点符号
         */
        CT_PUNC,

        /**
         * 空白字符
         */
        CT_WHITESPACE,

        /**
         * 中文数字，如一、二、三、四等
         */
        CT_CHINESE_NUMBER() {
            @Override
            public CharType getRootType() {
                return CT_CJK;
            }
        },

        /**
         * 句子分隔符，用于句中，如逗号(，)，顿号(、)等
         */
        CT_SUB_SENTENCE_SEG() {
            @Override
            public CharType getRootType() {
                return CT_PUNC;
            }
        },

        /**
         * 句子分隔符，用于句尾，如句号(。)，问号(？)
         */
        CT_SENTENCE_SEG() {
            @Override
            public CharType getRootType() {
                return CT_PUNC;
            }
        },

        /**
         * 连接符
         */
        CT_CONNECTOR() {
            @Override
            public CharType getRootType() {
                return CT_PUNC;
            }
        },

        /**
         * 段落分隔符
         */
        CT_PARAGRAPH_SEG() {
            @Override
            public CharType getRootType() {
                return CT_WHITESPACE;
            }
        },

        /**
         * 其他
         */
        CT_OTHER;

        /**
         * 获取根类型
         * 
         * @return
         */
        public CharType getRootType() {
            return this;
        }

    }

    /**
     * 繁转简
     */
    private static void toSimple() {
        try (InputStream in = CharacterUtil.class.getResourceAsStream("/cht&chs.dic");) {
            List<String> lines = IOUtils.readLines(in, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (org.apache.commons.lang3.StringUtils.isBlank(line)) {
                    continue;
                }
                String[] splits = line.split("\\s+");
                if (splits.length != 2) {
                    continue;
                }
                SYSTEM_CHARS[splits[0].charAt(0)] = SYSTEM_CHARS[splits[1].charAt(0)];
            }
        } catch (IOException e) {
            throw new RuntimeException("Load cht&chs.dic error.", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.isAllChineseNumber("九五"));
    }
}
