package test.nlp.ltp;

import test.nlp.dict.pos.POS;

/**
 * LTP的词性标注集
 */
public enum LTPPOS {
    /**
     * 形容词
     */
    a,

    /**
     * 区别词
     */
    b,

    /**
     * 连词
     */
    c,

    /**
     * 副词
     */
    d,

    /**
     * 叹词
     */
    e,

    /**
     * 语素词
     */
    g,

    /**
     * 前缀
     */
    h,

    /**
     * 成语
     */
    i,

    /**
     * 简称
     */
    j,

    /**
     * 后缀
     */
    k,

    /**
     * 数词
     */
    m,

    /**
     * 普通名词
     */
    n,

    /**
     * 方位名词
     */
    nd() {
        @Override
        public POS getPOS() {
            return POS.f;
        }
    },

    /**
     * 人名
     */
    nh() {
        @Override
        public POS getPOS() {
            return POS.nr;
        }
    },

    /**
     * 机构名
     */
    ni() {
        @Override
        public POS getPOS() {
            return POS.nt;
        }
    },

    /**
     * 处所名
     */
    nl() {
        @Override
        public POS getPOS() {
            return POS.s;
        }
    },

    /**
     * 地名
     */
    ns,

    /**
     * 时间名词
     */
    nt() {
        @Override
        public POS getPOS() {
            return POS.t;
        }
    },

    /**
     * 专有名词
     */
    nz,

    /**
     * 叹词
     */
    o,

    /**
     * 介词
     */
    p,

    /**
     * 量词
     */
    q,

    /**
     * 代词
     */
    r,

    /**
     * 助词
     */
    u,

    /**
     * 动词
     */
    v,

    /**
     * 标点
     */
    wp() {
        @Override
        public POS getPOS() {
            return POS.w;
        }
    },

    /**
     * 外文词
     */
    ws() {
        @Override
        public POS getPOS() {
            return POS.nx;
        }
    },

    /**
     * non-lexeme
     */
    x,

    z,

    begin,

    end;

    public POS getPOS() {
        return POS.valueOf(this.toString());
    }
}
