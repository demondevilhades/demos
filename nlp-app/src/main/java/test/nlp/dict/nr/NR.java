package test.nlp.dict.nr;

/**
 * 人名标注标签
 */
public enum NR {

    /**
     * 其他无关词， 如[全军]和[武普][官兵][深切]缅怀邓小平
     */
    A,

    /**
     * 姓氏, 如[张]华平
     */
    B,

    /**
     * 双名首字，如张[华]平
     */
    C,

    /**
     * 双名尾字，如张华[平]
     */
    D,

    /**
     * 单名，如张[浩]
     */
    E,

    /**
     * 前缀，如[老]刘
     */
    F,

    /**
     * 后缀，如王[总]
     */
    G,

    /**
     * 人名的上文， 如又[来到]干洪洋的家
     */
    K,

    /**
     * 人名的下文， 如新华社记者黄文[摄]
     */
    L,

    /**
     * 两个中国人名之间的成分， 如编剧邵均林[和]稽道青说
     */
    M,

    /**
     * 句首词
     */
    S,

    /**
     * 人名的上文与姓氏成词，如现任主席[为何]鲁丽
     */
    U,

    /**
     * 人名的末字与人名下文成词，如龚学[平等]领导
     */
    V,

    /**
     * 人名整个成词
     */
    W,

    /**
     * 姓与双名的首字成词，[王国]维
     */
    X,

    /**
     * 姓与单名成词，如[高峰]
     */
    Y,

    /**
     * 双名本身成词,如张[朝阳]
     */
    Z
}
