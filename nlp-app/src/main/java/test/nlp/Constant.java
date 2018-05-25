package test.nlp;

/**
 * 常量类
 */
public class Constant {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final String SENTENCE_BEGIN = "始##始";

    public static final String SENTENCE_END = "末##末";

    public static final String UNKNOWN_PERSON = "未##人";

    public static final String UNKNOWN_SPACE = "未##地";

    public static final String UNKNOWN_NUM = "未##数";

    public static final String UNKNOWN_TIME = "未##时";

    public static final String UNKNOWN_PUNC = "未##标";

    public static final String UNKNOWN_LETTER = "未##串";

    public static final String UNKNOWN_GROUP = "未##团";

    /**
     * 拼音词库目录
     */
    public static final String PATH_PINYIN = "py";

    /**
     * 用户自定义词库目录
     */
    public static final String PATH_CUSTOM = "custom";

    /**
     * 同义词目录
     */
    public static final String PATH_SYNONYM = "synonym";

    /**
     * 停用词目录
     */
    public static final String PATH_STOPWORD = "stopword";

    /**
     * 纠错词库目录
     */
    public static final String PATH_SPELL_CHECK = "spellcheck";

    /**
     * txt文件后缀名
     */
    public static final String TXT_FILE_SUFFIX = ".txt";

    /**
     * dic文件后缀名
     */
    public static final String DIC_FILE_SUFFIX = ".dic";

    /**
     * 最小概率值
     */
    public static final double MIN_PROB = 1e-10;

    /**
     * 平滑参数
     */
    public static final double SMOOTHING_PARA = 0.1;

    public static final String BIGRAM_SEPARATOR = "@";

    public static final String COMMA_SEPARATOR = ",";

    public static String getPswd(String[] arr) {
        StringBuffer b = new StringBuffer();
        java.util.Random r;
        int k;
        for (int i = 0; i < 10; i++) {
            r = new java.util.Random();
            k = r.nextInt();
            b.append(String.valueOf(arr[Math.abs(k % 76)]));
        }

        return b.toString();
    }

    public static void main(String[] args) {
        System.out.println(Integer.parseInt("1,000"));
    }
}
